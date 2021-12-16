package nsu.timofeev.badweatherapp.presentation.weather.list

import nsu.timofeev.badweatherapp.weather.FavoriteCityWeather
import nsu.timofeev.badweatherapp.weather.FavoriteCityRepository
import nsu.timofeev.badweatherapp.weather.WeatherRepository
import nsu.timofeev.badweatherapp.weather.current_weather_model.CityCurrentWeather
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class WeatherListPresenter @Inject constructor(
    private val favoriteCityRepository: FavoriteCityRepository,
    private val weatherRepository: WeatherRepository
) : MvpPresenter<ListView>() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private fun Disposable.untilDestroy() {
        compositeDisposable.add(this)
    }

    fun openDetails(cityName: String) {
        if (cityName.isEmpty()) {
            viewState.showError("Field is empty!")
        } else {
            viewState.openDetails(cityName)
        }
    }

    fun onCityClicked(cityCurrent: CityCurrentWeather) {
        viewState.openDetails(cityCurrent.name)
    }

    private fun convertFavoriteCitiesList(list: List<FavoriteCityWeather>) {
        list.forEach { favoriteCity ->
            weatherRepository.getCurrentWeather(favoriteCity.name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    favoriteCityRepository.updateFavoriteCityWeather(FavoriteCityWeather(name = favoriteCity.name, weather = it))
                    viewState.bindOneCity(it)
                }, {
                    viewState.bindOneCity(favoriteCity.weather)
                })
                .untilDestroy()
        }
    }

    fun onFavoritesList() {
        viewState.cleanCitiesList()
        favoriteCityRepository.getAllFavoritesCityWeather()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                convertFavoriteCitiesList(it)
            }, {
                it.printStackTrace()
            })
            .untilDestroy()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
package nsu.timofeev.badweatherapp.presentation.weather.details

import nsu.timofeev.badweatherapp.weather.FavoriteCityForecast
import nsu.timofeev.badweatherapp.weather.FavoriteCityWeather
import nsu.timofeev.badweatherapp.weather.FavoriteCityRepository
import nsu.timofeev.badweatherapp.weather.WeatherRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class WeatherDetailsPresenter @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val favoriteCityRepository: FavoriteCityRepository
) : MvpPresenter<DetailsView>() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private fun Disposable.untilDestroy() {
        compositeDisposable.add(this)
    }

    fun convertTemp(temp: Double): Int {
        return (temp - 273).toInt()
    }

    fun loadCity(cityName: String) {
        viewState.setIsCurrentLoading(true)
        weatherRepository.getCurrentWeather(cityName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate {
                viewState.setIsCurrentLoading(false)
            }
            .subscribe({
                favoriteCityRepository.updateFavoriteCityWeather(FavoriteCityWeather(name = cityName, weather = it))
                viewState.bindCity(it)
            }, {
                favoriteCityRepository.getFavoriteCityWeather(cityName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        viewState.bindCity(it.weather)
                    }, {
                        it.printStackTrace()
                        viewState.goBack()
                    })
                    .untilDestroy()
            }).untilDestroy()
    }

    fun onAddToFavoriteButtonClicked(cityName: String, isChecked: Boolean) {
        if (!isChecked) {
            weatherRepository.getCurrentWeather(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({favoriteCityRepository.insertFavoriteCityWeather(FavoriteCityWeather(name = cityName, weather = it))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({}, {
                        it.printStackTrace()
                    })
                    .untilDestroy()}, {})
                .untilDestroy()
            weatherRepository.getWeatherForecast(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({favoriteCityRepository.insertFavoriteCityForecast(FavoriteCityForecast(name = cityName, forecast = it))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({}, {
                        it.printStackTrace()
                    })
                    .untilDestroy()}, {})
        .untilDestroy()
        } else {
            favoriteCityRepository.deleteFavoriteCityWeather(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}, {
                    it.printStackTrace()
                })
                .untilDestroy()
        }
    }

    fun haveInFavorites(cityName: String) {
        favoriteCityRepository.getFavoriteCityWeather(cityName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    viewState.setIsInFavorites(true)
                       },
                {
                    viewState.setIsInFavorites(false)
                })
            .untilDestroy()
    }

    fun loadForecast(cityName: String) {
        viewState.setIsForecastLoading(true)
        weatherRepository.getWeatherForecast(cityName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate {
                viewState.setIsForecastLoading(false)
            }
            .subscribe({
                favoriteCityRepository.updateFavoriteCityForecast(FavoriteCityForecast(name = cityName, forecast = it))
                viewState.bindForecastList(it.list)
            }, {
                favoriteCityRepository.getFavoriteCityForecast(cityName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({viewState.bindForecastList(it.forecast.list) }, {})
                    .untilDestroy()
                 }).untilDestroy()
    }

    fun goBack() {
        viewState.goBack()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
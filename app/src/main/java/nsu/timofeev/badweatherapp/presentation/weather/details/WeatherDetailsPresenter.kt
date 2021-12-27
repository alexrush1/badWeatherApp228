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
import nsu.timofeev.badweatherapp.weather.current_weather_model.CityCurrentWeather
import nsu.timofeev.badweatherapp.weather.weather_forecast_model.CityWeatherForecast
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
            .doOnError {
                favoriteCityRepository.getFavoriteCityWeather(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.bindCity(it.weather)
                }, {
                    viewState.goBack()
                })
                .untilDestroy()
            }
            .subscribe ({
                favoriteCityRepository.updateFavoriteCityWeather(
                    FavoriteCityWeather(
                        name = cityName,
                        weather = it
                    )
                )
                viewState.bindCity(it)
            }, {})
            .untilDestroy()
    }

    fun onAddToFavoriteButtonClicked(cityName: String, isChecked: Boolean) {
        if (!isChecked) {
            weatherRepository.getCurrentWeather(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ insertFavoriteCityWeather(cityName, it) }, {})
                .untilDestroy()
            weatherRepository.getWeatherForecast(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ insertFavoriteCityForecast(cityName, it) }, {})
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
            .doOnError {
                favoriteCityRepository.getFavoriteCityForecast(cityName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({viewState.bindForecastList(it.forecast.list) }, {})
                    .untilDestroy()
            }
            .subscribe({
                favoriteCityRepository.updateFavoriteCityForecast(FavoriteCityForecast(name = cityName, forecast = it))
                viewState.bindForecastList(it.list)
            }, {})
            .untilDestroy()
    }

    fun goBack() {
        viewState.goBack()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    private fun insertFavoriteCityWeather(cityName: String, weather: CityCurrentWeather) {
        favoriteCityRepository.insertFavoriteCityWeather(FavoriteCityWeather(name = cityName, weather = weather))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {
                it.printStackTrace()
            })
            .untilDestroy()
    }

    private fun insertFavoriteCityForecast(cityName: String, forecast: CityWeatherForecast) {
        favoriteCityRepository.insertFavoriteCityForecast(FavoriteCityForecast(name = cityName, forecast = forecast))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {
                it.printStackTrace()
            })
            .untilDestroy()
    }
}
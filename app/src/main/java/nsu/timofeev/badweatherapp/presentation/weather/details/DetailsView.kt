package nsu.timofeev.badweatherapp.presentation.weather.details

import nsu.timofeev.badweatherapp.weather.current_weather_model.CityCurrentWeather
import nsu.timofeev.badweatherapp.weather.weather_forecast_model.Forecast
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleTagStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleTagStrategy::class)
interface DetailsView: MvpView {

    fun setIsCurrentLoading(isLoading: Boolean)
    fun setIsForecastLoading(isLoading: Boolean)
    fun setIsInFavorites(isFavorite: Boolean)
    fun bindCity(cityCurrentWeather: CityCurrentWeather)
    fun bindForecastList(forecastList: List<Forecast>)
    fun goBack()
}
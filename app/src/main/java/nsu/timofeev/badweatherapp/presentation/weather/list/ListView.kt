package nsu.timofeev.badweatherapp.presentation.weather.list

import nsu.timofeev.badweatherapp.weather.current_weather_model.CityCurrentWeather
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleTagStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleTagStrategy::class)
interface ListView: MvpView {

    fun openDetails(cityName: String)
    fun bindCitiesList(cityCurrents: List<CityCurrentWeather>)
    fun cleanCitiesList()
    fun bindOneCity(city: CityCurrentWeather)
    fun showError(message: String)
}
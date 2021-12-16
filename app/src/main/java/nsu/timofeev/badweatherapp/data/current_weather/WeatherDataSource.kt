package nsu.timofeev.badweatherapp.data.current_weather

import nsu.timofeev.badweatherapp.weather.current_weather_model.CityCurrentWeather
import nsu.timofeev.badweatherapp.weather.weather_forecast_model.CityWeatherForecast
import io.reactivex.Single

interface WeatherDataSource {

    fun getCurrentWeather(cityName: String): Single<CityCurrentWeather>
    fun getWeatherForecast(cityName: String): Single<CityWeatherForecast>
}
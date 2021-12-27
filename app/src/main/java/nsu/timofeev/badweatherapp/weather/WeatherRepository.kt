package nsu.timofeev.badweatherapp.weather

import nsu.timofeev.badweatherapp.weather.current_weather_model.CityCurrentWeather
import nsu.timofeev.badweatherapp.weather.weather_forecast_model.CityWeatherForecast
import io.reactivex.Single

interface WeatherRepository {

    fun getCurrentWeather(cityName: String): Single<CityCurrentWeather>
    fun getWeatherForecast(cityName: String): Single<CityWeatherForecast>
}
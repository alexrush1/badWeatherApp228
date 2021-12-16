package nsu.timofeev.badweatherapp.data.current_weather

import nsu.timofeev.badweatherapp.weather.WeatherRepository
import nsu.timofeev.badweatherapp.weather.current_weather_model.CityCurrentWeather
import nsu.timofeev.badweatherapp.weather.weather_forecast_model.CityWeatherForecast
import io.reactivex.Single

class WeatherRepositoryImpl(private val weatherDataSource: WeatherDataSource): WeatherRepository {

    override fun getCurrentWeather(cityName: String): Single<CityCurrentWeather> = weatherDataSource.getCurrentWeather(cityName)
    override fun getWeatherForecast(cityName: String): Single<CityWeatherForecast> = weatherDataSource.getWeatherForecast(cityName)
}
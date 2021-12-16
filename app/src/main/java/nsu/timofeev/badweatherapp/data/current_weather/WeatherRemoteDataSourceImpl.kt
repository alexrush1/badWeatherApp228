package nsu.timofeev.badweatherapp.data.current_weather

import nsu.timofeev.badweatherapp.weather.current_weather_model.CityCurrentWeather
import nsu.timofeev.badweatherapp.weather.weather_forecast_model.CityWeatherForecast
import io.reactivex.Single


class WeatherRemoteDataSourceImpl(private val cityApi: WeatherApi): WeatherDataSource {

    override fun getCurrentWeather(cityName: String): Single<CityCurrentWeather> = cityApi.getCurrentWeather(cityName)
    override fun getWeatherForecast(cityName: String): Single<CityWeatherForecast> = cityApi.getWeatherForecast(cityName)
}
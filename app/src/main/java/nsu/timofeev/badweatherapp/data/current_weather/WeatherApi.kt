package nsu.timofeev.badweatherapp.data.current_weather

import nsu.timofeev.badweatherapp.weather.current_weather_model.CityCurrentWeather
import nsu.timofeev.badweatherapp.weather.weather_forecast_model.CityWeatherForecast
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    companion object {
        const val apiKey = "b2b3fa7ea69777aaa30f4d842fb9249f"
    }

    @GET("weather")
    fun getCurrentWeather(
        @Query("q") name: String,
        @Query("appid") appid: String = apiKey
    ): Single<CityCurrentWeather>

    @GET("forecast")
    fun getWeatherForecast(
        @Query("q") name: String,
        @Query("appid") appid: String = apiKey,
        @Query("cnt") cnt: Int = 50,
    ): Single<CityWeatherForecast>
}
package nsu.timofeev.badweatherapp.weather.weather_forecast_model

import java.io.Serializable

data class CityWeatherForecast(

    val list: List<Forecast>
) : Serializable

data class Forecast(

    val dt: Long,
    val dt_txt: String,
    val main: Main,
    val weather: List<Weather>
) : Serializable

data class Main(

    val temp_max: Double,
    val temp_min: Double
) : Serializable

data class Weather(

    val icon: String,
    val id: Int
) : Serializable
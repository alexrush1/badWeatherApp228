package nsu.timofeev.badweatherapp.weather.current_weather_model

import java.io.Serializable

data class CityCurrentWeather (

        val main: Main,
        val name: String,
        val sys: Sys,
        val weather: List<Weather>
) : Serializable

data class Main(

        val feels_like: Double,
        val humidity: Int,
        val temp: Double
) : Serializable

data class Sys(

        val country: String
) : Serializable

data class Weather(

        val description: String,
        val icon: String,
        val id: Int,
        val main: String
) : Serializable

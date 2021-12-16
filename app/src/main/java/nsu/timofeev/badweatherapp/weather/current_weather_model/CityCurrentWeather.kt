package nsu.timofeev.badweatherapp.weather.current_weather_model

class CityCurrentWeather(

        val main: Main,
        val name: String,
        val sys: Sys,
        val weather: List<Weather>
)

data class Main(

        val feels_like: Double,
        val humidity: Int,
        val temp: Double
)
data class Sys(

        val country: String
)
data class Weather(

        val description: String,
        val icon: String,
        val id: Int,
        val main: String
)

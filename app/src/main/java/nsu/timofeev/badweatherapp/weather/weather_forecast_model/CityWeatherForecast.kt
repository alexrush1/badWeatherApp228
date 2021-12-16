package nsu.timofeev.badweatherapp.weather.weather_forecast_model

data class CityWeatherForecast(

    val list: List<Forecast>
)

data class Forecast(

    val dt: Long,
    val dt_txt: String,
    val main: Main,
    val weather: List<Weather>
)

data class Main(

    val temp_max: Double,
    val temp_min: Double
)

data class Weather(

    val icon: String,
    val id: Int
)
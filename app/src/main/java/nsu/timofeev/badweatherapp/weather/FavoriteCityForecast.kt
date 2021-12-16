package nsu.timofeev.badweatherapp.weather

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import nsu.timofeev.badweatherapp.weather.weather_forecast_model.CityWeatherForecast

@Entity
data class FavoriteCityForecast(

    @PrimaryKey
    @ColumnInfo(name = "name")
    val name: String,
    val forecast: CityWeatherForecast
)

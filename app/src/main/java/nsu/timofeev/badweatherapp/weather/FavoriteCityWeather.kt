package nsu.timofeev.badweatherapp.weather

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import nsu.timofeev.badweatherapp.weather.current_weather_model.CityCurrentWeather

@Entity
data class FavoriteCityWeather(

    @PrimaryKey
    @ColumnInfo(name = "name")
    val name: String,
    val weather: CityCurrentWeather
)
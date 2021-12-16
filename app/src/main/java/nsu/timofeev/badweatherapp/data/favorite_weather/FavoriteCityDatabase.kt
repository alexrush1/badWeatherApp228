package nsu.timofeev.badweatherapp.data.favorite_weather

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import nsu.timofeev.badweatherapp.weather.FavoriteCityForecast
import nsu.timofeev.badweatherapp.weather.FavoriteCityWeather

@Database(entities = [FavoriteCityWeather::class, FavoriteCityForecast::class], version = 4)
@TypeConverters(CityCurrentWeatherConverter::class, CityWeatherForecastConverter::class)
abstract class FavoriteCityDatabase : RoomDatabase(){

    abstract fun getFavoriteCityWeatherDao(): FavoriteCityWeatherDao
    abstract fun getFavoriteCityForecastDao(): FavoriteCityForecastDao
}
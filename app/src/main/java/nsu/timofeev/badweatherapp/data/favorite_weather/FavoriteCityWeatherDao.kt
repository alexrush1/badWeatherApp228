package nsu.timofeev.badweatherapp.data.favorite_weather

import androidx.room.*
import nsu.timofeev.badweatherapp.weather.FavoriteCityWeather
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface FavoriteCityWeatherDao {
    @Query("SELECT * FROM FavoriteCityWeather")
    fun getAll(): Single<List<FavoriteCityWeather>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCity(favoriteCityWeather: FavoriteCityWeather): Completable

    @Query("DELETE FROM FavoriteCityWeather WHERE name=:favoriteCityName")
    fun deleteCity(favoriteCityName: String): Completable

    @Query("SELECT * FROM FavoriteCityWeather WHERE name = :favoriteCityName")
    fun get(favoriteCityName: String): Single<FavoriteCityWeather>

    @Update
    fun update(favoriteCityWeather: FavoriteCityWeather): Completable
}
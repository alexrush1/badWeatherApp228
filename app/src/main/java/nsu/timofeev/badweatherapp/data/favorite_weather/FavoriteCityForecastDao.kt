package nsu.timofeev.badweatherapp.data.favorite_weather

import androidx.room.*
import nsu.timofeev.badweatherapp.weather.FavoriteCityForecast
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface FavoriteCityForecastDao {
    @Query("SELECT * FROM FavoriteCityForecast")
    fun getAll(): Single<List<FavoriteCityForecast>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCity(favoriteCityWeather: FavoriteCityForecast): Completable

    @Query("DELETE FROM FavoriteCityForecast WHERE name = :favoriteCityName")
    fun deleteCity(favoriteCityName: String): Completable

    @Query("SELECT * FROM FavoriteCityForecast WHERE name = :favoriteCityName")
    fun get(favoriteCityName: String): Single<FavoriteCityForecast>

    @Update
    fun update(favoriteCityForecast: FavoriteCityForecast): Completable
}
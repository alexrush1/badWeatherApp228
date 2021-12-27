package nsu.timofeev.badweatherapp.weather

import io.reactivex.Completable
import io.reactivex.Single

interface FavoriteCityRepository {

    fun getAllFavoritesCityWeather(): Single<List<FavoriteCityWeather>>
    fun insertFavoriteCityWeather(favoriteCityWeather: FavoriteCityWeather): Completable
    fun deleteFavoriteCityWeather(favoriteCityName: String): Completable
    fun updateFavoriteCityWeather(favoriteCityWeather: FavoriteCityWeather): Completable
    fun getFavoriteCityWeather(favoriteCityName: String): Single<FavoriteCityWeather>

    fun getAllFavoritesCityForecast(): Single<List<FavoriteCityForecast>>
    fun insertFavoriteCityForecast(favoriteCityForecast: FavoriteCityForecast): Completable
    fun deleteFavoriteCityForecast(favoriteCityName: String): Completable
    fun updateFavoriteCityForecast(favoriteCityForecast: FavoriteCityForecast): Completable
    fun getFavoriteCityForecast(favoriteCityName: String): Single<FavoriteCityForecast>
}
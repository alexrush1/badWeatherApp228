package nsu.timofeev.badweatherapp.data.favorite_weather

import nsu.timofeev.badweatherapp.weather.FavoriteCityForecast
import nsu.timofeev.badweatherapp.weather.FavoriteCityWeather
import nsu.timofeev.badweatherapp.weather.FavoriteCityRepository
import io.reactivex.Completable
import io.reactivex.Single

class FavoriteCityRepositoryImpl(private val favoriteCityDataSource: FavoriteCityDataSource):
    FavoriteCityRepository {

    override fun getAllFavoritesCityWeather(): Single<List<FavoriteCityWeather>> {
        return favoriteCityDataSource.getAllFavoritesCityWeather()
    }

    override fun insertFavoriteCityWeather(favoriteCityWeather: FavoriteCityWeather): Completable {
        return favoriteCityDataSource.insertFavoriteCityWeather(favoriteCityWeather)
    }
    override fun deleteFavoriteCityWeather(favoriteCityName: String): Completable {
        return favoriteCityDataSource.deleteFavoriteCityWeather(favoriteCityName)
    }

    override fun updateFavoriteCityWeather(favoriteCityWeather: FavoriteCityWeather): Completable {
        return favoriteCityDataSource.updateFavoriteCityWeather(favoriteCityWeather)
    }

    override fun getFavoriteCityWeather(favoriteCityName: String): Single<FavoriteCityWeather> {
        return favoriteCityDataSource.getFavoriteCityWeather(favoriteCityName)
    }

    override fun getAllFavoritesCityForecast(): Single<List<FavoriteCityForecast>> {
        return favoriteCityDataSource.getAllFavoritesCityForecast()
    }

    override fun insertFavoriteCityForecast(favoriteCityForecast: FavoriteCityForecast): Completable {
        return favoriteCityDataSource.insertFavoriteCityForecast(favoriteCityForecast)
    }

    override fun deleteFavoriteCityForecast(favoriteCityName: String): Completable {
        return favoriteCityDataSource.deleteFavoriteCityForecast(favoriteCityName)
    }

    override fun updateFavoriteCityForecast(favoriteCityForecast: FavoriteCityForecast): Completable {
        return favoriteCityDataSource.updateFavoriteCityForecast(favoriteCityForecast)
    }

    override fun getFavoriteCityForecast(favoriteCityName: String): Single<FavoriteCityForecast> {
        return favoriteCityDataSource.getFavoriteCityForecast(favoriteCityName)
    }
}
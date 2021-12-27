package nsu.timofeev.badweatherapp.data.favorite_weather

import nsu.timofeev.badweatherapp.weather.FavoriteCityForecast
import nsu.timofeev.badweatherapp.weather.FavoriteCityWeather
import io.reactivex.Completable
import io.reactivex.Single

class FavoriteCityLocalDataSource(private val favoriteCityWeatherDao: FavoriteCityWeatherDao, private val favoriteCityForecastDao: FavoriteCityForecastDao):
    FavoriteCityDataSource {

    override fun getAllFavoritesCityWeather(): Single<List<FavoriteCityWeather>> {
        return favoriteCityWeatherDao.getAll()
    }

    override fun insertFavoriteCityWeather(favoriteCityWeather: FavoriteCityWeather): Completable {
        return favoriteCityWeatherDao.insertCity(favoriteCityWeather)
    }

    override fun deleteFavoriteCityWeather(favoriteCityName: String): Completable {
        return favoriteCityWeatherDao.deleteCity(favoriteCityName)
    }

    override fun updateFavoriteCityWeather(favoriteCityWeather: FavoriteCityWeather): Completable {
        return favoriteCityWeatherDao.update(favoriteCityWeather)
    }

    override fun getFavoriteCityWeather(favoriteCityName: String): Single<FavoriteCityWeather> {
        return favoriteCityWeatherDao.get(favoriteCityName)
    }

    override fun getAllFavoritesCityForecast(): Single<List<FavoriteCityForecast>> {
        return favoriteCityForecastDao.getAll()
    }

    override fun insertFavoriteCityForecast(favoriteCityForecast: FavoriteCityForecast): Completable {
        return favoriteCityForecastDao.insertCity(favoriteCityForecast)
    }

    override fun deleteFavoriteCityForecast(favoriteCityName: String): Completable {
        return favoriteCityForecastDao.deleteCity(favoriteCityName)
    }

    override fun updateFavoriteCityForecast(favoriteCityForecast: FavoriteCityForecast): Completable {
        return favoriteCityForecastDao.update(favoriteCityForecast)
    }

    override fun getFavoriteCityForecast(favoriteCityName: String): Single<FavoriteCityForecast> {
        return favoriteCityForecastDao.get(favoriteCityName)
    }
}
package nsu.timofeev.badweatherapp.data.favorite_weather

import nsu.timofeev.badweatherapp.weather.FavoriteCityForecast
import nsu.timofeev.badweatherapp.weather.FavoriteCityWeather
import io.reactivex.Completable
import io.reactivex.Single

class FavoriteCityLocalDataSource(private val favoriteCityWeatherDao: FavoriteCityWeatherDao, private val favoriteCityForecastDao: FavoriteCityForecastDao):
    FavoriteCityDataSource {

    override fun getAllFavoritesCityWeather(): Single<List<FavoriteCityWeather>> = favoriteCityWeatherDao.getAll()
    override fun insertFavoriteCityWeather(favoriteCityWeather: FavoriteCityWeather): Completable = favoriteCityWeatherDao.insertCity(favoriteCityWeather)
    override fun deleteFavoriteCityWeather(favoriteCityName: String): Completable = favoriteCityWeatherDao.deleteCity(favoriteCityName)
    override fun updateFavoriteCityWeather(favoriteCityWeather: FavoriteCityWeather): Completable = favoriteCityWeatherDao.update(favoriteCityWeather)
    override fun getFavoriteCityWeather(favoriteCityName: String): Single<FavoriteCityWeather> = favoriteCityWeatherDao.get(favoriteCityName)


    override fun getAllFavoritesCityForecast(): Single<List<FavoriteCityForecast>> = favoriteCityForecastDao.getAll()
    override fun insertFavoriteCityForecast(favoriteCityForecast: FavoriteCityForecast): Completable = favoriteCityForecastDao.insertCity(favoriteCityForecast)
    override fun deleteFavoriteCityForecast(favoriteCityName: String): Completable = favoriteCityForecastDao.deleteCity(favoriteCityName)
    override fun updateFavoriteCityForecast(favoriteCityForecast: FavoriteCityForecast): Completable = favoriteCityForecastDao.update(favoriteCityForecast)
    override fun getFavoriteCityForecast(favoriteCityName: String): Single<FavoriteCityForecast> = favoriteCityForecastDao.get(favoriteCityName)
}
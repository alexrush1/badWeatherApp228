package nsu.timofeev.badweatherapp.di

import android.content.Context
import androidx.room.Room
import nsu.timofeev.badweatherapp.weather.FavoriteCityRepository
import nsu.timofeev.badweatherapp.weather.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nsu.timofeev.badweatherapp.data.current_weather.WeatherApi
import nsu.timofeev.badweatherapp.data.current_weather.WeatherDataSource
import nsu.timofeev.badweatherapp.data.current_weather.WeatherRemoteDataSourceImpl
import nsu.timofeev.badweatherapp.data.current_weather.WeatherRepositoryImpl
import nsu.timofeev.badweatherapp.data.favorite_weather.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Container {

    @Singleton
    @Provides
    fun providesCityRemoteDataSource(weatherApi: WeatherApi): WeatherDataSource =
            WeatherRemoteDataSourceImpl(weatherApi)

    @Singleton
    @Provides
    fun providesCityRepository(weatherDataSource: WeatherDataSource): WeatherRepository =
            WeatherRepositoryImpl(weatherDataSource)

    @Singleton
    @Provides
    fun providesFavoriteCityLocalDataSource(favoriteCityWeatherDao: FavoriteCityWeatherDao, favoriteCityForecastDao: FavoriteCityForecastDao): FavoriteCityDataSource =
        FavoriteCityLocalDataSource(favoriteCityWeatherDao, favoriteCityForecastDao)

    @Singleton
    @Provides
    fun providesFavoriteCityRepository(favoriteCityDataSource: FavoriteCityDataSource):
            FavoriteCityRepository = FavoriteCityRepositoryImpl(favoriteCityDataSource)

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            }).build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://api.openweathermap.org/data/2.5/")
            .build()

    @Singleton
    @Provides
    fun providesWeatherApi(retrofit: Retrofit): WeatherApi =
        retrofit.create(WeatherApi::class.java)

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext applicationContext: Context) =
        Room.databaseBuilder(
            applicationContext,
            FavoriteCityDatabase::class.java, "favorite_city_database"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun providesFavoriteCityWeatherDao(database: FavoriteCityDatabase) = database.getFavoriteCityWeatherDao()

    @Singleton
    @Provides
    fun providesFavoriteCityForecastDao(database: FavoriteCityDatabase) = database.getFavoriteCityForecastDao()
}
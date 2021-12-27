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
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Container {

    @Singleton
    @Provides
    fun providesCityRemoteDataSource(weatherApi: WeatherApi): WeatherDataSource {
        return WeatherRemoteDataSourceImpl(weatherApi)
    }

    @Singleton
    @Provides
    fun providesCityRepository(weatherDataSource: WeatherDataSource): WeatherRepository {
        return WeatherRepositoryImpl(weatherDataSource)
    }

    @Singleton
    @Provides
    fun providesFavoriteCityLocalDataSource(favoriteCityWeatherDao: FavoriteCityWeatherDao, favoriteCityForecastDao: FavoriteCityForecastDao): FavoriteCityDataSource {
       return FavoriteCityLocalDataSource(favoriteCityWeatherDao, favoriteCityForecastDao)
    }


    @Singleton
    @Provides
    fun providesFavoriteCityRepository(favoriteCityDataSource: FavoriteCityDataSource): FavoriteCityRepository {
        return FavoriteCityRepositoryImpl(favoriteCityDataSource)
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(
                OkHttpClient
                    .Builder()
                    .connectTimeout(500, TimeUnit.MILLISECONDS)
                    .callTimeout(500, TimeUnit.MILLISECONDS)
                    .readTimeout(500, TimeUnit.MILLISECONDS)
                    .readTimeout(500, TimeUnit.MILLISECONDS)
                    .addInterceptor(HttpLoggingInterceptor()
                        .also {
                it.level = HttpLoggingInterceptor.Level.BODY
            }).build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://api.openweathermap.org/data/2.5/")
            .build()
    }

    @Singleton
    @Provides
    fun providesWeatherApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext applicationContext: Context): FavoriteCityDatabase {
        return Room.databaseBuilder(
            applicationContext,
            FavoriteCityDatabase::class.java, "favorite_city_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesFavoriteCityWeatherDao(database: FavoriteCityDatabase): FavoriteCityWeatherDao {
        return database.getFavoriteCityWeatherDao()
    }

    @Singleton
    @Provides
    fun providesFavoriteCityForecastDao(database: FavoriteCityDatabase): FavoriteCityForecastDao {
        return database.getFavoriteCityForecastDao()
    }
}
package nsu.timofeev.badweatherapp.data.favorite_weather

import androidx.room.TypeConverter
import nsu.timofeev.badweatherapp.weather.weather_forecast_model.CityWeatherForecast
import com.google.gson.Gson

class CityWeatherForecastConverter {

    var gsonInstance = Gson()

    @TypeConverter
    fun stringToOutboxItem(string: String): CityWeatherForecast? {
        if (string.isEmpty())
            return null
        return gsonInstance.fromJson(string, CityWeatherForecast::class.java)
    }

    @TypeConverter
    fun outboxItemToString(outboxItem: CityWeatherForecast): String {
        return gsonInstance.toJson(outboxItem)
    }
}
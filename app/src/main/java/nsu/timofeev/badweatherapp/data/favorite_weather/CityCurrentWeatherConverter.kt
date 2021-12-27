package nsu.timofeev.badweatherapp.data.favorite_weather

import androidx.room.TypeConverter
import nsu.timofeev.badweatherapp.weather.current_weather_model.CityCurrentWeather
import com.google.gson.Gson

class CityCurrentWeatherConverter {

    var gsonInstance = Gson()

    @TypeConverter
    fun stringToOutboxItem(string: String): CityCurrentWeather? {
        if (string.isEmpty())
            return null
        return gsonInstance.fromJson(string, CityCurrentWeather::class.java)
    }

    @TypeConverter
    fun outboxItemToString(outboxItem: CityCurrentWeather): String {
        return gsonInstance.toJson(outboxItem)
    }
}
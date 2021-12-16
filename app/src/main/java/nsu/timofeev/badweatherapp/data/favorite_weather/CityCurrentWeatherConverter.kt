package nsu.timofeev.badweatherapp.data.favorite_weather

import android.text.TextUtils
import androidx.room.TypeConverter
import nsu.timofeev.badweatherapp.weather.current_weather_model.CityCurrentWeather
import com.google.gson.Gson

class CityCurrentWeatherConverter {
    @TypeConverter
    fun stringToOutboxItem(string: String): CityCurrentWeather? {
        if (TextUtils.isEmpty(string))
            return null
        return Gson().fromJson(string, CityCurrentWeather::class.java)
    }

    @TypeConverter
    fun outboxItemToString(outboxItem: CityCurrentWeather): String {
        return Gson().toJson(outboxItem)
    }
}
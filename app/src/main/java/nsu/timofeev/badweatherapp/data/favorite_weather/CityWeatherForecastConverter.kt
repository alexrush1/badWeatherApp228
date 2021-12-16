package nsu.timofeev.badweatherapp.data.favorite_weather

import android.text.TextUtils
import androidx.room.TypeConverter
import nsu.timofeev.badweatherapp.weather.weather_forecast_model.CityWeatherForecast
import com.google.gson.Gson

class CityWeatherForecastConverter {

    @TypeConverter
    fun stringToOutboxItem(string: String): CityWeatherForecast? {
        if (TextUtils.isEmpty(string))
            return null
        return Gson().fromJson(string, CityWeatherForecast::class.java)
    }

    @TypeConverter
    fun outboxItemToString(outboxItem: CityWeatherForecast): String {
        return Gson().toJson(outboxItem)
    }
}
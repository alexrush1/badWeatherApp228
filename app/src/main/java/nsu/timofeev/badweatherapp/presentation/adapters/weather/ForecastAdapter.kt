package nsu.timofeev.badweatherapp.presentation.adapters.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nsu.timofeev.badweatherapp.R
import nsu.timofeev.badweatherapp.databinding.ItemForecastBinding
import nsu.timofeev.badweatherapp.weather.weather_forecast_model.Forecast
import java.text.SimpleDateFormat
import java.util.*

class ForecastAdapter: RecyclerView.Adapter<ForecastAdapter.ForecastHolder>() {

    var cityForecast: List<Forecast> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemForecastBinding = ItemForecastBinding.inflate(layoutInflater, parent, false)
        return ForecastHolder(itemForecastBinding)
    }

    override fun onBindViewHolder(holder: ForecastHolder, position: Int) {
        holder.bind(cityForecast[position])
    }

    override fun getItemCount(): Int = cityForecast.count()

    class ForecastHolder(private val itemForecastBinding: ItemForecastBinding) :
        RecyclerView.ViewHolder(itemForecastBinding.root) {
        fun bind(forecast: Forecast) {

            val pattern = "dd/MM/yyyy HH:mm"
            val simpleDateFormat = SimpleDateFormat(pattern, Locale("en"))
            val date = simpleDateFormat.format(Date(forecast.dt * 1000))
            itemForecastBinding.dateText.text = date.toString()

            if (forecast.main.temp_max.toInt() == forecast.main.temp_min.toInt()){
                itemForecastBinding.minMaxTempText.text = itemView.context.getString(
                    R.string.temp_main_format,
                    (forecast.main.temp_max - 273).toInt().toString()
                )
            } else {
                itemForecastBinding.minMaxTempText.text = itemView.context.getString(
                    R.string.min_max_temp_format,
                    (forecast.main.temp_min - 273).toInt().toString(),
                    (forecast.main.temp_max - 273).toInt().toString()
                )
            }
        }
    }
}
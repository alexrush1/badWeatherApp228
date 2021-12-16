package nsu.timofeev.badweatherapp.presentation.adapters.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nsu.timofeev.badweatherapp.R
import nsu.timofeev.badweatherapp.databinding.ItemCurrentBinding
import nsu.timofeev.badweatherapp.weather.current_weather_model.CityCurrentWeather

class CurrentAdapter(private val onItemClick: (CityCurrentWeather) -> Unit) : RecyclerView.Adapter<CurrentAdapter.CurrentHolder>(){

    var cityCurrents: List<CityCurrentWeather> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemCityBinding = ItemCurrentBinding.inflate(layoutInflater, parent, false)
        return CurrentHolder(itemCityBinding, onItemClick)
    }

    override fun onBindViewHolder(holder: CurrentHolder, position: Int) {
        val city = cityCurrents[position]
        holder.bind(city)
    }

    override fun getItemCount(): Int = cityCurrents.count()

    class CurrentHolder(private val itemCityBinding: ItemCurrentBinding,
                        private val onItemClick: (CityCurrentWeather) -> Unit) : RecyclerView.ViewHolder(itemCityBinding.root) {

        fun bind(cityCurrent: CityCurrentWeather) {
            itemCityBinding.cityNameText.text = cityCurrent.name
            val tempFar = (cityCurrent.main.temp - 273).toInt()
            itemCityBinding.tempText.text = itemView.context.getString(R.string.temp_main_format, tempFar.toString())
            itemView.setOnClickListener { onItemClick(cityCurrent) }
        }
    }
}
package nsu.timofeev.badweatherapp.presentation.weather.list

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import nsu.timofeev.badweatherapp.databinding.ActivityWeatherListBinding
import nsu.timofeev.badweatherapp.weather.current_weather_model.CityCurrentWeather
import nsu.timofeev.badweatherapp.presentation.adapters.weather.CurrentAdapter
import nsu.timofeev.badweatherapp.presentation.weather.details.WeatherDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class WeatherListActivity : MvpAppCompatActivity(), ListView {

    @Inject
    lateinit var presenterProvider: Provider<WeatherListPresenter>
    private val weatherListPresenter by moxyPresenter { presenterProvider.get() }

    private val adapter = CurrentAdapter {
        weatherListPresenter.onCityClicked(it)
    }

    private lateinit var activityWeatherListBinding: ActivityWeatherListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        setContentView(activityWeatherListBinding.root)
    }

    private fun initViews() {
        activityWeatherListBinding = ActivityWeatherListBinding.inflate(layoutInflater)
        activityWeatherListBinding.findButton.setOnClickListener {
            val cityName = activityWeatherListBinding.cityEditText.text.toString()
            weatherListPresenter.openDetails(cityName)
        }
        activityWeatherListBinding.citiesList.adapter = adapter
        activityWeatherListBinding.citiesList.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        weatherListPresenter.onFavoritesList()
    }

    override fun cleanCitiesList() {
        adapter.cityCurrents = emptyList()
    }

    override fun bindCitiesList(cityCurrents: List<CityCurrentWeather>) {
        adapter.cityCurrents = cityCurrents
    }

    override fun bindOneCity(city: CityCurrentWeather) {
        val newList: MutableList<CityCurrentWeather> = mutableListOf()
        newList.addAll(adapter.cityCurrents)
        newList.add(city)
        adapter.cityCurrents = newList.toList()
    }

    override fun openDetails(cityName: String) {
        WeatherDetailsActivity.start(this, cityName)
    }

    override fun showError(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}
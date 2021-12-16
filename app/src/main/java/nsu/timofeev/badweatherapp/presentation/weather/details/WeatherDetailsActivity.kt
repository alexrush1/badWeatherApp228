package nsu.timofeev.badweatherapp.presentation.weather.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import nsu.timofeev.badweatherapp.R
import nsu.timofeev.badweatherapp.databinding.ActivityWeatherDetailsBinding
import nsu.timofeev.badweatherapp.weather.current_weather_model.CityCurrentWeather
import nsu.timofeev.badweatherapp.weather.weather_forecast_model.Forecast
import nsu.timofeev.badweatherapp.presentation.adapters.weather.ForecastAdapter
import dagger.hilt.android.AndroidEntryPoint
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class WeatherDetailsActivity : MvpAppCompatActivity(), DetailsView {

    @Inject
    lateinit var presenterProvider: Provider<WeatherDetailsPresenter>
    private val weatherDetailsPresenter by moxyPresenter { presenterProvider.get() }
    private lateinit var cityName: String

    private lateinit var activityWeatherDetailsBinding: ActivityWeatherDetailsBinding
    private val adapter = ForecastAdapter()

    companion object {
        private const val EXTRA_NAME = "EXTRA_NAME"

        fun start(context: Context, cityName: String) {
            val intent = Intent(context, WeatherDetailsActivity::class.java)
            intent.putExtra(EXTRA_NAME, cityName)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cityName = intent.getStringExtra(EXTRA_NAME)?.toLowerCase(Locale.ROOT) ?: "Unknown"
        initViews()
        setContentView(activityWeatherDetailsBinding.root)
        weatherDetailsPresenter.loadCity(cityName)
        weatherDetailsPresenter.loadForecast(cityName)
    }

    private fun initViews() {
        activityWeatherDetailsBinding = ActivityWeatherDetailsBinding.inflate(layoutInflater)
        activityWeatherDetailsBinding.backButton.setOnClickListener {
            weatherDetailsPresenter.goBack()
        }
        weatherDetailsPresenter.haveInFavorites(cityName)
        activityWeatherDetailsBinding.addToFavoriteButton.setOnCheckedChangeListener { _, isChecked ->
            weatherDetailsPresenter.onAddToFavoriteButtonClicked(cityName, !isChecked)
        }
    }

    override fun setIsCurrentLoading(isLoading: Boolean) {
        activityWeatherDetailsBinding.detailsProgressBar.isVisible = isLoading
        activityWeatherDetailsBinding.detailsLayout.isVisible = !isLoading
    }

    override fun setIsInFavorites(isFavorite: Boolean) {
        activityWeatherDetailsBinding.addToFavoriteButton.isChecked = isFavorite
    }

    override fun setIsForecastLoading(isLoading: Boolean) {
        activityWeatherDetailsBinding.forecastProgressBar.isVisible = isLoading
        activityWeatherDetailsBinding.forecastList.isVisible = !isLoading
    }

    override fun bindCity(cityCurrentWeather: CityCurrentWeather) {
        activityWeatherDetailsBinding.countryText.text =
            applicationContext.getString(
                R.string.country_format, cityCurrentWeather.sys.country
            )
        activityWeatherDetailsBinding.cityNameText.text = cityCurrentWeather.name
        activityWeatherDetailsBinding.tempText.text = applicationContext.getString(
            R.string.temp_format,
            weatherDetailsPresenter.convertTemp(cityCurrentWeather.main.temp).toString()
        )
        activityWeatherDetailsBinding.feelsLikeText.text = applicationContext.getString(
            R.string.feels_like_format,
            weatherDetailsPresenter.convertTemp(cityCurrentWeather.main.feels_like).toString()
        )
        activityWeatherDetailsBinding.humidText.text = applicationContext.getString(
            R.string.humid_format,
            cityCurrentWeather.main.humidity.toString()
        )
        activityWeatherDetailsBinding.condText.text = applicationContext.getString(
            R.string.cond_format,
            cityCurrentWeather.weather[0].description
        )
        activityWeatherDetailsBinding.forecastList.adapter = adapter
        activityWeatherDetailsBinding.forecastList.layoutManager = LinearLayoutManager(this)
    }

    override fun bindForecastList(forecastList: List<Forecast>) {
        adapter.cityForecast = forecastList
    }

    override fun goBack() {
        finish()
    }
}
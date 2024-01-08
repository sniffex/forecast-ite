// ui/WeatherActivity.kt
package kh.edu.rupp.ite.iteforecast.view.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import kh.edu.rupp.ite.iteforecast.R
import kh.edu.rupp.ite.iteforecast.model.WeatherResponse
import kh.edu.rupp.ite.iteforecast.data.WeatherRepository
import kh.edu.rupp.ite.iteforecast.network.WeatherService
import com.bumptech.glide.Glide
import kh.edu.rupp.ite.iteforecast.viewmodel.WeatherViewModel
import kh.edu.rupp.ite.iteforecast.viewmodel.WeatherViewModelFactory

class WeatherActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels {
        WeatherViewModelFactory(WeatherRepository(WeatherService.apiService))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)

        // Observe LiveData and update UI accordingly
        viewModel.weatherData.observe(this, Observer { weatherResponse ->
            updateUI(weatherResponse)
        })

//        viewModel.errorMessage.observe(this, Observer { errorMessage ->
//            // Handle error message
//        })

        // Trigger the API call
        viewModel.getWeather("London")

    }

    private fun updateUI(weatherResponse: WeatherResponse) {
        val current = weatherResponse.current
        val location = weatherResponse.location
        val condition = current.condition
        val lastUpdateDate : String = current.last_updated
        val conditionIcon= condition.icon
        val feelsLike = current.feelslike_c
        val uv = current.uv
        val wind = current.wind_kph
        val windDir = current.wind_dir

        findViewById<TextView>(R.id.locationText).text = location.name
        findViewById<TextView>(R.id.temperatureText).text = "${current.temp_c}"
        findViewById<TextView>(R.id.conditionText).text = condition.text
        findViewById<TextView>(R.id.humidityText).text = "Humidity: ${current.humidity}%"
        findViewById<ImageView>(R.id.conditionImage).setImageURI(conditionIcon)
        findViewById<TextView>(R.id.lastUpdatedText).text = "Last updated: $lastUpdateDate"
        findViewById<TextView>(R.id.feelsLikeTemp).text = "Feels like:$feelsLike°C"
        findViewById<TextView>(R.id.uvIndexText).text = "UV Index: $uv"
        findViewById<TextView>(R.id.windText).text = "Wind:$wind km/h"
        findViewById<TextView>(R.id.windDirText).text = "($windDir)"
    }
}

private fun ImageView.setImageURI(icon: String) {
    val fullUrl = "https:$icon"
    Glide.with(this)
        .load(fullUrl)
        .placeholder(R.drawable.ic_default_weather) // Default icon if loading fails
        .error(R.drawable.ic_default_weather) // Default icon if there's an error
        .into(this)
}



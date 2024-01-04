// ui/WeatherActivity.kt
package kh.edu.rupp.ite.iteforecast.ui

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kh.edu.rupp.ite.iteforecast.R
import kh.edu.rupp.ite.iteforecast.model.WeatherResponse
import kh.edu.rupp.ite.iteforecast.data.WeatherRepository
import kh.edu.rupp.ite.iteforecast.network.WeatherService
import kh.edu.rupp.ite.iteforecast.ui.WeatherViewModel
import kh.edu.rupp.ite.iteforecast.ui.WeatherViewModelFactory

class WeatherActivity : AppCompatActivity() {

    private val viewModel: WeatherViewModel by viewModels {
        WeatherViewModelFactory(WeatherRepository(WeatherService.apiService))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        // Observe LiveData and update UI accordingly
        viewModel.weatherData.observe(this, Observer { weatherResponse ->
            updateUI(weatherResponse)
        })

        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            // Handle error message
        })

        // Trigger the API call
        viewModel.getWeather("Delhi") // Replace with the desired city name
    }

    private fun updateUI(weatherResponse: WeatherResponse) {
        findViewById<TextView>(R.id.cityTextView).text = weatherResponse.name
        findViewById<TextView>(R.id.temperatureTextView).text = "${weatherResponse.main.temp} °C"
        findViewById<TextView>(R.id.weatherDescriptionTextView).text = weatherResponse.weather[0].description
        findViewById<TextView>(R.id.humidityTextView).text = "Humidity: ${weatherResponse.main.humidity}%"
    }
}

// ui/WeatherActivity.kt
package kh.edu.rupp.ite.iteforecast.ui

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

class WeatherActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels {
        WeatherViewModelFactory(WeatherRepository(WeatherService.apiService))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_card)

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
        val conditionIcon= condition.icon

        findViewById<TextView>(R.id.locationText).text = location.name
        findViewById<TextView>(R.id.temperatureTextView).text = "${current.temp_c}"
        findViewById<TextView>(R.id.weatherText).text = condition.text
        findViewById<TextView>(R.id.humidityText).text = "Humidity: ${current.humidity}%"
        findViewById<ImageView>(R.id.weatherIcon).setImageURI(conditionIcon)
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



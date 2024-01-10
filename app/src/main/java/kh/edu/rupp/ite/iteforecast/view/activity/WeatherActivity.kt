// ui/WeatherActivity.kt
package kh.edu.rupp.ite.iteforecast.view.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import kh.edu.rupp.ite.iteforecast.R
import kh.edu.rupp.ite.iteforecast.data.WeatherRepository
import kh.edu.rupp.ite.iteforecast.network.WeatherService
import kh.edu.rupp.ite.iteforecast.databinding.ActivityWeatherBinding
import kh.edu.rupp.ite.iteforecast.view.fragment.HomeFragment
import kh.edu.rupp.ite.iteforecast.view.fragment.LocationFragment
import kh.edu.rupp.ite.iteforecast.viewmodel.WeatherViewModel
import kh.edu.rupp.ite.iteforecast.viewmodel.WeatherViewModelFactory

class WeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherBinding
    private val viewModel: WeatherViewModel by viewModels {
        WeatherViewModelFactory(WeatherRepository(WeatherService.apiService))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.activityWeatherBottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.iconHome -> replaceFragment(HomeFragment())
                R.id.iconLocation -> replaceFragment(LocationFragment())

                else -> {

                }
            }

            true
        }

    }


    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.activityWeatherFragmentContainer, fragment)
        transaction.commit()
    }


}


//    private fun updateUI(weatherResponse: WeatherResponse) {
//        val current = weatherResponse.current
//        val location = weatherResponse.location
//        val condition = current.condition
//        val lastUpdateDate : String = current.last_updated
//        val conditionIcon= condition.icon
//        val feelsLike = current.feelslike_c
//        val uv = current.uv
//        val wind = current.wind_kph
//        val windDir = current.wind_dir
//
//        findViewById<TextView>(R.id.locationText).text = location.name
//        findViewById<TextView>(R.id.temperatureText).text = "${current.temp_c}"
//        findViewById<TextView>(R.id.conditionText).text = condition.text
//        findViewById<TextView>(R.id.humidityText).text = "Humidity: ${current.humidity}%"
//        findViewById<ImageView>(R.id.conditionImage).setImageURI(conditionIcon)
//        findViewById<TextView>(R.id.lastUpdatedText).text = "Last updated: $lastUpdateDate"
//        findViewById<TextView>(R.id.feelsLikeTemp).text = "Feels like:$feelsLike°C"
//        findViewById<TextView>(R.id.uvIndexText).text = "UV Index: $uv"
//        findViewById<TextView>(R.id.windText).text = "Wind:$wind km/h"
//        findViewById<TextView>(R.id.windDirText).text = "($windDir)"
//    }

//    private fun updateUI(weatherResponse: WeatherResponse) {
//        val current = weatherResponse.current
//        val location = weatherResponse.location
//        val condition = current.condition
//        val lastUpdateDate : String = current.last_updated
//        val conditionIcon= condition.icon
//        val feelsLike = current.feelslike_c
//        val uv = current.uv
//        val wind = current.wind_kph
//        val windDir = current.wind_dir
//
//        findViewById<TextView>(R.id.locationText).text = location.name
//        findViewById<TextView>(R.id.temperatureText).text = "${current.temp_c}"
//        findViewById<TextView>(R.id.conditionText).text = condition.text
//        findViewById<TextView>(R.id.humidityText).text = "Humidity: ${current.humidity}%"
//        findViewById<ImageView>(R.id.conditionImage).setImageURI(conditionIcon)
//        findViewById<TextView>(R.id.lastUpdatedText).text = "Last updated: $lastUpdateDate"
//        findViewById<TextView>(R.id.feelsLikeTemp).text = "Feels like:$feelsLike°C"
//        findViewById<TextView>(R.id.uvIndexText).text = "UV Index: $uv"
//        findViewById<TextView>(R.id.windText).text = "Wind:$wind km/h"
//        findViewById<TextView>(R.id.windDirText).text = "($windDir)"
//    }


//private fun ImageView.setImageURI(icon: String) {
//    val fullUrl = "https:$icon"
//    Glide.with(this)
//        .load(fullUrl)
//        .placeholder(R.drawable.ic_default_weather) // Default icon if loading fails
//        .error(R.drawable.ic_default_weather) // Default icon if there's an error
//        .into(this)
//}



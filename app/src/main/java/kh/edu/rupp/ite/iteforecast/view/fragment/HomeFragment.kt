package kh.edu.rupp.ite.iteforecast.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.bumptech.glide.Glide
import kh.edu.rupp.ite.iteforecast.R
import kh.edu.rupp.ite.iteforecast.data.WeatherRepository
import kh.edu.rupp.ite.iteforecast.databinding.FragmentHomeBinding
import kh.edu.rupp.ite.iteforecast.model.WeatherResponse
import kh.edu.rupp.ite.iteforecast.network.WeatherService
import kh.edu.rupp.ite.iteforecast.viewmodel.WeatherViewModelFactory
import kh.edu.rupp.ite.iteforecast.viewmodel.WeatherViewModel

class HomeFragment: Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: WeatherViewModel by viewModels {
        WeatherViewModelFactory(WeatherRepository(WeatherService.apiService))
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

            binding = FragmentHomeBinding.inflate(inflater, container, false)
            return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Example: Call the API for London when the fragment is created
        updateWeatherForLocation("London")
    }

    private fun updateUI(weatherResponse: WeatherResponse) {
        val current = weatherResponse.current
        val location = weatherResponse.location
        val condition = current.condition

        binding.locationText.text = location.name
        binding.temperatureText.text = "${current.temp_c}°C"
        binding.conditionText.text = condition.text
        binding.humidityText.text = "Humidity: ${current.humidity}%"

        // Use Glide to load the image asynchronously
        loadImageWithGlide(binding.conditionImage, condition.icon)

        binding.lastUpdatedText.text = "Last updated: ${current.last_updated}"
        binding.feelsLikeTemp.text = "Feels like: ${current.feelslike_c}°C"
        binding.uvIndexText.text = "UV Index: ${current.uv}"
        binding.windText.text = "Wind: ${current.wind_kph} km/h"
        binding.windDirText.text = "(${current.wind_dir})"
    }

    private fun updateWeatherForLocation(location: String) {
        // Observe the LiveData from the ViewModel
        viewModel.weatherData.observe(viewLifecycleOwner, Observer { weatherResponse ->
            updateUI(weatherResponse)
        })

        // Make the API call to get weather data for the specified location
        viewModel.getWeather(location)
    }

    private fun loadImageWithGlide(imageView: ImageView, icon: String) {
        val fullUrl = "https:$icon"
        Glide.with(this)
            .load(fullUrl)
            .placeholder(R.drawable.ic_default_weather) // Default icon if loading fails
            .error(R.drawable.ic_default_weather) // Default icon if there's an error
            .into(imageView)
    }

}

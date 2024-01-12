package kh.edu.rupp.ite.iteforecast.view.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.gms.location.LocationServices
import kh.edu.rupp.ite.iteforecast.R
import kh.edu.rupp.ite.iteforecast.adapter.HourlyForecastAdapter
import kh.edu.rupp.ite.iteforecast.databinding.FragmentHomeBinding
import kh.edu.rupp.ite.iteforecast.model.WeatherResponse
import kh.edu.rupp.ite.iteforecast.viewmodel.WeatherViewModel


class HomeFragment: Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: WeatherViewModel by viewModels ()
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
        // Request location permission and get location when the fragment is created
        requestLocationPermission()
        // Example: Call the API for London when the fragment is created
//        updateWeatherForLocation("London")
//        updateLocationByLonLat(48.8567,2.3508)
    }

    private fun updateUI(weatherResponse: List<WeatherResponse>) {
        val current = weatherResponse[0].current
        val location = weatherResponse[0].location
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

        binding.recyclerViewHourlyForecast.adapter = HourlyForecastAdapter(weatherResponse[0].forecast.forecastday[0].hour)

        binding.sunriseText.text = "Sunrise: ${weatherResponse[0].forecast.forecastday[0].astro.sunrise}"
        binding.sunsetText.text = "Sunset: ${weatherResponse[0].forecast.forecastday[0].astro.sunset}"
        binding.uvIndexText.text = "UV Index: ${weatherResponse[0].current.uv}"
        binding.rainText.text = "Rain: ${weatherResponse[0].forecast.forecastday[0].day.totalprecip_mm} mm"
    }

    private fun updateWeatherForLocation(location: String) {
        // Observe the LiveData from the ViewModel
        viewModel.weatherData.observe(viewLifecycleOwner, Observer { weatherResponse ->
            updateUI(weatherResponse)
        })

        // Make the API call to get weather data for the specified location
        viewModel.getWeather(location)
    }
//    private fun updateLocationByLonLat(lon: Double, lat: Double) {
//        viewModel.weatherData.observe(viewLifecycleOwner, Observer { weatherResponse ->
//            updateUI(weatherResponse)
//        })
//        viewModel.getWeatherByLonLat(lon, lat)
//    }

    private fun loadImageWithGlide(imageView: ImageView, icon: String) {
        val fullUrl = "https:$icon"
        Glide.with(this)
            .load(fullUrl)
            .placeholder(R.drawable.ic_default_weather) // Default icon if loading fails
            .error(R.drawable.ic_default_weather) // Default icon if there's an error
            .into(imageView)
    }

    private val locationPermissionCode = 123
    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
        } else {
            // Permission already granted, proceed to get location
            getLocation()
        }
    }

    private fun getLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        // Check if permission is granted, and if not, request it
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request the permission
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
        } else {
            // Permission already granted, proceed to get location
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    // Got last known location
                    if (location != null) {
                        val latitude = location.latitude
                        val longitude = location.longitude

                        // Now you can use these coordinates to fetch weather data
                        updateWeatherForLocation("$latitude,$longitude")
                    } else {
                        // Handle the case where location is null even after permission is granted
                        Log.e("Location", "Last known location is null")
                    }
                }
                .addOnFailureListener { e ->
                    // Handle failure
                    Log.e("Location", "Error getting location: ${e.message}")
                }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == locationPermissionCode) {
            // Check if the permission was granted
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed to get location
                getLocation()
            } else {
                // Permission denied, handle accordingly
                // You may want to show a message to the user or provide an alternative
            }
        }
    }

}

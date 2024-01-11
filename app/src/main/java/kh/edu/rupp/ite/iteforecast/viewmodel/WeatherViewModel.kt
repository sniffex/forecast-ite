// WeatherViewModel.kt
package kh.edu.rupp.ite.iteforecast.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kh.edu.rupp.ite.iteforecast.model.WeatherResponse
import kh.edu.rupp.ite.iteforecast.service.WeatherApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.launch

object WeatherService {
    private const val BASE_URL = "https://api.weatherapi.com/v1/"
    const val API_KEY = "e3b9a8628c0e4ac7a5134107240801"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: WeatherApiService = retrofit.create(WeatherApiService::class.java)
}

class WeatherViewModel : ViewModel() {

    private val _weatherData = MutableLiveData<List<WeatherResponse>>()
    val weatherData: LiveData<List<WeatherResponse>>
        get() = _weatherData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun getWeather(cityName: String) {
        viewModelScope.launch {
            try {
                val response = getCurrentWeather(cityName)
                Log.d("WeatherViewModel", "API Response: $response")
                showToast("API Response: $response")

                val newList = (_weatherData.value ?: emptyList()) + response
                _weatherData.value = newList

            } catch (e: Exception) {
                Log.e("WeatherViewModel", "API Error: ${e.message}", e)
                _errorMessage.postValue("Error fetching weather data")
                showToast("Error fetching weather data")
            }
        }
    }

    private fun showToast(message: String) {
        _errorMessage.postValue(message)
    }
}

// Add the following extension function outside the WeatherViewModel class
suspend fun getCurrentWeather(cityName: String): WeatherResponse {
    return WeatherService.apiService.getCurrentWeather(cityName, WeatherService.API_KEY)
}

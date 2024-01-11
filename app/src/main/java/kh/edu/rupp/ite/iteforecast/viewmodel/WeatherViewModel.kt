package kh.edu.rupp.ite.iteforecast.viewmodel

// WeatherViewModel.kt
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kh.edu.rupp.ite.iteforecast.data.WeatherRepository
import kh.edu.rupp.ite.iteforecast.model.WeatherResponse
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _weatherData = MutableLiveData<List<WeatherResponse>>()
    val weatherData: LiveData<List<WeatherResponse>>
        get() = _weatherData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun getWeather(cityName: String) {
        viewModelScope.launch{
            try {
                val response = repository.getCurrentWeather(cityName)
                Log.d("WeatherViewModel", "API Response: $response")

                val newList = (_weatherData.value ?: emptyList()) + response
                _weatherData.value = newList

            } catch (e: Exception) {
                Log.e("WeatherViewModel", "API Error: ${e.message}", e)
                _errorMessage.postValue("Error fetching weather data")
            }
        }
    }
    fun getWeatherByLonLat(lon: Double, lat: Double) {
        viewModelScope.launch {
            try {
//                val apiKey = "YOUR_API_KEY"  // Replace with your actual API key
//                val apiUrl = "http://api.weatherapi.com/v1/current.json?key=$apiKey&q=$lon,$lat&aqi=no"

                val response = repository.getCurrentWeatherByLonLat(lon, lat)
                Log.d("WeatherViewModel", "API Response: $response")

                val newList = (_weatherData.value ?: emptyList()) + response
                _weatherData.value = newList
            } catch (e: Exception) {
                Log.e("WeatherViewModel", "API Error: ${e.message}", e)
                _errorMessage.postValue("Error fetching weather data")
            }
        }
    }

}

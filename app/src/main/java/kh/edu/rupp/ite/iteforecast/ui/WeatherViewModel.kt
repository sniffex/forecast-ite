package kh.edu.rupp.ite.iteforecast.ui

// WeatherViewModel.kt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kh.edu.rupp.ite.iteforecast.model.WeatherResponse
import kh.edu.rupp.ite.iteforecast.data.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse>
        get() = _weatherData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun getWeather(cityName: String) {
        viewModelScope.launch {
            try {
                val response = repository.getWeather(cityName)
                _weatherData.value = response
            } catch (e: Exception) {
                _errorMessage.value = "Error fetching weather data"
            }
        }
    }
}

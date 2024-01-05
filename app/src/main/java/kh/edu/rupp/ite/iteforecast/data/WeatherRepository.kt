package kh.edu.rupp.ite.iteforecast.data

import kh.edu.rupp.ite.iteforecast.model.WeatherResponse

class WeatherRepository (private val apiService: WeatherApiService) {
    suspend fun getCurrentWeather(cityName: String): WeatherResponse {
        return apiService.getCurrentWeather(cityName, "d3265951425647d4951114047230310")
    }
}
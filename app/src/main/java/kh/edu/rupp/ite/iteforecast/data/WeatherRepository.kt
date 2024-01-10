package kh.edu.rupp.ite.iteforecast.data

import kh.edu.rupp.ite.iteforecast.model.WeatherResponse

class WeatherRepository (private val apiService: WeatherApiService) {
    suspend fun getWeather(cityName: String): WeatherResponse {
        return apiService.getCurrentWeather(cityName, "ce72f30e8a35d834c7ce86aed5dbec2c")
    }
}
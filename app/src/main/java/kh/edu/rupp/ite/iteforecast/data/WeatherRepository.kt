package kh.edu.rupp.ite.iteforecast.data

import kh.edu.rupp.ite.iteforecast.model.WeatherResponse

class WeatherRepository (private val apiService: WeatherApiService) {
    private val api_key = "e3b9a8628c0e4ac7a5134107240801"
    suspend fun getCurrentWeather(cityName: String): WeatherResponse {
        return apiService.getCurrentWeather(cityName, api_key)
    }
    suspend fun getCurrentWeatherByLonLat(lon: Double, lat: Double): WeatherResponse {
        val location = "$lon,$lat"
        return apiService.getCurrentWeather(location, api_key)
    }
}
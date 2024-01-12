package kh.edu.rupp.ite.iteforecast.service

import kh.edu.rupp.ite.iteforecast.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("forecast.json")
    suspend fun getCurrentWeather(
        @Query("q") cityName: String,
        @Query("key") apiKey: String,
        @Query("aqi") includeAqi: String = "no"
    ): WeatherResponse
    suspend fun getHourlyForecast(
        @Query("key") apiKey: String,
        @Query("q") cityName: String,
        @Query("days") days: Int = 1,
        @Query("aqi") includeAqi: String = "no",
        @Query("alerts") includeAlerts: String = "no"
    ): WeatherResponse

}



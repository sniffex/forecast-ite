package kh.edu.rupp.ite.iteforecast.data

import kh.edu.rupp.ite.iteforecast.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query
interface WeatherApiService {
    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("q") cityName: String,
        @Query("key") apiKey: String,
        @Query("aqi") includeAqi: String = "no"
    ): WeatherResponse
}
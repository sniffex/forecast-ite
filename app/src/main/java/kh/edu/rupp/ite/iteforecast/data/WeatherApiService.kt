package kh.edu.rupp.ite.iteforecast.data

import kh.edu.rupp.ite.iteforecast.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query
interface WeatherApiService {
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String,
    ): WeatherResponse
}
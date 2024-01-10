package kh.edu.rupp.ite.iteforecast.network

import kh.edu.rupp.ite.iteforecast.data.WeatherApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherService {
    private const val BASE_URL = "https://api.weatherapi.com/v1/"
    private const val API_KEY = "e3b9a8628c0e4ac7a5134107240801"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: WeatherApiService = retrofit.create(WeatherApiService::class.java)
}
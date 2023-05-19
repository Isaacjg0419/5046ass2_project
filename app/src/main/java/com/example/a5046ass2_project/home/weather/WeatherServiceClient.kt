package com.example.a5046ass2_project.home.weather

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherServiceClient {
    companion object RetrofitManager {
        //        get response of url from google search api
        private val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        fun getWeatherService(): WeatherService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(WeatherService::class.java)
        }
    }
}
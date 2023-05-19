package com.example.a5046ass2_project.home.weather

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather/")
    suspend fun getCityData(
        @Query("q") q:String,
        @Query("appid") appId:String = "e97a67b1664cf6ed95b9f6da3415f59e"
    ): Response<City>
}
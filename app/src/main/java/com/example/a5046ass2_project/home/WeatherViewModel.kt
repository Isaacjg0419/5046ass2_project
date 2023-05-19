package com.example.a5046ass2_project.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.a5046ass2_project.home.weather.City
import com.example.a5046ass2_project.home.weather.WeatherServiceClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke
import kotlinx.coroutines.launch
import retrofit2.Response

class WeatherViewModel(application: Application) : AndroidViewModel(application) {
    private val city: MutableLiveData<City> = MutableLiveData()

    val getCity: LiveData<City>
        get() = city

    fun getWeatherInfo() {
        val weatherService = WeatherServiceClient.getWeatherService()
        viewModelScope.launch {
            (Dispatchers.IO) {
                val responseData: Response<City> =
                    weatherService.getCityData(
                        q = "Melbourne"
                    )
                if (responseData.isSuccessful) {
                    val cityRes = responseData.body()!!
                    city.postValue(cityRes)
                } else {
                    Log.i("Error ", "Weather API Response failed")
                }
            }
        }
    }
}
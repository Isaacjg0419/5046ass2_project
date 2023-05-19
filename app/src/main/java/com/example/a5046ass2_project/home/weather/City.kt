package com.example.a5046ass2_project.home.weather

data class Main(
    val temp:Double = 0.0,
    val humidity:Int = 0
)

data class City(
    val weather:List<Weather>,
    val main: Main,
    val wind: Wind,
    val name:String = ""
)

data class Weather(
    val description:String = "",
    val icon:String = ""
)

data class Wind (
    val speed:Float =0.0f,
    val deg:Int = 0
)

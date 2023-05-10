package com.example.a5046ass2_project.Map


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.URL

@Entity(tableName = "properties")
data class Property(
    @PrimaryKey
    val id: Long = 0,
    val address: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val property_type:String,
    val price:Int,
    val room_count:Int,
    val postcode:Int,
    val url:String
)
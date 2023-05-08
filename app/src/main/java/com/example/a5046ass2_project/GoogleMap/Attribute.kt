package com.example.a5046ass2_project.GoogleMap

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "attributes")
data class Attribute(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val latitude: Double,
    val longitude: Double
)


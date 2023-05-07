package com.example.a5046ass2_project.Room.entity
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Customer (
    @ColumnInfo(name = "first_name") var firstName: String?,
    @ColumnInfo(name = "last_name") var lastName: String?,
    @ColumnInfo(name = "salary") var salary: Double?,
    @PrimaryKey(autoGenerate = true) var uid: Int=0
)
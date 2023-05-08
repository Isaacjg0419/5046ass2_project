package com.example.a5046ass2_project.GoogleMap

import androidx.room.Database

import androidx.room.RoomDatabase



@Database(entities = [Attribute::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun attributeDao(): AttributeDao
}


package com.example.a5046ass2_project.Map

import android.content.Context
import androidx.room.Database
import androidx.room.Room

import androidx.room.RoomDatabase



@Database(entities = [Property::class], version = 3, exportSchema = false)
abstract class PropertyDatabase : RoomDatabase() {
    abstract fun propertyDAO():PropertyDAO

    companion object {
        @Volatile
        private var instance: PropertyDatabase? = null


        fun getInstance(context: Context): PropertyDatabase {
            if (instance == null) {
                synchronized(PropertyDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PropertyDatabase::class.java,
                        "app_database"
                    ).fallbackToDestructiveMigration().build()

                }
            }
            return instance!!
        }
    }
}



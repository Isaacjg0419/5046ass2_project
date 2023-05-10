package com.example.a5046ass2_project.Map

import android.content.Context
import androidx.room.Database
import androidx.room.Room

import androidx.room.RoomDatabase



@Database(entities = [Property::class], version = 3, exportSchema = false)
abstract class PropertyDabase : RoomDatabase() {
    abstract fun propertyDAO():PropertyDAO

    companion object {
        @Volatile
        private var instance: PropertyDabase? = null


        fun getInstance(context: Context): PropertyDabase {
            if (instance == null) {
                synchronized(PropertyDabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PropertyDabase::class.java,
                        "app_database"
                    ).fallbackToDestructiveMigration().build()

                }
            }
            return instance!!
        }
    }
}



package com.example.a5046ass2_project.map
import android.content.Context
import androidx.room.Database
import androidx.room.Room

import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.a5046ass2_project.profile.Converters
import com.example.a5046ass2_project.profile.ProfileDAO
import com.example.a5046ass2_project.profile.UserProfile

@Database(entities = [Property::class, UserProfile::class], version = 7, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PropertyDatabase : RoomDatabase() {
    abstract fun propertyDAO():PropertyDAO
    abstract fun profileDAO(): ProfileDAO
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
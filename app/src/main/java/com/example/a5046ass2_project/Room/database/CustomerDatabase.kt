package com.example.a5046ass2_project.Room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.a5046ass2_project.Room.dao.CustomerDAO
import com.example.a5046ass2_project.Room.entity.Customer

class CustomerDatabase {
    @Database(entities = arrayOf(Customer::class), version = 1, exportSchema = false)
    public abstract class CustomerDatabase : RoomDatabase() {
        abstract fun customerDao(): CustomerDAO

        companion object {
            //// Volatile annotation means any writes to this method will be visible to other
//            threads and guarantees visibility of changes to variables across threads
            @Volatile
            private var INSTANCE: CustomerDatabase? = null
            fun getInstance(context: Context): CustomerDatabase {
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        CustomerDatabase::class.java,
                        "CustomerDatabase"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                    instance
                }
            }
        }
    }
}
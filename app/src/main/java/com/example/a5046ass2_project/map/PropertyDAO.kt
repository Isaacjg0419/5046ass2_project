package com.example.a5046ass2_project.map

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.*

@Dao

interface PropertyDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(property: Property)

    @Query("SELECT * FROM properties")
    suspend fun getAllAttributes(): List<Property>

    @Query("SELECT * FROM properties WHERE postcode = :postcode")
    suspend fun getPropertiesByPostcode(postcode: Int): List<Property>

    @Query("SELECT * FROM properties WHERE id = :id LIMIT 1")
    suspend fun getPropertyByMarkerId(id: Long): Property?

    @Query("SELECT * FROM properties WHERE publish_date >= :startDate and publish_date <= :endDate")
    suspend fun getPropertiesByPeriod(startDate: Date, endDate: Date): List<Property>
}



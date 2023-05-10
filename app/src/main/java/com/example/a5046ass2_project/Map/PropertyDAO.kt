package com.example.a5046ass2_project.Map

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

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
}



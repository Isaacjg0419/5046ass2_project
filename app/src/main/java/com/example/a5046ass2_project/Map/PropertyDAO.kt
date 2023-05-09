package com.example.a5046ass2_project.Map

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PropertyDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(property: Property)

    @Query("SELECT * FROM properties")
    fun getAllAttributes(): List<Property>
//search by postcode
    @Query("SELECT * FROM properties WHERE postcode = :postcode")
    fun getPropertiesByPostcode(postcode: Int): List<Property>
}
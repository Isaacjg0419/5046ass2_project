package com.example.a5046ass2_project.GoogleMap

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy




@Dao
interface AttributeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(attribute: Attribute)

    @Query("SELECT * FROM attributes")
    fun getAllAttributes(): List<Attribute>
}



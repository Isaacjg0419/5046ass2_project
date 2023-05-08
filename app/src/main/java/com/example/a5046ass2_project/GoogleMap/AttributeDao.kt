package com.example.a5046ass2_project.GoogleMap

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface AttributeDao {
    @Query("SELECT * FROM attributes")
    fun getAllAttributes():LiveData< List<Attribute>>

    @Insert
    fun insertAttribute(attribute: Attribute)

    @Delete
    fun deleteAttribute(attribute: Attribute)
}


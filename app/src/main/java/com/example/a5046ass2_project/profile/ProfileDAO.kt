package com.example.a5046ass2_project.profile


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProfileDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userProfile: UserProfile)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(userProfile: UserProfile)

    @Query("SELECT * FROM UserProfile WHERE email = :email LIMIT 1")
    suspend fun getProfileByEmail(email: String): UserProfile?
}
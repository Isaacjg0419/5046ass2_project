package com.example.a5046ass2_project.Room.dao


import androidx.room.*
import com.example.a5046ass2_project.Room.entity.Customer
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDAO {
    @Query("SELECT * FROM customer ORDER BY last_name ASC")
    fun getAll(): Flow<List<Customer>>
    @Query("SELECT * FROM customer WHERE uid = :customerId LIMIT 1")
    suspend fun findByID(customerId: Int): Customer?
    @Insert
    suspend fun insert(customer: Customer)
    @Delete
    suspend fun delete(customer: Customer)
    @Update
    suspend fun update(customer: Customer)
    @Query("DELETE FROM customer")
    suspend fun deleteAll()
}
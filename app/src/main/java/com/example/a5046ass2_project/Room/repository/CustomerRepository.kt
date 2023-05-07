package com.example.a5046ass2_project.Room.repository

import android.app.Application
import com.example.a5046ass2_project.Room.dao.CustomerDAO
import com.example.a5046ass2_project.Room.database.CustomerDatabase
import com.example.a5046ass2_project.Room.entity.Customer
import kotlinx.coroutines.flow.Flow

class CustomerRepository(application: Application) {
    private var customerDao: CustomerDAO =
        CustomerDatabase.CustomerDatabase.getInstance(application).customerDao()
    //allcustomers public property so it can be called from viewmodel
    val allcustomers: Flow<List<Customer>> = customerDao.getAll()

    suspend fun findById(customerId: Int): Customer? {
        return customerDao.findByID(customerId)
    }

    suspend fun insert(customer: Customer) {
        customerDao.insert(customer)
    }
    suspend fun delete(customer: Customer) {
        customerDao.delete(customer)
    }
    suspend fun deleteAll() {
        customerDao.deleteAll()
    }
    suspend fun update(customer: Customer) {
        customerDao.update(customer)
    }
}
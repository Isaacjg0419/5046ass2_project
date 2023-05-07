package com.example.a5046ass2_project.Room.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.a5046ass2_project.Room.entity.Customer
import com.example.a5046ass2_project.Room.repository.CustomerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CustomerViewModel(application: Application): AndroidViewModel(application) {
    private val cRepository: CustomerRepository
    init{
        cRepository = CustomerRepository(application)
    }
    var allCustomers: LiveData<List<Customer>> = cRepository.allcustomers.asLiveData()
    fun findCustomerById(id: Int): LiveData<Customer?> = liveData{
        emit(cRepository.findById(id))
    }
    fun insert(customer: Customer) = viewModelScope.launch(Dispatchers.IO){
        cRepository.insert(customer)
    }
    fun delete(customer: Customer) = viewModelScope.launch(Dispatchers.IO) {
        cRepository.delete(customer)
    }
    fun deleteAll() = viewModelScope.launch (Dispatchers.IO) {
        cRepository.deleteAll()
    }
    fun update(customer: Customer) =
        viewModelScope.launch (Dispatchers.IO) {
            cRepository.update(customer)
        }
}



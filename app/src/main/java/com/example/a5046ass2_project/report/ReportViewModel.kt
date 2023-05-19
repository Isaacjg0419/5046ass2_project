package com.example.a5046ass2_project.report

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.a5046ass2_project.Map.Property
import com.example.a5046ass2_project.Map.PropertyDAO
import com.example.a5046ass2_project.Map.PropertyDatabase
import com.example.a5046ass2_project.map.Property
import com.example.a5046ass2_project.map.PropertyDAO
import com.example.a5046ass2_project.map.PropertyDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ReportViewModel(application: Application) : AndroidViewModel(application) {

    private var propertyDAO: PropertyDAO = PropertyDatabase.getInstance(application).propertyDAO()
    private var propertyList: MutableLiveData<List<Property>> = MutableLiveData()

    fun getDateFromRoom() {
        CoroutineScope(Dispatchers.IO).launch {
            val properties = propertyDAO.getAllAttributes()
            propertyList.postValue(properties)
        }
    }

    fun getPropertyList(): LiveData<List<Property>> {
        return propertyList
    }

    fun getPropertyListByPeriod(startDate: Date, endDate: Date) {
        CoroutineScope(Dispatchers.IO).launch {
            val properties = propertyDAO.getPropertiesByPeriod(startDate, endDate)
            propertyList.postValue(properties)
        }
    }
}
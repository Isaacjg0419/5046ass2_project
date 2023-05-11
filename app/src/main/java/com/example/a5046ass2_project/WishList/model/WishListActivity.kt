package com.example.a5046ass2_project.WishList.model

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a5046ass2_project.Map.Property
import com.example.a5046ass2_project.R
import com.example.a5046ass2_project.WishList.adapter.PropertyAdapter




class WishListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var propertyAdapter: PropertyAdapter
    private lateinit var properties: MutableList<Property>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wish_list)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        properties = getProperties().toMutableList()
        propertyAdapter = PropertyAdapter(properties)
        recyclerView.adapter = propertyAdapter
    }

    private fun getProperties(): List<Property> {
        val propertyList = mutableListOf(
            Property(1, "Address 1", "Description 1", 0.0, 0.0, "Type 1", 100000, 3, 12345, "URL 1"),
            Property(2, "Address 2", "Description 2", 0.0, 0.0, "Type 2", 200000, 4, 54321, "URL 2"),
            Property(3, "Address 3", "Description 3", 0.0, 0.0, "Type 3", 300000, 2, 67890, "URL 3")
        )
        return propertyList
    }
}



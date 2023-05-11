package com.example.a5046ass2_project.WishList.model

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a5046ass2_project.Map.Property
import com.example.a5046ass2_project.Map.PropertyDatabase
import com.example.a5046ass2_project.R
import com.example.a5046ass2_project.WishList.adapter.PropertyAdapter
import com.example.a5046ass2_project.WishList.adapter.WishlistManager
import com.example.a5046ass2_project.WishList.adapter.WishlistManager.getPropertyList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class WishlistActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var propertyAdapter: PropertyAdapter
    private lateinit var wishlistManager: WishlistManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wish_list)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        wishlistManager = WishlistManager

        CoroutineScope(Dispatchers.Main).launch {
            val propertyList = getProperties()
            wishlistManager.addPropertyList(propertyList)

            propertyAdapter = PropertyAdapter(wishlistManager.getPropertyList())
            recyclerView.adapter = propertyAdapter
        }
    }

    private suspend fun getProperties(): List<Property> {
        val propertyList = mutableListOf<Property>()

        // 从 Intent 中获取传递的 propertyId
        val propertyId = intent.getLongExtra("propertyId", 0)

        // 使用 propertyId 从数据库中获取相应的属性信息
        val property = getPropertyById(propertyId)

        // 如果获取到属性信息，则添加到属性列表中
        if (property != null) {
            propertyList.add(property)
        }

        return propertyList
    }

    private suspend fun getPropertyById(propertyId: Long): Property? {
        val propertyDAO = PropertyDatabase.getInstance(applicationContext).propertyDAO()
        return propertyDAO.getPropertyByMarkerId(propertyId)
    }
}












package com.example.a5046ass2_project.WishList.adapter

import com.example.a5046ass2_project.Map.Property

object WishlistManager {
    private val properties: MutableList<Property> = mutableListOf()

    fun addProperty(property: Property) {
        properties.add(property)
    }

    fun removeProperty(property: Property) {
        properties.remove(property)
    }

    fun getPropertyList(): MutableList<Property> {
        return properties
    }
    fun addPropertyList(propertyList: List<Property>) {
        properties.addAll(propertyList)
    }
}



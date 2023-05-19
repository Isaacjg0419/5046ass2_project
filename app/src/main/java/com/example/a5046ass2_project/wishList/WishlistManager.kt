package com.example.a5046ass2_project.wishList

import com.example.a5046ass2_project.map.Property

object WishlistManager {
    private val properties: MutableList<Property> = mutableListOf()

    fun addProperty(property: Property) {
        if (property !in properties) {
            properties.add(property)
        }
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



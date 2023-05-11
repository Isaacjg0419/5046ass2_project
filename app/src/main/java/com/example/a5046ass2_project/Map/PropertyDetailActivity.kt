package com.example.a5046ass2_project.Map

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.a5046ass2_project.R
import com.example.a5046ass2_project.WishList.model.WishlistActivity
import com.google.android.material.snackbar.Snackbar

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PropertyDetailActivity : AppCompatActivity() {

    private lateinit var propertyDAO: PropertyDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_detail)

        propertyDAO = PropertyDatabase.getInstance(applicationContext).propertyDAO()

        val propertyId = intent.getLongExtra("id", 0)

        CoroutineScope(Dispatchers.IO).launch {
            val property = propertyDAO.getPropertyByMarkerId(propertyId)

            withContext(Dispatchers.Main) {
                if (property != null) {
                    renderPropertyDetail(property)

                    val addToWishlistButton: Button = findViewById(R.id.addToWishlistButton)
                    addToWishlistButton.setOnClickListener {
                        addToWishlist(property)
                    }
                }
            }
        }
    }

    private fun renderPropertyDetail(property: Property) {
        val addressTextView: TextView = findViewById(R.id.addressTextView)
        val priceTextView: TextView = findViewById(R.id.priceTextView)
        val roomCountTextView: TextView = findViewById(R.id.roomCountTextView)
        val descriptionTextView: TextView = findViewById(R.id.descriptionTextView)
        val postcodeTextView: TextView = findViewById(R.id.postcodeTextView)

        addressTextView.text = property.address
        postcodeTextView.text = property.postcode.toString()
        priceTextView.text = "The rental price is ${property.price.toString()} per week"
        roomCountTextView.text = "This property has ${property.room_count.toString()} rooms"
        descriptionTextView.text = "Introduction:\n${property.description}"
    }

    private fun addToWishlist(property: Property) {
        val intent = Intent(this, WishlistActivity::class.java)
        intent.putExtra("property", property)
        startActivity(intent)
    }
}







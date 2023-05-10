package com.example.a5046ass2_project.Map

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.a5046ass2_project.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var googleMap: GoogleMap
    private lateinit var propertyDAO: PropertyDAO
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)



        propertyDAO = PropertyDabase.getInstance(applicationContext).propertyDAO()

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
// search view by post code
        searchView = findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchMarkersByPostcode(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isBlank()) {
                    showAllMarkers()
                }
                return false
            }
        })
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.uiSettings.isZoomControlsEnabled = true

        val monashClaytonLatLng = LatLng(-37.915, 145.135)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(monashClaytonLatLng, 14f))
        loadJsonDataAndRenderMarkers()
    }

    private fun loadJsonDataAndRenderMarkers() {
        val inputStream = assets.open("property_data.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }

        val jsonArray = JSONArray(jsonString)

        CoroutineScope(Dispatchers.IO).launch {
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val address = jsonObject.getString("address")
                val description = jsonObject.getString("description")
                val latitude = jsonObject.getDouble("latitude")
                val longitude = jsonObject.getDouble("longitude")
                val property_type = jsonObject.getString("property_type")
                val price = jsonObject.getInt("price")
                val room_count = jsonObject.getInt("room_count")
                val postcode = jsonObject.getInt("postcode")

                val latLng = LatLng(latitude, longitude)
                val markerOptions =
                    MarkerOptions().position(latLng).title(description).snippet(address)

                withContext(Dispatchers.Main) {
                    val marker = googleMap.addMarker(markerOptions)
                    setMarkerClickListener(marker)
                }

                val property = Property(
                    id = i.toLong(),
                    address = address,
                    description = description,
                    latitude = latitude,
                    longitude = longitude,
                    property_type = property_type,
                    price = price,
                    room_count = room_count,
                    postcode = postcode,
                    url = ""
                )
                propertyDAO.insert(property)

            }
        }
    }


    private fun setMarkerClickListener(marker: Marker?) {
        googleMap.setOnMarkerClickListener { clickedMarker ->
            val intent = Intent(this@MapsActivity, PropertyDetailActivity::class.java)
            intent.putExtra("id", clickedMarker.id)
            startActivity(intent)
            true
        }
    }


    //search method
    private fun searchMarkersByPostcode(postcode: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val properties = propertyDAO.getPropertiesByPostcode(postcode.toInt())

            withContext(Dispatchers.Main) {
                googleMap.clear()

                for (property in properties) {
                    val latLng = LatLng(property.latitude, property.longitude)
                    val markerOptions = MarkerOptions().position(latLng).title(property.description)
                        .snippet(property.address)
                    googleMap.addMarker(markerOptions)
                }
            }
        }
    }

    // when search bar is empty or close, show all markers
    private fun showAllMarkers() {
        CoroutineScope(Dispatchers.IO).launch {
            val properties = propertyDAO.getAllAttributes()

            withContext(Dispatchers.Main) {
                googleMap.clear()

                for (property in properties) {
                    val latLng = LatLng(property.latitude, property.longitude)
                    val markerOptions = MarkerOptions().position(latLng).title(property.description)
                        .snippet(property.address)
                    googleMap.addMarker(markerOptions)
                }
            }
        }
    }

}
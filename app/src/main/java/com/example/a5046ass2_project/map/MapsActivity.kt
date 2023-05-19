package com.example.a5046ass2_project.map

import android.content.Intent
import android.os.Bundle

import android.widget.SearchView

import androidx.appcompat.app.AppCompatActivity

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

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat

import com.google.android.gms.location.*

import com.google.android.gms.maps.model.BitmapDescriptorFactory


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var googleMap: GoogleMap
    private lateinit var propertyDAO: PropertyDAO
    private lateinit var searchView: SearchView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var currentLocationMarker: Marker? = null

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1001
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        propertyDAO = PropertyDatabase.getInstance(applicationContext).propertyDAO()

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

//        geet and add the current location
        addCurrentLocationMarker()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                ask for current location access
                addCurrentLocationMarker()
//                if user denied the access
                Toast.makeText(
                    this,
                    "require permission to get current location",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_REQUEST_CODE
        )
    }


    private fun addCurrentLocationMarker() {
        val markerIcon = BitmapDescriptorFactory.fromResource(R.drawable.current_location)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
//                check get the current location successfully

                if (location != null) {
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    currentLocationMarker?.remove() // remove previous marker

                    currentLocationMarker = googleMap.addMarker(
                        MarkerOptions()
                            .position(currentLatLng)
                            .title("My Location").icon(markerIcon)
                    )
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                }
            }

            // 注册位置更新的监听器
            fusedLocationClient.requestLocationUpdates(
                LocationRequest.create(),
                object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        // update the location
                        val currentLocation = locationResult.lastLocation
                        val currentLatLng =
                            LatLng(currentLocation.latitude, currentLocation.longitude)
                        currentLocationMarker?.remove()
                        currentLocationMarker = googleMap.addMarker(
                            MarkerOptions()
                                .position(currentLatLng)
                                .title("My Location").icon(markerIcon)
                        )
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                    }
                },
                null
            )
        } else {
//             if do not have access,ask the user to give
            requestLocationPermission()
        }
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
            val markerId = clickedMarker.id.substring(1).toLong() // 解析标记的ID
            intent.putExtra("id", markerId)
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
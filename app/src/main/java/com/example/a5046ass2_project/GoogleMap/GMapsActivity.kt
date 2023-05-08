package com.example.a5046ass2_project.GoogleMap
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.room.Room
import com.example.a5046ass2_project.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.CameraPosition

class GMapsActivity: AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var attributeDao: AttributeDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gmaps)

        // 初始化 Room 数据库
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
        attributeDao = db.attributeDao()

        // 获取地图对象
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.uiSettings.setZoomControlsEnabled(true)

        // 请求位置权限
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap.isMyLocationEnabled = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }

        // 设置墨尔本为地图的中心点
        val melbourne = LatLng(-37.8136, 144.9631)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(melbourne, DEFAULT_ZOOM))
        val markerOptions = MarkerOptions().position(melbourne).title("Melbourne CBD")

        googleMap.addMarker(markerOptions)

        // 添加属性数据到地图
        attributeDao.getAllAttributes().observe(this, Observer { attributes ->
            attributes.forEach { attribute ->
                val position = LatLng(attribute.latitude, attribute.longitude)
                val markerOptions = MarkerOptions().position(position).title(attribute.title)
                googleMap.addMarker(markerOptions)
            }
        })
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 123
        private const val DEFAULT_ZOOM = 10f
    }
}

import com.mapbox.geojson.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.map_screen.R

import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
class MapBoxKT : AppCompatActivity() {
    var mapView: MapView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mapView = findViewById(R.id.mapView)
        val point= Point.fromLngLat(145.045837, -37.876823 )
        var cameraPosition:CameraOptions = CameraOptions.Builder()
            .zoom(13.0)
            .center(point)
            .build();
        mapView?.getMapboxMap()?.loadStyleUri(Style.MAPBOX_STREETS)
        mapView?.getMapboxMap()?.setCamera(cameraPosition);
    }
}
package com.boss.allaboutmaps;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.boss.allaboutmaps.databinding.ActivityMain2Binding;
import com.boss.allaboutmaps.databinding.ActivityMainBinding;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class MainActivity2 extends AppCompatActivity {
    ActivityMain2Binding binding;

    private MapView mapView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityMain2Binding.inflate(getLayoutInflater());


        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the MapView
        mapView = findViewById(R.id.map);

        // Set the tile source to use the default OpenStreetMap (MAPNIK)
        mapView.setTileSource(TileSourceFactory.MAPNIK);

        // Enable zoom controls
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        // Set the initial zoom level and center point
        IMapController mapController = mapView.getController();
        mapController.setZoom(10); // Set an initial zoom level
//        mapController.setCenter(new org.osmdroid.api.GeoPoint(51.505, -0.09)); // Set the initial center point (latitude, longitude)

        // Enable location overlay to show user's location
        GpsMyLocationProvider locationProvider = new GpsMyLocationProvider(this);
        MyLocationNewOverlay locationOverlay = new MyLocationNewOverlay(locationProvider, mapView);
        locationOverlay.enableMyLocation();
        locationOverlay.enableFollowLocation();

        // Set a custom location icon if desired
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background); // Replace with your own icon
        locationOverlay.setPersonIcon(icon);

        // Add the location overlay to the map
        mapView.getOverlays().add(locationOverlay);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume(); // Important to call onResume for OSMDroid
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause(); // Important to call onPause for OSMDroid
    }


    }


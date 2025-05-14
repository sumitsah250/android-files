package com.boss.allaboutmaps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.boss.allaboutmaps.databinding.ActivityMain2Binding;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class MainActivity2 extends AppCompatActivity {

    ActivityMain2Binding binding;
    private MapView mapView;
    private MyLocationNewOverlay locationOverlay;
    private Marker pointerMarker;

    // 👇 Info box views
    private TextView tvCurrentLocation, tvTappedLocation, tvDistance;
    private View infoBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ✅ Set user agent to avoid osmdroid warnings
        Configuration.getInstance().setUserAgentValue(getPackageName());

        // ✅ Edge-to-edge layout
        EdgeToEdge.enable(this);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // ✅ Handle system insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ✅ Setup MapView
        mapView = findViewById(R.id.map);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        IMapController mapController = mapView.getController();
        mapController.setZoom(18.0);

        // ✅ Location overlay setup
        locationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this), mapView);
        locationOverlay.enableMyLocation();
        locationOverlay.enableFollowLocation();
        mapView.getOverlays().add(locationOverlay);

        // ✅ Pointer marker setup
        pointerMarker = new Marker(mapView);
        pointerMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        pointerMarker.setVisible(false);
        mapView.getOverlays().add(pointerMarker);

        // ✅ Initialize info box views
        tvCurrentLocation = findViewById(R.id.tvCurrentLocation);
        tvTappedLocation = findViewById(R.id.tvTappedLocation);
        tvDistance = findViewById(R.id.tvDistance);
        infoBox = findViewById(R.id.infoBox);

        // ✅ Tap event to show marker and distance
        MapEventsReceiver receiver = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                pointerMarker.setPosition(p);
                pointerMarker.setVisible(true);
                mapView.invalidate();

                GeoPoint myLocation = locationOverlay.getMyLocation();
                if (myLocation != null) {
                    double distance = myLocation.distanceToAsDouble(p);

                    // ✅ Show info box and update values
                    infoBox.setVisibility(View.VISIBLE);
                    tvCurrentLocation.setText("Current: " +
                            String.format("%.5f, %.5f", myLocation.getLatitude(), myLocation.getLongitude()));
                    tvTappedLocation.setText("Marked: " +
                            String.format("%.5f, %.5f", p.getLatitude(), p.getLongitude()));
                    tvDistance.setText("Distance: " + String.format("%.2f", distance) + " meters");
                } else {
                    Toast.makeText(MainActivity2.this,
                            "User location not available yet", Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        };

        MapEventsOverlay overlayEvents = new MapEventsOverlay(receiver);
        mapView.getOverlays().add(overlayEvents);

        // ✅ Runtime location permission check
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        if (locationOverlay != null) locationOverlay.enableMyLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        if (locationOverlay != null) locationOverlay.disableMyLocation();
    }
}

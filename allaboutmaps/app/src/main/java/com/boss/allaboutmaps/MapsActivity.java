package com.boss.allaboutmaps;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.boss.allaboutmaps.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.PolygonOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng wrc = new LatLng(28.2539, 83.9764);
//        mMap.addMarker(new MarkerOptions().position(wrc).title("Marker in WRC"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(wrc));
        MarkerOptions markerOptions = new MarkerOptions().position(wrc).title("Marker in Wrc");
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(wrc));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(wrc,16f));

        //circle

        mMap.addCircle(new CircleOptions().center(wrc).radius(1000).fillColor(Color.RED).strokeColor(R.color.black));


        //polygon

//        mMap.addPolygon(new PolygonOptions().add(new LatLng(28.2539, 83.9764)
//                ,new LatLng(29.2539, 84.9764)
//                ,new LatLng(30.2539, 85.9764)
//                ,new LatLng(31.2539, 86.9764)
//                ,new LatLng(28.2539, 83.9764)).fillColor(Color.YELLOW).strokeColor(Color.GREEN));

//        mMap.addGroundOverlay(new GroundOverlayOptions().position(wrc,1000f,1000f)
//                .image(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher_background)).clickable(true));
    }
}
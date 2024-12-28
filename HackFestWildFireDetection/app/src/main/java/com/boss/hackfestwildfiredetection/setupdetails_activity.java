package com.boss.hackfestwildfiredetection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.boss.hackfestwildfiredetection.databinding.ActivitySetupdetailsBinding;

public class setupdetails_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActivitySetupdetailsBinding binding;
        binding= ActivitySetupdetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
binding.goToMapButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        double latitude = 37.7749;
        double longitude = -122.4194;
        double radius = 1000.0;

        // Create an Intent to pass data to the Kotlin Activity
        Intent intent = new Intent(setupdetails_activity.this, Maps_activity.class);

        // Pass the data through the Intent
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        intent.putExtra("radius", radius);

        // Start the Kotlin Activity
        startActivity(intent);
    }
});

    }
}
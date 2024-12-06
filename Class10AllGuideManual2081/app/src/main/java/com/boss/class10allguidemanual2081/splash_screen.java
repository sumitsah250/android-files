package com.boss.class10allguidemanual2081;

import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.window.SplashScreen;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class splash_screen extends AppCompatActivity {
    private TextView logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        logo=findViewById(R.id.centertext);
        // Hide the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(splash_screen.this,MainActivity.class));
//                finish();
//
//            }
//        },2000);
        animateLogo();
//
//



    }
    private void animateLogo() {
        // Create a rotation animation for the logo
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(logo, "rotation", 0f, 360f);
        rotationAnimator.setDuration(1000);  // 1 second duration
        rotationAnimator.setInterpolator(new DecelerateInterpolator());

        // Create a scaling animation for the logo
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(logo, "scaleX", 1f, 1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(logo, "scaleY", 1f, 1f);
        scaleXAnimator.setDuration(1000);
        scaleYAnimator.setDuration(1000);
        scaleXAnimator.setInterpolator(new DecelerateInterpolator());
        scaleYAnimator.setInterpolator(new DecelerateInterpolator());

        // Combine both animations (rotation and scaling)
//        rotationAnimator.start();
        scaleXAnimator.start();
        scaleYAnimator.start();

        // After animation is done, shrink logo and hide splash screen
        scaleXAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                // Scale down the logo after 1 second of scaling to full size
                ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(logo, "scaleX", 1f, 0f);
                ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(logo, "scaleY", 1f, 0f);
                scaleDownX.setDuration(500); // 0.5 seconds
                scaleDownY.setDuration(500);
                scaleDownX.setInterpolator(new DecelerateInterpolator());
                scaleDownY.setInterpolator(new DecelerateInterpolator());

                scaleDownX.start();
                scaleDownY.start();

                // Wait until the scale-down animation finishes and then launch the next activity
                scaleDownY.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(android.animation.Animator animation) {
                        // Start the main activity after the animation finishes
                        Intent intent = new Intent(getApplication(), MainActivity.class);
                        startActivity(intent);
                        finish();  // Close the splash screen activity
                    }
                });
            }
        });
    }
}
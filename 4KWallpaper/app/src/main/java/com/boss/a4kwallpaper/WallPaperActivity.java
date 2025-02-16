package com.boss.a4kwallpaper;

import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.boss.a4kwallpaper.databinding.ActivityWallPaperBinding;
import com.boss.models.Photo;
import com.squareup.picasso.Picasso;

public class WallPaperActivity extends AppCompatActivity {
    ActivityWallPaperBinding binding;
    Photo photo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityWallPaperBinding.inflate( getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.fabSetWallpaper.setVisibility(View.GONE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        photo= (Photo) getIntent().getSerializableExtra("photo");
        Picasso.get().load(photo.getSrc().original).placeholder(R.drawable.wallpaper_svgrepo_com).into(binding.imageViewWallpaper);

        binding.fabDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.fabSetWallpaper.setVisibility(View.VISIBLE);
                DownloadManager downloadManager = null;
                downloadManager= (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(photo.getSrc().original);

                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(false)
                        .setTitle("Wallpaper by"+photo.getPhotographer())
                        .setMimeType("image/jpeg")
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,"Wallpaper by"+photo.getPhotographer()+".jpg");
                downloadManager.enqueue(request);
                Toast.makeText(WallPaperActivity.this, "Downloading...", Toast.LENGTH_SHORT).show();

                Toast.makeText(WallPaperActivity.this, "Download Completed", Toast.LENGTH_SHORT).show();

            }
        });
        binding.fabSetWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(WallPaperActivity.this);
                Bitmap bitmap = ((BitmapDrawable) binding.imageViewWallpaper.getDrawable()).getBitmap();
                Toast.makeText(WallPaperActivity.this, "Wallpaper Applied", Toast.LENGTH_SHORT).show();
                    wallpaperManager.setBitmap(bitmap);
                }catch (Exception e){
                    Toast.makeText(WallPaperActivity.this, "Couldn't Add Wallpaper"+e, Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Handle the back button press
            onBackPressed(); // Go back to the previous activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
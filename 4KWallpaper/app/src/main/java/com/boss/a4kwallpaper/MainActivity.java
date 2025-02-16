package com.boss.a4kwallpaper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.SearchView;

import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.adapters.CuratedAdapter;
import com.boss.Listners.CuretedResponseListners;
import com.boss.Listners.OnRecyclerClickListtner;
import com.boss.Listners.SearchResponseListners;
import com.boss.a4kwallpaper.databinding.ActivityMainBinding;
import com.boss.models.CuretedApiResponse;
import com.boss.models.Photo;
import com.boss.models.SearchApiResponse;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnRecyclerClickListtner {
    ActivityMainBinding binding;
    ProgressDialog dialog;
    RequestManager manager;
    CuratedAdapter curatedAdapter;
    int page=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dialog= new ProgressDialog(this);
        dialog.setTitle("Loading...");
        manager= new RequestManager(this);
        manager.getCuratedWallpapers(listners,"1");

        binding.fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String next_page=String.valueOf(page+1);
                manager.getCuratedWallpapers(listners,next_page);
                dialog.show();
            }
        });
        binding.fabPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(page>1){
                    String prev_page=String.valueOf(page-1);
                    manager.getCuratedWallpapers(listners,prev_page);
                    dialog.show();
                }
            }
        });

    }
    private final CuretedResponseListners listners= new CuretedResponseListners() {
        @Override
        public void onFetch(CuretedApiResponse response, String message) {
            if(response.getPhotos().isEmpty()){
                Toast.makeText(MainActivity.this, "No Image Found", Toast.LENGTH_SHORT).show();
                return;
            }
            dialog.dismiss();
            page=response.getPage();
            showData(response.getPhotos());
        }
        @Override
        public void onError(String message) {
            dialog.dismiss();
            Log.d("MainActivity",message);
            Toast.makeText(MainActivity.this, ""+message, Toast.LENGTH_SHORT).show();
        }
    };
    private void showData(ArrayList<Photo> photos) {
        binding.recyclerHome.setHasFixedSize(true);
        binding.recyclerHome.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
        curatedAdapter= new CuratedAdapter(MainActivity.this,photos,this);
        binding.recyclerHome.setAdapter(curatedAdapter);
    }

    @Override
    public void onClick(Photo photo) {

        startActivity(new Intent(MainActivity.this,WallPaperActivity.class).putExtra("photo",photo));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView =(SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search ...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                manager.SearchCuratedWallpapers(searchResponseListners,"1",query);
                dialog.show();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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
    private  final SearchResponseListners searchResponseListners = new SearchResponseListners() {
        @Override
        public void onFetch(SearchApiResponse responseListners, String message) {
            dialog.dismiss();
            if(responseListners.getPhotos().isEmpty()){
                Toast.makeText(MainActivity.this, "No Image Found", Toast.LENGTH_SHORT).show();
                return;
            }
            showData(responseListners.getPhotos());
        }

        @Override
        public void onError(String message) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    };

}
package com.boss.class10allguidemanual2081;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.boss.Subjects.GeneralEnglish.EnglishMediumMath;
import com.boss.Subjects.GeneralEnglish.EnglishMediumOptMath;
import com.boss.Subjects.GeneralEnglish.EnglishMediumScience;
import com.boss.Subjects.GeneralEnglish.EnglishMediumSocial;
import com.boss.Subjects.GeneralEnglish.GeneralAccount;
import com.boss.Subjects.GeneralEnglish.GeneralComputer;
import com.boss.Subjects.GeneralEnglish.GeneralEnglish;
import com.boss.Subjects.GeneralEnglish.GeneralNepali;
import com.boss.Subjects.GeneralEnglish.NepaliMediumMath;
import com.boss.Subjects.GeneralEnglish.NepaliMediumOptMath;
import com.boss.Subjects.GeneralEnglish.NepaliMediumScience;
import com.boss.Subjects.GeneralEnglish.NeplaiMediumSocial;
import com.boss.Subjects.GeneralEnglish.SeeModelQuestion;
import com.boss.Subjects.GeneralEnglish.SeeModelSolution;
import com.boss.class10allguidemanual2081.databinding.ActivityMainBinding;
import com.boss.class10allguidemanual2081.databinding.MainactivityContentBinding;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ActivityMainBinding binding1;

    private AppUpdateManager appUpdateManager;
    private static final int REQUEST_CODE_UPDATE = 100;
//    FirebaseFirestore firestore;
//    ArrayList<Model> ChapterList;
//    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding1 = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding1.getRoot());

         //navigation bar
        MainactivityContentBinding binding = MainactivityContentBinding.bind(binding1.includedLayout.getRoot());

        binding.toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding1.main.open();
            }
        });
        binding1.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId=item.getItemId();
                if(itemId==R.id.nav_home){
                    binding1.main.closeDrawer(GravityCompat.START);
                }
                if(itemId==R.id.privacy){
                    openPrivacyPolicy();
                }
                if(itemId==R.id.rate){
                    showRateApp();
                }
                if(itemId==R.id.seeallapps){
                    openPlayStore();
                }
                if(itemId==R.id.share){
                    shareApp();
                }
                return false;
            }
        });

        //navigation

        //for action bar

//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setTitle("All In One Guide 2081");
//        }
        //toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle("All IN ONE GUIDE");
            getSupportActionBar().setSubtitle("new syllabus 2081");
        }
        //toolbar
        //for Action Bar



        //app update
        // Initialize AppUpdateManager
        appUpdateManager = AppUpdateManagerFactory.create(this);

        // Check for the update
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                    appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {

                // Request the update
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo,
                            AppUpdateType.IMMEDIATE,
                            MainActivity.this,
                            REQUEST_CODE_UPDATE
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //app update


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        binding.generalEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GeneralEnglish.class);
                intent.putExtra("Book"," English");
                startActivity(intent);
            }
        });
        binding.GeneralNepali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(new Intent(MainActivity.this, GeneralNepali.class));
                intent.putExtra("Book"," Nepali");
                startActivity(intent);

            }
        });
        binding.GeneralComputer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GeneralComputer.class);
                intent.putExtra("Book"," Computer");
                startActivity(intent);

            }
        });
//        binding.GeneralAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, GeneralAccount.class);
//                intent.putExtra("Book"," Account");
//                startActivity(intent);
//
//            }
//        });
        binding.EnglishMediumMath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this, EnglishMediumMath.class);
                intent.putExtra("Book"," Math");
                startActivity(intent);

            }
        });
        binding.EnglishMediumScience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EnglishMediumScience.class);
                intent.putExtra("Book"," Science");
                startActivity(intent);

            }
        });
        binding.EnglishMediumSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EnglishMediumSocial.class);
                intent.putExtra("Book"," Social");
                startActivity(intent);

            }
        });
//        binding.EnglishMediumOptMath.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, EnglishMediumOptMath.class);
//                intent.putExtra("Book"," OptMath");
//                startActivity(intent);
//
//            }
//        });
        binding.NepaliMediumMath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NepaliMediumMath.class);
                intent.putExtra("Book"," Math");
                startActivity(intent);

            }
        });
        binding.NepaliMediumScience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NepaliMediumScience.class);
                intent.putExtra("Book"," Science");
                startActivity(intent);

            }
        });
        binding.NepaliMediumSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NeplaiMediumSocial.class);
                intent.putExtra("Book"," Social");
                startActivity(intent);

            }
        });
//        binding.NepaliMediumOptMath.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, NepaliMediumOptMath.class);
//                intent.putExtra("Book"," Math");
//                startActivity(intent);
//
//            }
//        });
        binding.SeeModelQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SeeModelQuestion.class);
                intent.putExtra("Book"," See Model Question");
                startActivity(intent);

            }
        });
        binding.SeeModelSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, SeeModelSolution.class);
                intent.putExtra("Book"," See Model Solution");
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_UPDATE) {
            if (data != null) {
                if (resultCode != RESULT_OK) {
                    Toast.makeText(this, "Update failed!", Toast.LENGTH_SHORT).show();
                }
                // Handle your logic with data
            } else {
                Log.d("crash due to app check","null pointer");
                // Handle case when data is null
            }

        }
    }
    //app update


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        new MenuInflater(this).inflate(R.menu.top_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.rate){
            showRateApp();

        }
        return super.onOptionsItemSelected(item);
    }
    public void showRateApp() {
        // Create a ReviewManager instance
        ReviewManager reviewManager = ReviewManagerFactory.create(this);

        // Request a ReviewInfo object
        Task<ReviewInfo> request = reviewManager.requestReviewFlow();

        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Retrieve the ReviewInfo object
                ReviewInfo reviewInfo = task.getResult();

                // Launch the review flow
                Task<Void> flow = reviewManager.launchReviewFlow(this, reviewInfo);
                flow.addOnCompleteListener(flowTask -> {
                    // Optionally handle the completion of the review flow
                    Toast.makeText(this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
                });
            } else {
                // Handle the error (e.g., log it, display a message to the user)
                Toast.makeText(this, "Unable to show review dialog at this time.", Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void openPlayStore() {
        String developerPageUrl = "https://play.google.com/store/apps/developer?id=S developers";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(developerPageUrl));
        startActivity(intent);
    }
    private void shareApp() {
        String appPackageName = "com.boss.class10allguidemanual2081"; // Replace with your app's package name
        String appUrl = "https://play.google.com/store/apps/details?id=" + appPackageName;

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this app!"); // Title of the message
        shareIntent.putExtra(Intent.EXTRA_TEXT, "I recommend this app: " + appUrl); // Body of the message

        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }
    private void openPrivacyPolicy() {
        String privacyPolicyUrl = "https://www.termsfeed.com/live/812849f8-27eb-482e-9a40-3b21895767f1"; // Replace with your actual URL
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(privacyPolicyUrl));
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        // Check if the navigation drawer is open
        if (binding1.main.isDrawerOpen(GravityCompat.START)) {
            // Close the drawer if it is open
            binding1.main.closeDrawer(GravityCompat.START);
        } else {
            // Otherwise, follow the default back button behavior
            super.onBackPressed();
        }
    }
}
package com.boss.class10allguidemanual2081.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowMetrics;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.boss.class10allguidemanual2081.MainActivity;
import com.boss.class10allguidemanual2081.R;
import com.boss.class10allguidemanual2081.databinding.ActivityDetailsBinding;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Details extends AppCompatActivity {
    ActivityDetailsBinding binding;
//    ProgressDialog dialog;
   Dialog dialog;
    private static String TEST_AD_UNIT_ID="ca-app-pub-3940256099942544/9214589741";
    private static String Banner_AD_UNIT_ID="ca-app-pub-8523770818071031/7603943298";

    private static final int PAGE_LOAD_LIMIT = 5; // Limit to load 10 pages at a time
    private int currentPage = 0; // Track the current page being viewed
    private int totalPages = 0; // Total number of pages in the document

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // for interstial ads
        loadInterstialAd();
        // for interstial ads

                //for ads
        AdView adView = new AdView(this);
        adView.setAdUnitId(Banner_AD_UNIT_ID);
        adView.setAdSize(getAdSize());
        AdView adContainerView = findViewById(R.id.bannerAds);
        adContainerView.removeAllViews();
        adContainerView.addView(adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                adContainerView.setVisibility(View.VISIBLE);  // Show ad when loaded
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                adContainerView.setVisibility(View.GONE);  // Hide ad when failed
            }
        });
        //for ads


        //for ads on dialog box
       dialog = new Dialog(Details.this,R.style.CustomDialogTheme);
        dialog.setContentView(R.layout.custom_dialog_loader);

        AdView adViewdialog = new AdView(this);
        adViewdialog.setAdUnitId(TEST_AD_UNIT_ID);
        adViewdialog.setAdSize(getAdSize());
        AdView adContainerViewdialog = dialog.findViewById(R.id.bannerAds);
        adContainerViewdialog.removeAllViews();
        adContainerViewdialog.addView(adViewdialog);
        AdRequest adRequestdialog = new AdRequest.Builder().build();
        adView.loadAd(adRequestdialog);
//        adView.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                adContainerViewdialog.setVisibility(View.VISIBLE);  // Show ad when loaded
//            }
//
//            @Override
//            public void onAdFailedToLoad(LoadAdError adError) {
//                adContainerViewdialog.setVisibility(View.GONE);  // Hide ad when failed
//            }
//        });

        // dialog box ad's











//        //for Progress Dialog
//        dialog= new ProgressDialog(this);
//        dialog.setMessage("Loading for the first time...\nPreparing your file \nPlease wait...");
//        dialog.setCancelable(false);
//        // Allow back button to cancel the dialog
//        dialog.setOnKeyListener((dialog, keyCode, event) -> {
//            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
//                dialog.dismiss(); // Dismiss the dialog
//                return true; // Consume the event
//            }
//            return false; // Pass the event to other listeners
//        });




        String chapter = getIntent().getStringExtra("chapter");
        String pdflink = getIntent().getStringExtra("Book_Pdf_link");
        try {
            if(pdflink!=null || !pdflink.equals("")) {
                showPdf(pdflink);
            }else{
                Toast.makeText(this, "Sorry this is not available at the moment", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            Log.d("DetailsActivity",""+e);
           binding.sorrymsg.setVisibility(View.VISIBLE);
        }


        getSupportActionBar().setTitle(chapter);
    }
    private void showPdf(String pdflink) {
        dialog.show();
        if (pdflink != null && !pdflink.isEmpty()) {
            String fileName = "pdf_" + pdflink.hashCode() + ".pdf";
            File cacheDir = getCacheDir();
            File pdfFile = new File(cacheDir, fileName);

            if (pdfFile.exists()) {
                // Load the cached PDF and show the first 10 pages
                loadPdf(pdfFile);
                //for interstial ads
                if(mInterstitialAd!=null){
                    mInterstitialAd.show(Details.this);
                }else{
                    loadInterstialAd();
                }
                // for interstial ads
            } else {
                // Download, cache, and load the PDF
                downloadAndLoadPdf(pdflink, pdfFile);
            }
        } else {
            binding.sorrymsg.setVisibility(View.VISIBLE);
        }
    }

    private void loadPdf(File pdfFile) {
        // Load the first 10 pages from the file
        runOnUiThread(() -> {
            try {
                if (!isFinishing() && binding != null && binding.pdfView != null) {
                    binding.pdfView.recycle(); // Clear any previous PDF
                    binding.pdfView.fromFile(pdfFile)
                            .onPageChange((page, pageCount) -> {
                                totalPages = pageCount; // Track total pages
                                // Trigger loading next pages when the user reaches near the last page
                                if (page >= currentPage + PAGE_LOAD_LIMIT - 1) {
                                    loadNextPages(pdfFile);
                                }
                            })
                            .onError(t -> Log.e("PDFViewError", "Error rendering PDF", t))
                            .enableDoubletap(false) // Disable double-tap for fast scrolling
                            .defaultPage(currentPage) // Start from the current page
                            .load();
                    dialog.dismiss();
                     //for interstial ads
                                    if(mInterstitialAd!=null){
                                        mInterstitialAd.show(Details.this);
                                    }else{
                                        loadInterstialAd();
                                    }
                                    // for interstial ads
                }
            } catch (Exception e) {
                Log.e("Details_activity", "Error loading cached PDF", e);
            }
        });
    }

    private void downloadAndLoadPdf(String pdflink, File pdfFile) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(pdflink).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("Details_activity", "PDF download failed", e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (response.body() != null) {
                    try (InputStream inputStream = response.body().byteStream();
                         FileOutputStream outputStream = new FileOutputStream(pdfFile)) {

                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, length);
                        }

                        runOnUiThread(() -> {
                            try {
                                if (!isFinishing() && binding != null && binding.pdfView != null) {
                                    loadPdf(pdfFile); // After download, load the PDF
                                    dialog.dismiss();
                                    //for interstial ads
                                    if(mInterstitialAd!=null){
                                        mInterstitialAd.show(Details.this);
                                    }else{
                                        loadInterstialAd();
                                    }
                                    // for interstial ads
                                }
                            } catch (Exception e) {
                                Log.e("Details_activity", "Error displaying downloaded PDF", e);
                            }
                        });
                    } catch (IOException e) {
                        Log.e("Details_activity", "Error saving downloaded PDF", e);
                    }
                } else {
                    Log.e("Details_activity", "Response body is null");
                }
            }
        });
    }

    private void loadNextPages(File pdfFile) {
        // Load the next set of pages (next 10 pages) in the background
        new Thread(() -> {
            try {
                // Wait for a short duration before starting to load next pages
                Thread.sleep(500); // Simulate loading delay for the next pages
                currentPage += PAGE_LOAD_LIMIT; // Increment the page counter

                // If the next set of pages is still within the bounds of the document, load them
                if (currentPage < totalPages) {
                    runOnUiThread(() -> {
                        try {
                            if (!isFinishing() && binding != null && binding.pdfView != null) {
                                binding.pdfView.fromFile(pdfFile)
                                        .onPageChange((page, pageCount) -> {
                                            totalPages = pageCount;
                                            if (page >= currentPage + PAGE_LOAD_LIMIT - 1) {
                                                loadNextPages(pdfFile); // Continue loading next pages
                                            }
                                        })
                                        .onError(t -> Log.e("PDFViewError", "Error rendering PDF", t))
                                        .defaultPage(currentPage)
                                        .load();
                            }
                        } catch (Exception e) {
                            Log.e("Details_activity", "Error loading next pages", e);
                        }
                    });
                }
            } catch (InterruptedException e) {
                Log.e("Details_activity", "Error loading next pages", e);
            }
        }).start();
    }
    private AdSize getAdSize(){
        //calculate with pixels
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int addWidthPixels = displayMetrics.widthPixels;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
            WindowMetrics windowMetrics = getWindowManager().getCurrentWindowMetrics();
            addWidthPixels=windowMetrics.getBounds().width();
        }

        //calculate density
        float density = displayMetrics.density;
        //calculate adwidht
        int adWidth = (int) (addWidthPixels/density);
        return AdSize.getCurrentOrientationInlineAdaptiveBannerAdSize(this,adWidth);

    }
    private InterstitialAd mInterstitialAd;
    private static final String TAG = "Details";
    void loadInterstialAd()
    {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-8523770818071031/9140386247", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");

                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                            @Override
                            public void onAdClicked() {
                                // Called when a click is recorded for an ad.
                                Log.d(TAG, "Ad was clicked.");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                Log.d(TAG, "Ad dismissed fullscreen content.");
                                mInterstitialAd = null;
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                Log.e(TAG, "Ad failed to show fullscreen content.");
                                mInterstitialAd = null;
                            }

                            @Override
                            public void onAdImpression() {
                                // Called when an impression is recorded for an ad.
                                Log.d(TAG, "Ad recorded an impression.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                Log.d(TAG, "Ad showed fullscreen content.");
                            }
                        });



                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d(TAG, loadAdError.toString());
                        mInterstitialAd = null;
                    }
                });

    }

}
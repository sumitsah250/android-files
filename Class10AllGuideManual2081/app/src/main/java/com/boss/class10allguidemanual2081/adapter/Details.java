package com.boss.class10allguidemanual2081.adapter;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.boss.class10allguidemanual2081.R;
import com.boss.class10allguidemanual2081.databinding.ActivityDetailsBinding;

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
    ProgressDialog dialog;

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
        //for Progress Dialog
        dialog= new ProgressDialog(this);
        dialog.setMessage("Loading for the first time...\nPreparing your file \nPlease wait...");
        dialog.setCancelable(false);
        // Allow back button to cancel the dialog
        dialog.setOnKeyListener((dialog, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                dialog.dismiss(); // Dismiss the dialog
                return true; // Consume the event
            }
            return false; // Pass the event to other listeners
        });




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
                // Safely load the cached PDF
                runOnUiThread(() -> {
                    try {
                        if (!isFinishing() && binding != null && binding.pdfView != null) {
                            binding.pdfView.recycle(); // Clear any previous PDF
                            binding.pdfView.fromFile(pdfFile)
                                    .onError(t -> Log.e("PDFViewError", "Error rendering PDF", t))
                                    .load();
                            dialog.dismiss();
                        }
                    } catch (Exception e) {
                        Log.e("Details_activity", "Error loading cached PDF", e);
                    }
                });
            } else {
                // Download, cache, and load the PDF
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
                                            binding.pdfView.recycle(); // Clear any previous PDF
                                            binding.pdfView.fromFile(pdfFile)
                                                    .onError(t -> Log.e("PDFViewError", "Error rendering PDF", t))
                                                    .load();
                                            dialog.dismiss();

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
        } else {
            binding.sorrymsg.setVisibility(View.VISIBLE);
        }
    }


}
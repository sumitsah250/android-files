package com.boss.databinding__live_data;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.boss.databinding__live_data.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    Button btnincr,btndcr;
    TextView txtcount;

    ActivityMainBinding binding;
    private  MainViewModel mainViewModel;



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
        //
//        btnincr=findViewById(R.id.btnincr);
//        btndcr=findViewById(R.id.btndcr);
//        txtcount=findViewById(R.id.txtcount);
        //
          mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
          Log.i("MainActivity","view model is initialized");
          binding.txtcount.setText(mainViewModel.count+"");
        binding.btnincr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.count=mainViewModel.count+1;
                binding.txtcount.setText(mainViewModel.count+"");

            }
        });
        binding.btndcr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.count=mainViewModel.count-1;
                binding.txtcount.setText(mainViewModel.count+"");

            }
        });

    }
}
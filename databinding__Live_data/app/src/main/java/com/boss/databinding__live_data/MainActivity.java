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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.boss.databinding__live_data.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    Button btnincr,btndcr;
    TextView txtcount;

    ActivityMainBinding binding;
    private  MainViewModel mainViewModel;
    private  MainViewModel1 mainViewModel1;



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

     /// while using view model
//          mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
////          mainViewModel = new MainViewModel();  // this create new view model each time so no use to us
//          Log.i("activityinfo","MainActivity:view model is initialized");
//          binding.txtcount.setText(mainViewModel.count+"");
//        binding.btnincr.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mainViewModel.count=mainViewModel.count+1;
//                binding.txtcount.setText(mainViewModel.count+"");
//
//            }
//        });
//        binding.btndcr.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mainViewModel.count=mainViewModel.count-1;
//                binding.txtcount.setText(mainViewModel.count+"");
//
//            }
//        });



        //// while using view model



        //Live data
        mainViewModel1 = new ViewModelProvider(this).get(MainViewModel1.class);
//          mainViewModel = new MainViewModel();  // this create new view model each time so no use to us
        mainViewModel1.score.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.txtcount.setText(mainViewModel1.score.getValue().toString());
            }
        });

        binding.btnincr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel1.increment();

            }
        });
        binding.btndcr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel1.decrement();
            }
        });

        //Live data




    }

}
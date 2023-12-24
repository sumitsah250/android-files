package com.example.customtoast;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast toast =new Toast(getApplicationContext());
                View view =getLayoutInflater().inflate(R.layout.custom_toast_layout,
                        (ViewGroup) findViewById(R.id.ll));
                toast.setView(view);
                TextView txtview=view.findViewById(R.id.txtmassage);
                txtview.setText("message passed sucessfully");
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP|Gravity.RIGHT,0,0);
                toast.show();
            }
        });

    }
}
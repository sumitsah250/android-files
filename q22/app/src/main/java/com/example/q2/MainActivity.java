package com.example.q2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btn1,btn2;
    String s1;
    TextView t1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1=findViewById(R.id.t1);
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);

//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                t1.setText("sumit");
//                s1=t1.getText().toString();
//            }
//        });
//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                t1.setText(s1);
//            }
//        });


    }
    public void check(View v){
        Button current = (Button) v;
        current.setText("aaditya");
        Toast.makeText(this, t1.getText(), Toast.LENGTH_SHORT).show();

    }
}
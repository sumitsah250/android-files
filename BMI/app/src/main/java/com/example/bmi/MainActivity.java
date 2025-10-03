package com.example.bmi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout backbroundmain;
        EditText edtweight,edtfheight,edtiheight = null;
        TextView textResult;
        TextView textbmi;
        Button btncalculate;
        edtweight = findViewById(R.id.edtweight);
        edtfheight = findViewById(R.id.edtfheight);
        edtiheight = findViewById(R.id.edtiheight);
        btncalculate =findViewById(R.id.btncalculator);
        backbroundmain=findViewById(R.id.backgroundmain);
        textbmi=findViewById(R.id.textbmi);
        textResult = findViewById(R.id.textResult);
        EditText finalEdtweight = edtweight;
        EditText finalEdtfheight = edtfheight;
        EditText finalEdtiheight = edtiheight;
        btncalculate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if (!finalEdtfheight.getText().toString().equals("") && !finalEdtweight.getText().toString().equals("") && !finalEdtiheight.getText().toString().equals("")) {
                    int wt = Integer.parseInt(finalEdtweight.getText().toString());
                    int ft = Integer.parseInt(finalEdtfheight.getText().toString());
                    int in = Integer.parseInt(finalEdtiheight.getText().toString());
                    int totalIn = ft * 12 + in;
                    double totalCm = totalIn * 2.53;
                    double totalM = totalCm / 100;
                    double bmi = wt / (totalM * totalM);
                    if (bmi > 25) {
                        textResult.setText("you are Over weight 🦍️");
                        backbroundmain.setBackgroundColor(getResources().getColor(R.color.colorOW));
                    } else if (bmi < 18) {
                        textResult.setText("you are Under weight 🤦‍♂️");
                        backbroundmain.setBackgroundColor(getResources().getColor(R.color.colorUN));


                    } else {
                        textResult.setText("you are Heaalty  💪");
                        backbroundmain.setBackgroundColor(getResources().getColor(R.color.colorH));
                    }
                }
            }
        }
        );








    }
}
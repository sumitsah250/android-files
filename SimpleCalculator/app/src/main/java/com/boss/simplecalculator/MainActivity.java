package com.boss.simplecalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etNumber;
    private Button btnAdd;
    private TextView tvSum, tvAverage, tvGrade;
    private RecyclerView rvMarks;

    private ArrayList<Integer> marks = new ArrayList<>();
    private MarksAdapter marksAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        // Initialize Views

        // Initialize Views
        etNumber = findViewById(R.id.etNumber);
        btnAdd = findViewById(R.id.btnAdd);
        tvSum = findViewById(R.id.tvSum);
        tvAverage = findViewById(R.id.tvAverage);
        tvGrade = findViewById(R.id.tvGrade);
        rvMarks = findViewById(R.id.rvMarks);

        // Set up RecyclerView
        marksAdapter = new MarksAdapter(marks);
        rvMarks.setLayoutManager(new LinearLayoutManager(this));
        rvMarks.setAdapter(marksAdapter);

        // Add Button Listener
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMark();
            }
        });
    }

    private void addMark() {
        String input = etNumber.getText().toString().trim();

        if (input.isEmpty()) {
            Toast.makeText(this, "Please enter a mark!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add the entered mark to the list
        int mark = Integer.parseInt(input);
        marks.add(mark);

        // Notify the adapter of data change
        marksAdapter.notifyDataSetChanged();

        // Calculate and display the sum, average, and grade
        int sum = 0;
        for (int m : marks) {
            sum += m;
        }
        double average = (double) sum / marks.size();
        String grade = getGrade(average);

        tvSum.setText("Sum: " + sum);
        tvAverage.setText("Average: " + average);
        tvGrade.setText("Grade: " + grade);

        etNumber.setText(""); // Clear the input field
    }

    private String getGrade(double average) {
        if (average >= 90) {
            return "A";
        } else if (average >= 80) {
            return "B";
        } else if (average >= 70) {
            return "C";
        } else if (average >= 50) {
            return "D";
        } else {
            return "F";
        }
    }

}
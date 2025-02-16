package com.boss.cgpacalculator;

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
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText cgpaInput, creditInput;
    private Button addButton;
    private TextView cgpaResult;
    private RecyclerView recyclerView;
    private CGPAAdapter adapter;
    private List<CGPAEntry> cgpaList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        // Initialize UI Components
        cgpaInput = findViewById(R.id.cgpaInput);
        creditInput = findViewById(R.id.creditInput);
        addButton = findViewById(R.id.addButton);
        cgpaResult = findViewById(R.id.cgpaResult);
        recyclerView = findViewById(R.id.recyclerView);

        // RecyclerView Setup
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CGPAAdapter(cgpaList);
        recyclerView.setAdapter(adapter);

        // Add Button Click
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCGPAEntry();
            }
        });
    }

    private void addCGPAEntry() {
        String cgpaStr = cgpaInput.getText().toString().trim();
        String creditStr = creditInput.getText().toString().trim();

        if (cgpaStr.isEmpty() || creditStr.isEmpty()) {
            Toast.makeText(this, "Please enter both CGPA and Credit!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double cgpa = Double.parseDouble(cgpaStr);
            int credit = Integer.parseInt(creditStr);

            cgpaList.add(new CGPAEntry(cgpa, credit));
            adapter.notifyDataSetChanged();

            calculateFinalCGPA();
            cgpaInput.setText("");
            creditInput.setText("");

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input!", Toast.LENGTH_SHORT).show();
        }
    }

    private void calculateFinalCGPA() {
        double totalPoints = 0;
        int totalCredits = 0;

        for (CGPAEntry entry : cgpaList) {
            totalPoints += entry.getCgpa() * entry.getCredit();
            totalCredits += entry.getCredit();
        }

        if (totalCredits > 0) {
            double finalCGPA = totalPoints / totalCredits;
            cgpaResult.setText("CGPA: " + String.format("%.2f", finalCGPA));
        }
    }
}

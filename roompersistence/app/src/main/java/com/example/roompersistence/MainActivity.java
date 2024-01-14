package com.example.roompersistence;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edtTitle,edtamount;
    Button btnadd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtTitle = findViewById(R.id.edttitle);
        edtamount = findViewById(R.id.edtamount);
        btnadd = findViewById(R.id.btnadd);
        databaseHelper dbhelp = databaseHelper.getDb(this);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title =edtTitle.getText().toString();
                String amount = edtamount.getText().toString();

                dbhelp.expenseDao().addTx(
                        new Expense(title,amount)
                );
                ArrayList<Expense> arrExpenses = (ArrayList<Expense>) dbhelp.expenseDao().getAllExpense();
                for(int i=0;i<arrExpenses.size();i++){
                    Log.d("Data","Title "+ arrExpenses.get(i).getTitle()+ "amount"+arrExpenses.get(i).getAmount());
                }
            }
        });


    }
}
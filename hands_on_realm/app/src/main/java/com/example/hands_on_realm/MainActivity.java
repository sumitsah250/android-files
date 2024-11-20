package com.example.hands_on_realm;

import static java.security.AccessController.getContext;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hands_on_realm.databinding.ActivityMainBinding;
import com.example.hands_on_realm.model.Constants;
import com.example.hands_on_realm.model.MainViewModel;
import com.example.hands_on_realm.model.Student;
import com.example.hands_on_realm.model.StudentAdapter;

import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    private static final  String TAG=".MainActivity";
    EditText edtname,edtdetails;
    TextView display;
    Button btnsave;
//    Realm realm;
//    ListView listView

    ArrayAdapter<String> arr;
    ActivityMainBinding binding;
    public MainViewModel mainViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Constants.number=999;
       //main
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        //main

//      listView=findViewById(R.id.listview);
        Constants.number=999;
        edtname=findViewById(R.id.edtname1);
        edtdetails=findViewById(R.id.edtdetails1);
        btnsave=findViewById(R.id.save);
         edtname.setText(Constants.Name);
         edtdetails.setText(String.valueOf(Constants.number));

//        realm = Realm.getDefaultInstance();
//        readData();
//        realm.beginTransaction();
//        realm.deleteAll();
//        realm.commitTransaction();
        mainViewModel.getstudent();
        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData(edtname.getText().toString(),edtdetails.getText().toString());
                mainViewModel.getstudent();
            }
        });

        // mutable code

        mainViewModel.studentdata.observe(this, new Observer<RealmResults<Student>>() {
            @Override
            public void onChanged(RealmResults<Student> students) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                binding.recyclerview.setLayoutManager(layoutManager);
                StudentAdapter studentAdapter = new StudentAdapter(MainActivity.this,students);
                binding.recyclerview.setAdapter(studentAdapter);
            }
        });

        //mutable code




    }
    private void saveData(String name,String age){
        Student s = new Student();
        s.setName(name);
        s.setAge(Integer.parseInt(age));
        s.setId(new Date().getTime());
        mainViewModel.addstudent(s);
    }

//    private void readData(){
//        RealmResults<Student> student1 = null;
//               student1= realm.where(Student.class).findAll();
//        String data ="";
//        ArrayList<String> arrayList = new ArrayList<>();
//
//        for (Student student : student1) {
//            data = student.getName() + " - " + student.getAge();
//            arrayList.add(data);
//        }
//        arr = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
//
//
//    }
}
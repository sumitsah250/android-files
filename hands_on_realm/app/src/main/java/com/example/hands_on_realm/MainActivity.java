package com.example.hands_on_realm;

import android.os.Bundle;
import android.util.Log;
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

import com.example.hands_on_realm.model.Student;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    private static final  String TAG=".MainActivity";
    EditText edtname,edtdetails;
    TextView display;
    Button btnsave;
    Realm realm;
    ListView listView;

    ArrayAdapter<String> arr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


          listView=findViewById(R.id.listview);
        edtname=findViewById(R.id.edtname1);
        edtdetails=findViewById(R.id.edtdetails1);
        btnsave=findViewById(R.id.save);




        realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
//        realm.deleteAll();
//        realm.commitTransaction();

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData(edtname.getText().toString(),edtdetails.getText().toString());
                readData();

            }
        });



    }
    private void saveData(String name,String age){
        Student s = new Student();
        s.setName(name);
        s.setAge(Integer.parseInt(age));
        realm.beginTransaction();
        realm.insert(s);
        realm.commitTransaction();
    }
    private void readData(){
        RealmResults<Student> student1 = realm.where(Student.class).findAll();
        String data ="";
        ArrayList<String> arrayList = new ArrayList<>();
        for(Student student: student1){
            try {
                Log.d(TAG, "readData:");
                data = data + "\n" + student.toString();
                Toast.makeText(this, ""+data, Toast.LENGTH_SHORT).show();
                arrayList.add(data);
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }
        arr = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arr);
    }
}
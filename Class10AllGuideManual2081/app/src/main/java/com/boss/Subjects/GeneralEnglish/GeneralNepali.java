package com.boss.Subjects.GeneralEnglish;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.boss.class10allguidemanual2081.R;
import com.boss.class10allguidemanual2081.adapter.Adapter;
import com.boss.class10allguidemanual2081.databinding.ActivityGeneralNepaliBinding;
import com.boss.class10allguidemanual2081.modelclass.Model;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Objects;

public class GeneralNepali extends AppCompatActivity {
    ActivityGeneralNepaliBinding binding;
    FirebaseFirestore firestore;
    ArrayList<Model> ChapterList;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityGeneralNepaliBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //for action bar
        String Book= getIntent().getStringExtra("Book");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(Book);
        }
        //for Action Bar
        firestore=FirebaseFirestore.getInstance();
        ChapterList=new ArrayList<>();
        adapter =new Adapter(this,ChapterList);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerview.setAdapter(adapter);
        LoadData();
    }
    private void LoadData() {
        ChapterList.clear();
        firestore.collection("GeneralNepali").orderBy("id", Query.Direction.ASCENDING)
                .addSnapshotListener((value, error) -> {
                    if(error!=null){
                        Toast.makeText(this, "Chapter Loaded", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for(DocumentChange dc : Objects.requireNonNull(value).getDocumentChanges()){
                        if(dc.getType()==DocumentChange.Type.ADDED){
                            ChapterList.add(dc.getDocument().toObject(Model.class));
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

    }
}
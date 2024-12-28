package com.boss.Subjects.GeneralEnglish;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.boss.class10allguidemanual2081.R;
import com.boss.class10allguidemanual2081.adapter.Adapter;
import com.boss.class10allguidemanual2081.modelclass.Model;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Objects;

public class EnglishMediumMath extends AppCompatActivity {
    FirebaseFirestore firestore;
    ArrayList<Model> ChapterList;
    Adapter adapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_english_medium_math);
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

        // for review


        // for review

        recyclerView=findViewById(R.id.recyclerview);
        firestore=FirebaseFirestore.getInstance();
        ChapterList=new ArrayList<>();
        adapter =new Adapter(this,ChapterList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        LoadData();
    }
    private void LoadData() {
        ChapterList.clear();
        firestore.collection("EnglishMediumMath").orderBy("id", Query.Direction.ASCENDING)
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
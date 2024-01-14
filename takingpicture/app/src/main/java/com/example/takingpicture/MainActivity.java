package com.example.takingpicture;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private final int CAMERA_REQ_CODE=1;
    private final int GALLARY_REQ_CODE=1;
    ImageView imgcamera;
    Button btncamera;

    FirebaseDatabase database1 = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database1.getReference("message");
//    myRef.setValue("SUMIT");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgcamera= findViewById(R.id.imgcamera);
        btncamera= findViewById(R.id.btncamera);

        btncamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGallary = new Intent(Intent.ACTION_PICK);
                iGallary.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallary,GALLARY_REQ_CODE);


//                Intent icamera =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(icamera,CAMERA_REQ_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == GALLARY_REQ_CODE){
                imgcamera.setImageURI(data.getData());
            }

//            if(requestCode == CAMERA_REQ_CODE){
//                Toast.makeText(this, "sucess", Toast.LENGTH_SHORT).show();   //this is from camera
//               Bitmap img  = (Bitmap)(data.getExtras().get("data"));
//                imgcamera.setImageBitmap(img);
//            }
//            else{
//                Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
//            }
        }

    }
}
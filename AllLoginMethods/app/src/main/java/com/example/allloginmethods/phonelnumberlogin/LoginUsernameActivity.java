package com.example.allloginmethods.phonelnumberlogin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.allloginmethods.R;
//import com.example.messagewithme.Utils.AndroidUtil;
//import com.example.messagewithme.Utils.MyUser;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;

public class LoginUsernameActivity extends AppCompatActivity {
//    EditText username;
//    ImageView UserImage;
//    Button Nextbtn;
//    private final int GALLARY_REQ_CODE=1;
//    TextView textChosse;
//    ProgressBar progressBar;
//    Uri imageUri;
//    StorageReference storageReference;
//
//    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_username);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



//
//        username=findViewById(R.id.login_username);
//        UserImage=findViewById(R.id.login_image_view);
//        textChosse=findViewById(R.id.choose_image_text);
//        Nextbtn = findViewById(R.id.login_let_me_in_btn);
//        progressBar= findViewById(R.id.login_progress_bar);
//
//        UserImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent iGallary = new Intent(Intent.ACTION_PICK);
//                iGallary.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(iGallary,GALLARY_REQ_CODE);
//            }
//        });
//
//        Nextbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                progressBar.setVisibility(View.VISIBLE);
////                SimpleDateFormat formater =new SimpleDateFormat("yyyy__MM__dd __HH __mm__ss", Locale.CANADA);
////                Date now = new Date();
////                String fileName = formater.format(now);
//
//
//                String phonenum=getIntent().getExtras().getString("phone");
//                String id = getIntent().getExtras().getString("id");
//                MyUser myUser = new MyUser(username.getText().toString(),phonenum );
//                try {
//                    firebaseDatabase.getReference().child("users").child(id).setValue(myUser);
//                }catch (Exception e){
//                    Toast.makeText(LoginUsernameActivity.this, "nabin"+""+e, Toast.LENGTH_SHORT).show();
//                }
//
//
//
//                String fileName =id ; // getIntent().getExtras().getString("phone")+
//
//
//          try {
//              storageReference= FirebaseStorage.getInstance().getReference("images/"+fileName);
//
//          }catch ( Exception e){
//              Toast.makeText(LoginUsernameActivity.this, ""+e, Toast.LENGTH_SHORT).show();
//          }
//
//
//                storageReference.putFile(imageUri)
//                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                            @Override
//                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                UserImage.setImageURI(null);
//                                Toast.makeText(LoginUsernameActivity.this, "User created Successfully", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(LoginUsernameActivity.this,MainActivity.class);
//                                intent.putExtra("id",id);
//                                startActivity(intent);
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                AndroidUtil.showToast(getApplicationContext()," failed to upload the image");
//                            }
//                        });
//
//            }
//        });



    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode == RESULT_OK ){
//            if(requestCode == GALLARY_REQ_CODE){
//                UserImage.setImageURI(data.getData());
//                imageUri=data.getData();
//                textChosse.setVisibility(View.GONE);
//            }
//
////            if(requestCode == CAMERA_REQ_CODE){
////                Toast.makeText(this, "sucess", Toast.LENGTH_SHORT).show();   //this is from camera
////                Bitmap img  = (Bitmap)(data.getExtras().get("data"));
////                imgcamera.setImageBitmap(img);
////            }
////            else{
////                Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
////            }
//        }
//
//    }
}
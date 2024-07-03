package com.example.allloginmethods.phonelnumberlogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.allloginmethods.R;
import com.hbb20.CountryCodePicker;

public class LoginPhoneNumberActivity extends AppCompatActivity {
    CountryCodePicker countryCodePicker;
    EditText phoneInput;
    Button sendOtpbtn;
    ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_phone_number);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//        countryCodePicker=findViewById(R.id.login_countryCode);
//        sendOtpbtn=findViewById(R.id.send_otp_btn);
//        progressBar=findViewById(R.id.login_progress_bar);
//        phoneInput=findViewById(R.id.login_mobileNumber);
//        countryCodePicker.registerCarrierNumberEditText(phoneInput);
//        sendOtpbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              if( !countryCodePicker.isValidFullNumber()) {
//                  phoneInput.setError("phone number not valid");
//                  return;
//              }
//              Intent intent = new Intent(LoginPhoneNumberActivity.this, LoginOtpActivity.class);
//              intent.putExtra("phone",countryCodePicker.getFullNumberWithPlus());
//              startActivity(intent);
//              finish();
//            }
//        });
    }
}
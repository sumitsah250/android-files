package com.example.allloginmethods.phonelnumberlogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.allloginmethods.R;
//import com.example.messagewithme.Utils.AndroidUtil;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.FirebaseException;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.PhoneAuthCredential;
//import com.google.firebase.auth.PhoneAuthOptions;
//import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class LoginOtpActivity extends AppCompatActivity {
//    String phoneNumber;
//    long timeoutSeconds = 60L;
//    String verificationCode;
//
//    PhoneAuthProvider.ForceResendingToken ResendingToken;
//    EditText otpInput;
//    Button nextBtn;
//    ProgressBar progressBar;
//    TextView resendOtpTextView;
//    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_otp);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//        otpInput=findViewById(R.id.login_otp);
//        nextBtn=findViewById(R.id.login_next_btn);
//        progressBar=findViewById(R.id.login_progress_bar);
//        resendOtpTextView=findViewById(R.id.resent_otp_textview);
//
//
//        phoneNumber=getIntent().getExtras().getString("phone");
////        Toast.makeText(this, ""+phoneNumber, Toast.LENGTH_LONG).show();
//        sendOtp(phoneNumber,false);
//
//
//    }
//    void sendOtp(String phoneNumber,boolean isResend){
//        startResendTimer();
//        setInProgress(true);
//        PhoneAuthOptions.Builder builder = PhoneAuthOptions.newBuilder(firebaseAuth)
//                .setPhoneNumber(phoneNumber)
//                .setTimeout(timeoutSeconds, TimeUnit.SECONDS)
//                .setActivity(this)
//                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                    @Override
//                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                        signIn(phoneAuthCredential);
//                        setInProgress(false);
//
//                    }
//
//                    @Override
//                    public void onVerificationFailed(@NonNull FirebaseException e) {
//                        AndroidUtil.showToast(getApplicationContext(),"Otp not sent");
//                        setInProgress(false);
//
//                    }
//
//                    @Override
//                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                        super.onCodeSent(s, forceResendingToken);
//                        verificationCode =s;
//                        ResendingToken=forceResendingToken;
//                        AndroidUtil.showToast(getApplicationContext(),"OTP sent sucessfully");
//                        setInProgress(false);
//                    }
//                });
//        if(isResend){
//            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(ResendingToken).build());
//        }else{
//            PhoneAuthProvider.verifyPhoneNumber(builder.build());
//        }
//        nextBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String enteredOtp  =otpInput.getText().toString();
//               PhoneAuthCredential credential= PhoneAuthProvider.getCredential(verificationCode,enteredOtp);
//               signIn(credential);
//               setInProgress(true);
//            }
//        });
//        resendOtpTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sendOtp(phoneNumber,true);
//            }
//        });
//
//
//
//    }
//    void setInProgress(boolean inProgress){
//        if(inProgress){
//            progressBar.setVisibility(View.VISIBLE);
//            nextBtn.setVisibility(View.GONE);
//        }else{
//            progressBar.setVisibility(View.GONE);
//            nextBtn.setVisibility(View.VISIBLE);
//        }
//    }
//    void  signIn(PhoneAuthCredential phoneAuthCredential){
//        setInProgress(true);
//        firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//
//                setInProgress(false);
//
//                FirebaseUser user = task.getResult().getUser();
//                long creationTimestamp = user.getMetadata().getCreationTimestamp();
//                long lastSignInTimestamp = user.getMetadata().getLastSignInTimestamp();
//                if (creationTimestamp == lastSignInTimestamp) {
//                    String id = task.getResult().getUser().getUid();
//                    if(task.isSuccessful()){
//                        Intent intent = new Intent(LoginOtpActivity.this,LoginUsernameActivity.class);
//                        intent.putExtra("phone",phoneNumber);
//                        intent.putExtra("id",id);
//                        startActivity(intent);
//                        finish();
//                    }else{
//                        AndroidUtil.showToast(getApplicationContext(),"OTP verification failed");
//                    }
//                    //do create new user
//                } else {
//                    startActivity(new Intent(LoginOtpActivity.this,MainActivity.class));
//                    //user is exists, just do login
//                }
//
//            }
//        });

    }
//    void startResendTimer(){
//        resendOtpTextView.setEnabled(false);
//        Timer timer = new Timer();
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                timeoutSeconds--;
//                resendOtpTextView.setText("Resend OTP in "+timeoutSeconds+" seconds");
//                if(timeoutSeconds<=0){
//                    timeoutSeconds=60L;
//                    timer.cancel();
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            resendOtpTextView.setEnabled(true);
//
//                        }
//                    });
//                }
//
//            }
//        },0,1000);
//    }
}
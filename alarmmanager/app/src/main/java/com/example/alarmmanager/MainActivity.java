package com.example.alarmmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    static final int ALARM_REQ = 100;
    EditText editText;
    TextView txtview;
    Button btnfire;
    LocalDateTime now = LocalDateTime.now();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtview = findViewById(R.id.txtView);
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();
        int millis = now.get(ChronoField.MILLI_OF_SECOND);

        txtview.setText("" + hour + ":" + minute);

        Intent intent = new Intent(MainActivity.this, MyReciver.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        editText = findViewById(R.id.edttime);
        btnfire = findViewById(R.id.btnfire);
        PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
//        long triggertime = System.currentTimeMillis() + Getmili(19,22);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, triggertime, pi);

        btnfire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] arr;
                arr=(editText.getText().toString().split(":"));
                long triggertime1 = System.currentTimeMillis() + Getmili(Long.parseLong(arr[0]),Long.parseLong(arr[1]));
                alarmManager.set(AlarmManager.RTC_WAKEUP, triggertime1, pi);

            }
        });

    }

public long Getmili(long hour,long minute){
    long h1 =now.getHour();
    long h2 = now.getMinute();
    long x = (((hour*3600 + minute*60) - (h1*3600 +h2*60))*1000);
    Toast.makeText(this, ""+h1+":"+h2+"/"+hour+":"+minute+"="+x, Toast.LENGTH_SHORT).show();
    return x;
}

}
package com.boss.alarm;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.boss.alarm.databinding.ActivityMainBinding;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private MaterialTimePicker timePicker;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


            binding= ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            createNotificationChannel();
            binding.btnselecttime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timePicker = new MaterialTimePicker.Builder()
                            .setTimeFormat(TimeFormat.CLOCK_12H)
                            .setHour(12)
                            .setMinute(0)
                            .setTitleText("Select Alarm Time")
                            .build();

                    timePicker.show(getSupportFragmentManager(),"androidknowledge");
                    timePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(timePicker.getHour()>12){
                                binding.btnselecttime.setText(
                                        String.format("%02d",(timePicker.getHour()-12))+":"+String.format("%02d",timePicker.getMinute())+"PM"
                                );
                            }else{
                                binding.btnselecttime.setText(
                                        String.format(timePicker.getHour()+":"+timePicker.getMinute()+"AM")
                                );
                            }
                            calendar= Calendar.getInstance();
                            calendar.set(Calendar.HOUR_OF_DAY,timePicker.getHour());
                            calendar.set(Calendar.MINUTE,timePicker.getMinute());
                            calendar.set(Calendar.SECOND,0);
                            calendar.set(Calendar.MILLISECOND,0);


                        }
                    });
                }
            });
            binding.btnselecttime1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(MainActivity.this,AlarmReciver.class);
                    pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT) ;

                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
                    Toast.makeText(MainActivity.this, "Alarm Set", Toast.LENGTH_SHORT).show();

                }
            });
            binding.btncancelalarm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,AlarmReciver.class);
                    pendingIntent = pendingIntent.getBroadcast(MainActivity.this,0,intent, PendingIntent.FLAG_IMMUTABLE |PendingIntent.FLAG_UPDATE_CURRENT);

                    if(alarmManager == null){
                        alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    }
                    alarmManager.cancel(pendingIntent);
                    Toast.makeText(MainActivity.this, "Alarm Cancel", Toast.LENGTH_SHORT).show();
                }
            });

    }
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name ="akchannel";
            String desc="Channel for Alarm Manager";
            int imp = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel =new NotificationChannel("androidknowledge",name,imp);
            channel.setDescription(desc);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);



        }
    }
}
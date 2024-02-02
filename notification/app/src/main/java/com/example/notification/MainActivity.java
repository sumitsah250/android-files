package com.example.notification;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.res.ResourcesCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "my channel";
    private static final int NOTIFICATION_ID = 100;
    private  static final int REQ_CODE=100;
    Notification notification;
    TextView txthellow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txthellow=findViewById(R.id.txtellow);

        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.i,null);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap largeIcon = bitmapDrawable.getBitmap();


        NotificationManager nm= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notification = new Notification.Builder(MainActivity.this)
//                    .setLargeIcon(largeIcon)
                    .setSmallIcon(R.drawable.i)

                    .setContentText("new massage")
                    .setSubText("new massage from sumit")
//                    .setOngoing(true)
                    .setChannelId(CHANNEL_ID)
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .build();
            nm.createNotificationChannel(new NotificationChannel(CHANNEL_ID,"new channel",NotificationManager.IMPORTANCE_HIGH));
            nm.notify(NOTIFICATION_ID,notification);
        }

    }
}
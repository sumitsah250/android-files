package com.example.alarmmanager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;


public class MyReciver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "mychannel";
    private static final int NOTIFICATION_ID = 100;
    MediaPlayer mp;
    Notification notification;

    @Override
    public void onReceive(Context context, Intent intent) {
//        mp = MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
//        mp.setLooping(true);
//        mp.start();

        NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notification = new Notification.Builder(context)
//                    .setLargeIcon(largeIcon)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentText("their are task o be completed")
                    .setSubText("new massage from sumit")
//                    .setOngoing(true)
                    .setChannelId(CHANNEL_ID)
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .build();
            nm.createNotificationChannel(new NotificationChannel(CHANNEL_ID, "new channel", NotificationManager.IMPORTANCE_HIGH));
            nm.notify(NOTIFICATION_ID, notification);

        }
    }
}

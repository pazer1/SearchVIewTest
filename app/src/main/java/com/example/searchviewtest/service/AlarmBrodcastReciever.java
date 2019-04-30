package com.example.searchviewtest.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.searchviewtest.R;

public class AlarmBrodcastReciever extends BroadcastReceiver {

    private final static int NOTIFICATION_ID = 222;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AlarmBroadcastReceiver","onReceive");

        RemoteViews notificationLayout = new RemoteViews(context.getPackageName(), R.layout.notification_small);

        Notification notification = new NotificationCompat.Builder(context,"notification_channel_id")
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notificationLayout)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID,notification);

    }
}

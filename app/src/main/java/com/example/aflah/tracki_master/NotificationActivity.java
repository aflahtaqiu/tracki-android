package com.example.aflah.tracki_master;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by X441U on 10/30/2018.
 */

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        demoHeadsUp(null);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void demoHeadsUp(View view) {
        //To be heads up , the process is the same but setPriority should be called with at leas
        //PRIORITY_HIGHT , and the notification should use either sound or vibration
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(this, NavigationActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        Notification.Builder builder = new Notification.Builder(this);

        builder.setContentTitle("Tracki")
                .setContentText("Pasang textnya disini...")
                .setSmallIcon(R.mipmap.logotracki)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.logotracki))
                .setContentIntent(pi)
                .setVibrate(new long[]{250, 250, 250, 250})
                .setSound(soundUri)
                .setPriority(Notification.PRIORITY_MAX);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

}

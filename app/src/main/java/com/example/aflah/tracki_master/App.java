package com.example.aflah.tracki_master;

import android.annotation.TargetApi;
import android.app.Application;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.eyro.cubeacon.CBBootstrapListener;
import com.eyro.cubeacon.CBBootstrapRegion;
import com.eyro.cubeacon.CBRegion;
import com.eyro.cubeacon.Cubeacon;
import com.eyro.cubeacon.LogLevel;
import com.eyro.cubeacon.Logger;
import com.eyro.cubeacon.MonitoringState;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

import java.util.UUID;

/**
 * Created by Ennobel on 10/4/2018.
 */

public class App extends Application implements BootstrapNotifier {
    private static final String TAG = App.class.getSimpleName();
    private RegionBootstrap regionBootstrap;
    private BackgroundPowerSaver backgroundPowerSaver;
    private BeaconManager beaconManager;


    @Override
    public void onCreate() {
        super.onCreate();

        // set Cubeacon SDK log level to verbose mode
        Logger.setLogLevel(LogLevel.VERBOSE);

        // enable background power saver to save battery life up to 60%
        Cubeacon.setBackgroundPowerSaver(true);

        // initializing Cubeacon SDK
        Cubeacon.initialize(this);


        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        backgroundPowerSaver = new BackgroundPowerSaver(this);
        Region region = new Region("backgroundRegion", null, null, null);
        beaconManager.setBackgroundBetweenScanPeriod(0);
        beaconManager.setBackgroundScanPeriod(500);

        String CHANNEL_ID = "TRACKI";
        NotificationChannel mChannel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID, "nobel", NotificationManager.IMPORTANCE_HIGH);
        }
        Notification.Builder builder = new Notification.Builder(this);
//        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setContentTitle("Scanning for Beacons");
        Intent intent = new Intent(this, NavigationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        builder.setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID);
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(mChannel);
        }

        beaconManager.enableForegroundServiceScanning(builder.build(), 456);

        beaconManager.setEnableScheduledScanJobs(false);
        regionBootstrap= new RegionBootstrap(this,region);

    }

    @Override
    public void didEnterRegion(Region region) {
    Log.v("backgrounda","masuk region");
//        Intent intent = new Intent(this, NotificationActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        this.startActivity(intent);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(this, NavigationActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        String CHANNEL_ID = "TRACKI";
        NotificationChannel mChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID, "nobel", NotificationManager.IMPORTANCE_HIGH);
        }
        Notification.Builder builder = new Notification.Builder(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setContentTitle("Tracki")
                    .setContentText("toko dengan cubeacon terdeteksi")
                    .setSmallIcon(R.mipmap.logotracki)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.logotracki))
                    .setContentIntent(pi)
                    .setVibrate(new long[]{250, 250, 250, 250})
                    .setSound(soundUri)
                    .setChannelId(CHANNEL_ID)
                    .setPriority(Notification.PRIORITY_MAX);
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(mChannel);
        }else{

            builder.setContentTitle("Tracki")
                    .setContentText("toko dengan cubeacon terdeteksi")
                    .setSmallIcon(R.mipmap.logotracki)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.logotracki))
                    .setContentIntent(pi)
                    .setVibrate(new long[]{250, 250, 250, 250})
                    .setSound(soundUri)
                    .setPriority(Notification.PRIORITY_MAX);
        }

        notificationManager.notify(0, builder.build());
    }

    @Override
    public void didExitRegion(Region region) {
        Log.v("backgrounda","keluar region");
    }

    @Override
    public void didDetermineStateForRegion(int i, Region region) {

    }

    }


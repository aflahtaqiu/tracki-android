package com.example.aflah.tracki_master;

import android.app.Application;
import android.util.Log;

import com.eyro.cubeacon.CBBootstrapListener;
import com.eyro.cubeacon.CBBootstrapRegion;
import com.eyro.cubeacon.CBRegion;
import com.eyro.cubeacon.Cubeacon;
import com.eyro.cubeacon.LogLevel;
import com.eyro.cubeacon.Logger;
import com.eyro.cubeacon.MonitoringState;

import java.util.UUID;

/**
 * Created by Ennobel on 10/4/2018.
 */

public class App extends Application implements CBBootstrapListener {
    private static final String TAG = App.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        // set Cubeacon SDK log level to verbose mode
        Logger.setLogLevel(LogLevel.VERBOSE);

        // enable background power saver to save battery life up to 60%
        Cubeacon.setBackgroundPowerSaver(true);

        // initializing Cubeacon SDK
        Cubeacon.initialize(this);

        // setup region scanning when OS boot completed
        CBBootstrapRegion.setup(this,
                new CBRegion("com.eyro.cubeacon.ranging_region",
                        UUID.fromString("CB10023F-A318-3394-4199-A8730C7C1AEC")));
    }

    @Override
    public void didEnterRegion(CBRegion region) {
        Log.d(TAG, "Entering region: " + region);
    }

    @Override
    public void didExitRegion(CBRegion region) {
        Log.d(TAG, "Exiting region: " + region);
    }

    @Override
    public void didDetermineStateForRegion(MonitoringState state, CBRegion region) {
        Log.d(TAG, "Region: " + region + ", state: " + state.name());
    }
}

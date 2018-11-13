package com.example.aflah.tracki_master;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;

import com.example.aflah.tracki_master.Auth.LoginActivity;
import com.example.aflah.tracki_master.Model.UserLogin;
import com.example.aflah.tracki_master.NavbarFragment.AccountFragment;
import com.example.aflah.tracki_master.NavbarFragment.HomeFragment;
import com.eyro.cubeacon.CBBeacon;
import com.eyro.cubeacon.CBRangingListener;
import com.eyro.cubeacon.CBRegion;
import com.eyro.cubeacon.CBServiceListener;
import com.eyro.cubeacon.Cubeacon;
import com.eyro.cubeacon.SystemRequirementManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
//        HomeFragment.OnFragmentInteractionListener,
        AccountFragment.OnFragmentInteractionListener,
        CBRangingListener, CBServiceListener{

    private static final String TAG = NavigationActivity.class.getSimpleName();
    private HomeFragment homeFragment = null;
    private AccountFragment accountFragment = null;
    private FragmentTransaction fragmentTransaction;
    private OnCubeaconUpdated mOnCubeaconUpdated;

    private Cubeacon cubeacon;
    private List<Map<String, String>> data;
    private List<CBBeacon> beacons;
    SharedPreferences sharedPreferences;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (homeFragment == null){
                        homeFragment = new HomeFragment();
                    }
                    mOnCubeaconUpdated = homeFragment;
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, homeFragment, "").commit();
                    return true;
                case R.id.navigation_account:
                    if (accountFragment == null){
                        accountFragment = new AccountFragment();
                    }
                    if (sharedPreferences.getString("tokenLogin", "").isEmpty()){
                        startActivity(new Intent(NavigationActivity.this, LoginActivity.class));
                        finish();
                    }else {
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content, accountFragment, "").commit();
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Log.v("masukActivity", "navigation");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);


        try{
            if (getIntent()!=null){
                Log.v("AccountFragmentLoc", " ada isinya " +getIntent().getIntExtra("AccountFragmentLoc", 0));
                if (getIntent().getIntExtra("AccountFragmentLoc", 0) != 0){
                    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
                    navigation.setSelectedItemId(getIntent().getIntExtra("AccountFragmentLoc", 0));
                }
            }

        }catch (Exception e){
            Log.v("AccountFragmentLoc", " " +e.getMessage() );
        }

        data = new ArrayList<>();
        beacons = new ArrayList<>();
        cubeacon = Cubeacon.getInstance();

        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("userLogin", "");
        UserLogin userLogin = gson.fromJson(json, UserLogin.class);
        String userToken = sharedPreferences.getString("tokenLogin", "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // check all requirement like is BLE available, is bluetooth on/off,
        // location service for Android API 23 or later
        if (SystemRequirementManager.checkAllRequirementUsingDefaultDialog(this)) {
            // connecting to Cubeacon service when all requirements completed
            cubeacon.connect(this);
            // disable background mode, because we're going to use full
            // scanning resource in foreground mode
            cubeacon.setBackgroundMode(false);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // enable background mode when this activity paused
        cubeacon.setBackgroundMode(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        cubeacon.disconnect(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void didRangeBeaconsInRegion(List<CBBeacon> list, CBRegion cbRegion) {
        this.beacons = list;

        String title, subtitle;
        Map<String, String> map;

        // clear data
        data.clear();
        for (CBBeacon beacon : beacons) {
            title = beacon.getProximityUUID().toString().toUpperCase();
            subtitle = String.format(Locale.getDefault(), "M:%d m:%d RSSI:%d Accuracy:%.2fm",
                    beacon.getMajor(), beacon.getMinor(), beacon.getRssi(), beacon.getAccuracy());
            map = new HashMap<>();
            map.put("title", title);
            map.put("subtitle", subtitle);
            data.add(map);
        }
        //TODO : IMPLEMENT INTERFACE


        // update view using runnable
        mOnCubeaconUpdated.setData(data,list);
    }

    @Override
    public void onBeaconServiceConnect() {
        cubeacon.addRangingListener(this);
        try {
            // create a new region for ranging beacons
            CBRegion region = new CBRegion("com.eyro.cubeacon.ranging_region",
                    UUID.fromString("CB10023F-A318-3394-4199-A8730C7C1AEC"));
            // start ranging beacons using region
            cubeacon.startRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
            Log.e(TAG, "Error while start ranging beacon, " + e);
        }
    }

    public interface OnCubeaconUpdated{
        void setData(List<Map<String, String>> data,List<CBBeacon> cub);
    }
}

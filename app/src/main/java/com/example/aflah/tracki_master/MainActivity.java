package com.example.aflah.tracki_master;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eyro.cubeacon.Cubeacon;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Cubeacon.initialize(this);
    }
}

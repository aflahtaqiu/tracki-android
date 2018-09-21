package com.example.aflah.tracki_master.View.Toko;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;

import com.example.aflah.tracki_master.R;

public class DetailTokoActivity extends AppCompatActivity implements MakananFragment.OnFragmentInteractionListener, MinumanFragment.OnFragmentInteractionListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_toko);

        final CheckBox checkBox = (CheckBox) findViewById(R.id.favourite);

        TabLayout menuLayout = (TabLayout)findViewById(R.id.menuLayout);
        menuLayout.addTab(menuLayout.newTab().setText("Makanan"));
        menuLayout.addTab(menuLayout.newTab().setText("Minuman"));

        menuLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager menuPager = (ViewPager)findViewById(R.id.pager);
        final MenuAdapter adapter = new MenuAdapter(getSupportFragmentManager(), menuLayout.getTabCount());
        menuPager.setAdapter(adapter);
        menuPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(menuLayout));

        menuLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                menuPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

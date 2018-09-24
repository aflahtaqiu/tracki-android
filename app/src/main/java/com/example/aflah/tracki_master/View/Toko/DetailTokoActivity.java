package com.example.aflah.tracki_master.View.Toko;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.aflah.tracki_master.R;

import java.util.Timer;
import java.util.TimerTask;

public class DetailTokoActivity extends AppCompatActivity implements MakananFragment.OnFragmentInteractionListener, MinumanFragment.OnFragmentInteractionListener {

    ViewPager viewPager;
//    LinearLayout sliderDotPanel;
//    private int dotcount;
//    private ImageView[] dots;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_toko);

        viewPager = (ViewPager)findViewById(R.id.viewPager);

//        sliderDotPanel = (LinearLayout) findViewById(R.id.SliderDots);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);

//        dotcount = viewPagerAdapter.getCount();
//        dots = new ImageView[dotcount];

//        for (int i =0 ; i < dotcount ; i++){
//
//            dots[i] = new ImageView(this);
//            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonactive_dot));
//
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//            params.setMargins(8, 0,8,0);
//
//            sliderDotPanel.addView(dots[i], params);
//        }

//        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
//
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//                for (int i = 0;i < dotcount;i++){
//
//                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
//                }
//                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000,4000);
//        sini
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

    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            DetailTokoActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(viewPager.getCurrentItem() == 0 ){
                        viewPager.setCurrentItem(1);
                    }else if(viewPager.getCurrentItem() == 1){
                        viewPager.setCurrentItem(2);
                    }else{
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

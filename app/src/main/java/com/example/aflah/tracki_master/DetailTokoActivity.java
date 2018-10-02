package com.example.aflah.tracki_master;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.aflah.tracki_master.Adapter.DetailTokoViewPagerAdapter;
import com.example.aflah.tracki_master.DetailTokoFragment.DetailTokoFragment;
import com.example.aflah.tracki_master.DetailTokoFragment.MakananFragment;
import com.example.aflah.tracki_master.DetailTokoFragment.MinumanFragment;

public class DetailTokoActivity extends AppCompatActivity implements DetailTokoFragment.OnFragmentInteractionListener, MakananFragment.OnFragmentInteractionListener, MinumanFragment.OnFragmentInteractionListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DetailTokoViewPagerAdapter detailTokoViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_toko);

        tabLayout = (TabLayout) findViewById(R.id.tablayout_detailtoko);
        viewPager = (ViewPager) findViewById(R.id.viewpager_detailtoko);
        detailTokoViewPagerAdapter = new DetailTokoViewPagerAdapter(getSupportFragmentManager());

        detailTokoViewPagerAdapter.AddFragment(new DetailTokoFragment(), "");
        detailTokoViewPagerAdapter.AddFragment(new MakananFragment(), "");
        detailTokoViewPagerAdapter.AddFragment(new MinumanFragment(), "");

        viewPager.setAdapter(detailTokoViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_store);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_restaurant);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_local_cafe);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

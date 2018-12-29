package com.example.aflah.tracki_master.DetailToko;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.aflah.tracki_master.Adapter.CarouselDetailTokoAdapter;
import com.example.aflah.tracki_master.Adapter.DetailTokoViewPagerAdapter;
import com.example.aflah.tracki_master.Model.Response.ResponseDetailToko;
import com.example.aflah.tracki_master.R;
import com.example.aflah.tracki_master.Retrofit.ApiRequest;
import com.example.aflah.tracki_master.Retrofit.RetroServer;
import com.example.aflah.tracki_master.View.MakananFragment;
import com.example.aflah.tracki_master.View.MinumanFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTokoActivity extends AppCompatActivity implements DetailTokoFragment.OnFragmentInteractionListener, MakananFragment.OnFragmentInteractionListener, MinumanFragment.OnFragmentInteractionListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DetailTokoViewPagerAdapter detailTokoViewPagerAdapter;

    ViewPager viewPager_CarouselDetailToko;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_toko);

        linearLayout = (LinearLayout) findViewById(R.id.linearLayout_detailToko);
        tabLayout = (TabLayout) findViewById(R.id.tablayout_detailtoko);
        viewPager = (ViewPager) findViewById(R.id.viewpager_detailtoko);
        viewPager_CarouselDetailToko = (ViewPager) findViewById(R.id.viewPager_carousel_detailToko);
        detailTokoViewPagerAdapter = new DetailTokoViewPagerAdapter(getSupportFragmentManager());

        linearLayout.setVisibility(View.GONE);

        detailTokoViewPagerAdapter.AddFragment(new DetailTokoFragment(), "About");
        detailTokoViewPagerAdapter.AddFragment(new MakananFragment(), "Makanan");
        detailTokoViewPagerAdapter.AddFragment(new MinumanFragment(), "Minuman");

        List<String> urlImageList = new ArrayList<>();
        int idToko = getIntent().getExtras().getInt("idTokoTerdekat");
        ApiRequest apiRequest = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseDetailToko> getData = apiRequest.getStoreByID(idToko);
        getData.enqueue(new Callback<ResponseDetailToko>() {
            @Override
            public void onResponse(Call<ResponseDetailToko> call, Response<ResponseDetailToko> response) {
                for (int i =0 ;i< response.body().getStore().getGalleries().size();i++){
                    urlImageList.add(response.body().getStore().getGalleries().get(i).getPicture());
                }
                CarouselDetailTokoAdapter carouselDetailTokoAdapter = new CarouselDetailTokoAdapter(DetailTokoActivity.this, urlImageList);
                viewPager_CarouselDetailToko.setAdapter(carouselDetailTokoAdapter);
            }

            @Override
            public void onFailure(Call<ResponseDetailToko> call, Throwable t) {

            }
        });

        viewPager.setAdapter(detailTokoViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimeCarousel(), 4000, 4000);
        linearLayout.setVisibility(View.VISIBLE);
    }

    public class TimeCarousel extends TimerTask{
        @Override
        public void run() {
            DetailTokoActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    switch (viewPager_CarouselDetailToko.getCurrentItem()){
                        case 0 : viewPager_CarouselDetailToko.setCurrentItem(1); break;
                        case 1 : viewPager_CarouselDetailToko.setCurrentItem(2);break;
                        case 2 : viewPager_CarouselDetailToko.setCurrentItem(0);break;
                    }
                }
            });
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) { }
}

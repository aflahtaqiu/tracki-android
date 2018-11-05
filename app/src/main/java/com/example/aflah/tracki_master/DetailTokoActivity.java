package com.example.aflah.tracki_master;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.aflah.tracki_master.Adapter.CarouselDetailTokoAdapter;
import com.example.aflah.tracki_master.Adapter.DetailTokoViewPagerAdapter;
import com.example.aflah.tracki_master.DetailTokoFragment.DetailTokoFragment;
import com.example.aflah.tracki_master.DetailTokoFragment.MakananFragment;
import com.example.aflah.tracki_master.DetailTokoFragment.MinumanFragment;
import com.example.aflah.tracki_master.Model.Response.ResponseDetailToko;
import com.example.aflah.tracki_master.Retrofit.ApiRequest;
import com.example.aflah.tracki_master.Retrofit.RetroServer;

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

    LinearLayout linearLayout_DotsPanel;
    private int dotsCount;
    private ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_toko);

        tabLayout = (TabLayout) findViewById(R.id.tablayout_detailtoko);
        viewPager = (ViewPager) findViewById(R.id.viewpager_detailtoko);
        viewPager_CarouselDetailToko = (ViewPager) findViewById(R.id.viewPager_carousel_detailToko);
        detailTokoViewPagerAdapter = new DetailTokoViewPagerAdapter(getSupportFragmentManager());

        detailTokoViewPagerAdapter.AddFragment(new DetailTokoFragment(), "");
        detailTokoViewPagerAdapter.AddFragment(new MakananFragment(), "");
        detailTokoViewPagerAdapter.AddFragment(new MinumanFragment(), "");

        linearLayout_DotsPanel = (LinearLayout) findViewById(R.id.layoutDots_detailToko);

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

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_store);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_restaurant);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_local_cafe);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimeCarousel(), 4000, 4000);
    }

    public void setDotsCount(int dotsCount) {
        this.dotsCount = dotsCount;
    }

    public class TimeCarousel extends TimerTask{

        @Override
        public void run() {

            DetailTokoActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (viewPager_CarouselDetailToko.getCurrentItem() == 0)
                        viewPager_CarouselDetailToko.setCurrentItem(1, true);
                    else if (viewPager_CarouselDetailToko.getCurrentItem() == 1)
                        viewPager_CarouselDetailToko.setCurrentItem(2, true);
                    else
                        viewPager_CarouselDetailToko.setCurrentItem(0, true);
                }
            });
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

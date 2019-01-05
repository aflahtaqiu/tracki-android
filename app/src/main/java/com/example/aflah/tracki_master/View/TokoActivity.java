package com.example.aflah.tracki_master.View;

import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.aflah.tracki_master.Adapter.CarouselDetailTokoAdapter;
import com.example.aflah.tracki_master.Adapter.DetailTokoViewPagerAdapter;
import com.example.aflah.tracki_master.Contract.TokoContract;
import com.example.aflah.tracki_master.Injection;
import com.example.aflah.tracki_master.Presenter.TokoPresenter;
import com.example.aflah.tracki_master.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TokoActivity extends AppCompatActivity implements DetailTokoFragment.OnFragmentInteractionListener,
        MakananFragment.OnFragmentInteractionListener, MinumanFragment.OnFragmentInteractionListener, TokoContract.view {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DetailTokoViewPagerAdapter detailTokoViewPagerAdapter;

    ViewPager viewPager_CarouselDetailToko;
    ConstraintLayout constraintLayout;
    private CarouselDetailTokoAdapter carouselDetailTokoAdapter;
    List<String> urlImageList = new ArrayList<>();

    TokoPresenter presenter = new TokoPresenter(Injection.provideStoreRepository(), this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_toko);

        initViews();
        constraintLayout.setVisibility(View.GONE);

        int idToko = getIntent().getExtras().getInt("idTokoTerdekat");

        presenter.getImagesUrl(idToko);
        initCarouselAdapter();

        detailTokoViewPagerAdapter = new DetailTokoViewPagerAdapter(getSupportFragmentManager());
        detailTokoViewPagerAdapter.AddFragment(new DetailTokoFragment(), "About");
        detailTokoViewPagerAdapter.AddFragment(new MakananFragment(), "Makanan");
        detailTokoViewPagerAdapter.AddFragment(new MinumanFragment(), "Minuman");
        viewPager.setAdapter(detailTokoViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimeCarousel(), 4000, 4000);
        constraintLayout.setVisibility(View.VISIBLE);
    }

    private void initCarouselAdapter() {
        carouselDetailTokoAdapter = new CarouselDetailTokoAdapter(TokoActivity.this, urlImageList);
        viewPager_CarouselDetailToko.setAdapter(carouselDetailTokoAdapter);
    }

    private void initViews() {
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraint_detailToko);
        tabLayout = (TabLayout) findViewById(R.id.tablayout_detailtoko);
        viewPager = (ViewPager) findViewById(R.id.viewpager_detailtoko);
        viewPager_CarouselDetailToko = (ViewPager) findViewById(R.id.viewPager_carousel_detailToko);
    }

    @Override
    public void showDataList(List<String> urlList) {
        this.urlImageList.addAll(urlList);
        carouselDetailTokoAdapter.notifyDataSetChanged();
    }

    @Override
    public void showFailureMessage(String errMsg) {
        Toast.makeText(this, "Ada error : " + errMsg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showViewPager() {
        detailTokoViewPagerAdapter = new DetailTokoViewPagerAdapter(getSupportFragmentManager());
        detailTokoViewPagerAdapter.AddFragment(new DetailTokoFragment(), "About");
        detailTokoViewPagerAdapter.AddFragment(new MakananFragment(), "Makanan");
        detailTokoViewPagerAdapter.AddFragment(new MinumanFragment(), "Minuman");
        viewPager.setAdapter(detailTokoViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public class TimeCarousel extends TimerTask{
        @Override
        public void run() {
            TokoActivity.this.runOnUiThread(new Runnable() {
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

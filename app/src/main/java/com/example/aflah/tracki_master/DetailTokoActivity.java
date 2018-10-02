package com.example.aflah.tracki_master;

import android.app.ProgressDialog;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.aflah.tracki_master.Adapter.DetailTokoViewPagerAdapter;
import com.example.aflah.tracki_master.Adapter.StoreAdapter;
import com.example.aflah.tracki_master.DetailTokoFragment.DetailTokoFragment;
import com.example.aflah.tracki_master.DetailTokoFragment.MakananFragment;
import com.example.aflah.tracki_master.DetailTokoFragment.MinumanFragment;
import com.example.aflah.tracki_master.Model.Store;
import com.example.aflah.tracki_master.Model.StoreResponse;
import com.example.aflah.tracki_master.Retrofit.Client;
import com.example.aflah.tracki_master.Retrofit.RequestData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTokoActivity extends AppCompatActivity implements DetailTokoFragment.OnFragmentInteractionListener, MakananFragment.OnFragmentInteractionListener, MinumanFragment.OnFragmentInteractionListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DetailTokoViewPagerAdapter detailTokoViewPagerAdapter;

    private DetailTokoFragment detailTokoFragment = new DetailTokoFragment();

    private RecyclerView recyclerView_detailtoko;
    private Store store;
    ProgressDialog progressDialog;
    private SwipeRefreshLayout swipeRefreshLayout;

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

        initViews();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipecontainer_detailtoko);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                loadJSON();
                Toast.makeText(DetailTokoActivity.this, "Refreshed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initViews () {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mengambil data toko");
        progressDialog.setCancelable(false);
        progressDialog.show();

        recyclerView_detailtoko = (RecyclerView) detailTokoFragment.getActivity().findViewById(R.id.recyclerview_detailtoko);
        recyclerView_detailtoko.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView_detailtoko.smoothScrollToPosition(0);
        loadJSON();
    }

    private void loadJSON () {
        try {
            Client client = new Client();
            RequestData apiRequest = Client.getClient().create(RequestData.class);
            Call<StoreResponse> storeResponseCall = apiRequest.getStores();
            storeResponseCall.enqueue(new Callback<StoreResponse>() {
                @Override
                public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                    List<Store> stores = response.body().getStores();
                    recyclerView_detailtoko.setAdapter(new StoreAdapter(stores, getApplicationContext()));
                    recyclerView_detailtoko.smoothScrollToPosition(0);
                    swipeRefreshLayout.setRefreshing(false);
                    progressDialog.hide();
                }

                @Override
                public void onFailure(Call<StoreResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(DetailTokoActivity.this, "Error Failure ", Toast.LENGTH_LONG).show();
                    progressDialog.hide();
                }
            });
        }catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

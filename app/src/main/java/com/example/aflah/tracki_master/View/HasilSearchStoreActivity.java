package com.example.aflah.tracki_master.View;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.aflah.tracki_master.Adapter.HasilSearchStoreAdapter;
import com.example.aflah.tracki_master.Contract.HasilSearchStoreContract;
import com.example.aflah.tracki_master.Injection;
import com.example.aflah.tracki_master.Model.Store;
import com.example.aflah.tracki_master.Presenter.HasilSearchStorePresenter;
import com.example.aflah.tracki_master.R;

import java.util.ArrayList;
import java.util.List;

public class HasilSearchStoreActivity extends Activity implements HasilSearchStoreContract.view {
    RecyclerView recyclerViewHasilSearch;
    private HasilSearchStoreAdapter hasilSearchStoreAdapter;
    List<Store> listStore = new ArrayList<>();

    private HasilSearchStorePresenter presenter = new HasilSearchStorePresenter(Injection.provideStoreRepository(),this);

    public HasilSearchStoreActivity(){}

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_search);
        recyclerViewHasilSearch = findViewById(R.id.hasil_search);

        String keyword =  getIntent().getExtras().getString("search");
        presenter.getListStore(keyword);
        initAdapter();
    }

    @Override
    public void showDataList(List<Store> listStore) {
        this.listStore.addAll(listStore);
        hasilSearchStoreAdapter.notifyDataSetChanged();
    }

    @Override
    public void showFailureMessage(String errorMessage) {
        Toast.makeText(this, "Ada error : " + errorMessage, Toast.LENGTH_LONG).show();
    }

    public  void initAdapter(){
        hasilSearchStoreAdapter = new HasilSearchStoreAdapter(this,listStore);
        recyclerViewHasilSearch.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewHasilSearch.setAdapter(hasilSearchStoreAdapter);
    }
}

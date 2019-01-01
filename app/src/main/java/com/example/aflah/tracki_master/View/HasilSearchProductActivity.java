package com.example.aflah.tracki_master.View;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.aflah.tracki_master.Adapter.HasilSearchProductAdapter;
import com.example.aflah.tracki_master.Contract.HasilSearchProductContract;
import com.example.aflah.tracki_master.Injection;
import com.example.aflah.tracki_master.Model.Product;
import com.example.aflah.tracki_master.Presenter.HasilSearchProductPresenter;
import com.example.aflah.tracki_master.R;

import java.util.ArrayList;
import java.util.List;


public class HasilSearchProductActivity extends Activity implements HasilSearchProductContract.view {
    RecyclerView recyclerViewHasilSearch;
    private HasilSearchProductAdapter hasilSearchProductAdapter;
    List<Product> listProduct = new ArrayList<>();

    private HasilSearchProductPresenter presenter = new HasilSearchProductPresenter(Injection.provideProductRepository(),this);

    public HasilSearchProductActivity(){}

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_search);
        recyclerViewHasilSearch = findViewById(R.id.hasil_search);

        String keyword =  getIntent().getExtras().getString("search");
        presenter.getListProduct(keyword);
        initAdaptor();
    }


    @Override
    public void showDataList(List<Product> listProduct) {
        this.listProduct.addAll(listProduct);
        hasilSearchProductAdapter.notifyDataSetChanged();
    }

    @Override
    public void showFailureMessage(String errorMessage) {
        Toast.makeText(this, "Ada error : " + errorMessage, Toast.LENGTH_LONG).show();
    }
    public void initAdaptor(){
        hasilSearchProductAdapter = new HasilSearchProductAdapter(this , listProduct);
        recyclerViewHasilSearch.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewHasilSearch.setAdapter(hasilSearchProductAdapter);
    }
}

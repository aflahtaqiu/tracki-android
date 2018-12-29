package com.example.aflah.tracki_master.Presenter;

import com.example.aflah.tracki_master.Contract.MinumanContract;
import com.example.aflah.tracki_master.Data.ProductRepository;
import com.example.aflah.tracki_master.Data.ProductSource;
import com.example.aflah.tracki_master.Model.Product;

import java.util.List;

public class MinumanPresenter implements MinumanContract.presenter {

    private ProductRepository productRepository;
    private MinumanContract.view view;

    public MinumanPresenter(ProductRepository productRepository, MinumanContract.view view) {
        this.productRepository = productRepository;
        this.view = view;
    }

    @Override
    public void getAllMinuman(int idToko) {
        productRepository.getMinuman(idToko, new ProductSource.GetMinumanCallback() {
            @Override
            public void onSuccess(List<Product> listMinuman) {
                if (listMinuman.size() != 0) view.showDataList(listMinuman);
                else view.showIfNoMinuman();
            }

            @Override
            public void onFailure(String errMsg) {
                view.showFailure(errMsg);
            }
        });
    }
}

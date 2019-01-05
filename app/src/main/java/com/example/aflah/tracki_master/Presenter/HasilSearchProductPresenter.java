package com.example.aflah.tracki_master.Presenter;


import com.example.aflah.tracki_master.Contract.HasilSearchProductContract;
import com.example.aflah.tracki_master.Data.ProductRepository;
import com.example.aflah.tracki_master.Data.ProductSource;
import com.example.aflah.tracki_master.Model.Product;

import java.util.List;

public class HasilSearchProductPresenter implements HasilSearchProductContract.presenter{
    private ProductRepository productRepository;
    private HasilSearchProductContract.view view;

    public HasilSearchProductPresenter(ProductRepository productRepository, HasilSearchProductContract.view view){
        this.productRepository = productRepository;
        this.view = view;
    }

    @Override
    public void getListProduct(String keyword) {
        productRepository.getSearchList(keyword, new ProductSource.GetProductsSearchList() {
            @Override
            public void onSuccess(List<Product> listProduct) {
                view.showDataList(listProduct);
            }

            @Override
            public void onFailure(String errMsg) {
                view.showFailureMessage(errMsg);
            }
        });
    }
}

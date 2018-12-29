package com.example.aflah.tracki_master.Presenter;

import com.example.aflah.tracki_master.Contract.MakananContract;
import com.example.aflah.tracki_master.Data.ProductRepository;
import com.example.aflah.tracki_master.Data.ProductSource;
import com.example.aflah.tracki_master.Model.Product;

import java.util.List;

public class MakananPresenter implements MakananContract.presenter {

    private ProductRepository productRepository;
    private MakananContract.view view;

    public MakananPresenter(ProductRepository productRepository, MakananContract.view view) {
        this.productRepository = productRepository;
        this.view = view;
    }

    @Override
    public void getAllMakanan(int idToko) {
        productRepository.getMakanan(idToko, new ProductSource.GetMakananCallback() {
            @Override
            public void onSuccess(List<Product> listMakanan) {
                if (listMakanan.size() != 0)
                    view.showDataList(listMakanan);
                else view.showIfNoMakanan();
            }

            @Override
            public void onFailure(String errMsg) {
                view.showFailure(errMsg);
            }
        });
    }
}

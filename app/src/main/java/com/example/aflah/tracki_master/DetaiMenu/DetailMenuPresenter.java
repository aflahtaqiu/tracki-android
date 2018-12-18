package com.example.aflah.tracki_master.DetaiMenu;

import android.util.Log;

import com.example.aflah.tracki_master.Data.ProductRepository;
import com.example.aflah.tracki_master.Data.ProductSource;
import com.example.aflah.tracki_master.Model.Product;
import com.example.aflah.tracki_master.Model.ResponseProductById;

public class DetailMenuPresenter implements DetailMenuContract.presenter {

    private ProductRepository productRepository;
    private DetailMenuContract.view view;

    public DetailMenuPresenter(ProductRepository productRepository, DetailMenuContract.view view) {
        this.productRepository = productRepository;
        this.view = view;
    }

    @Override
    public void getDetailProduct(int idProduct) {

        productRepository.getProductById(idProduct, new ProductSource.GetProductByIdCallBack() {
            @Override
            public void onSuccess(ResponseProductById product) {
                view.hideProgress();
                view.showData(product);
                Log.e("testedbro","masuk presenter" + product.toString());
                Log.e("testedbro","id produk" + idProduct);
            }

            @Override
            public void onFailure(String errorMessage) {
                view.hideProgress();
                view.showError(errorMessage);
            }
        });
    }
}

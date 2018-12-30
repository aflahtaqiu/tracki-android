package com.example.aflah.tracki_master.Contract;


import com.example.aflah.tracki_master.Model.Product;

import java.util.List;

public interface HasilSearchProductContract {
    interface presenter {
        void getListProduct(String keyword);
    }
    interface view{
        void showDataList(List<Product> listProduct);
        void showFailureMessage(String errorMessage);
    }
}

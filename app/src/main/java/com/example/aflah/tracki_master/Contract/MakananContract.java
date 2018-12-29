package com.example.aflah.tracki_master.Contract;

import com.example.aflah.tracki_master.Model.Product;

import java.util.List;

public interface MakananContract {

    interface presenter {
        void getAllMakanan(int idToko);
    }

    interface view {
        void showDataList(List<Product> listMakanan);
        void showIfNoMakanan();
        void showFailure(String errMsg);
    }
}

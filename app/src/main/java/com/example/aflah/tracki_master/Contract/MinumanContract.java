package com.example.aflah.tracki_master.Contract;

import com.example.aflah.tracki_master.Model.Product;

import java.util.List;

public interface MinumanContract {

    interface presenter {
        void getAllMinuman(int idToko);
    }

    interface view {
        void showDataList(List<Product> listMinuman);
        void showIfNoMinuman();
        void showFailure(String errMsg);
    }
}

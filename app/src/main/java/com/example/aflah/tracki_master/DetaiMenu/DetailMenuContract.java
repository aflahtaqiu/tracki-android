package com.example.aflah.tracki_master.DetaiMenu;

import com.example.aflah.tracki_master.Model.Product;
import com.example.aflah.tracki_master.Model.ResponseProductById;

public interface DetailMenuContract {

     interface presenter{
         void getDetailProduct(int idProduct);
     }

     interface view{
         void hideProgress();
         void showData(ResponseProductById product);
         void showError(String errMsg);
     }
}

package com.example.aflah.tracki_master.Contract;

import com.example.aflah.tracki_master.Model.Response.ResponseProductById;

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

package com.example.aflah.tracki_master.DetailToko;

import java.util.List;

public interface TokoContract {

    interface view {
        void showDataList(List<String> urlList);
        void showFailureMessage(String errMsg);
        void showViewPager();
    }

    interface presenter {
        void getImagesUrl(int idToko);
    }
}

package com.example.aflah.tracki_master.DetailPromo;

import com.example.aflah.tracki_master.Model.Promotion;

public interface DetailPromoContract {

    interface view {
        void showProgress();
        void hideProgress();
        void showDialogSuccess();
        void hideDialogSuccess();
        void showDialogQRCode();
        void showData(Promotion promotion);
        void showError(String errorMsg);
        void showIsPromoUsed();
        void showIsPromoSaved();
    }

    interface presenter {
        void getDetailPromo(String userToken, int idPromo);
        void sendRedeemData(String userToken, int idPromo);
        void generateQRCode();
    }
}

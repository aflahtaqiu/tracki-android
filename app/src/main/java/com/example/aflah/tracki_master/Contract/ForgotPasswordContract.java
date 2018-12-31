package com.example.aflah.tracki_master.Contract;

public interface ForgotPasswordContract {

    interface presenter {
        void sendEmail(String email);
    }

    interface view {
        void showProgress();
        void hideProgress();
        void showSuccess(String pesan);
        void showFail(String pesan);
        void showError(String errMsg);
    }
}

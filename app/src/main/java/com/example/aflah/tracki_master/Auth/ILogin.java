package com.example.aflah.tracki_master.Auth;

public interface ILogin {

    public void loginEmailPassword(String email, String password);

    public void loginSebagaiTamu();

    public boolean cekValidasi();

    public boolean cekEmailPattern();
}

package com.example.aflah.tracki_master;

public interface IRegister {

    public boolean cekValidasi();

    public boolean cekEmailPattern();

    public void signupUserEmail(String email, String password);
}

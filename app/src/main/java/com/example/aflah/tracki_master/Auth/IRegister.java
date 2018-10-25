package com.example.aflah.tracki_master.Auth;

import java.util.Date;

public interface IRegister {

    public boolean cekValidasi();

    public boolean cekEmailPattern();

    public void signupUserEmail(String name, String email, Date dateOfBirth, String password);
}

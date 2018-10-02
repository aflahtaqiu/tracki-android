package com.example.aflah.tracki_master.Model;

public class Makanan {

    private String namaMakanan;
    private String hargaMakanan;
    private int fotoMakanan;

    public Makanan(String namaMakanan, String hargaMakanan, int fotoMakanan) {
        this.namaMakanan = namaMakanan;
        this.hargaMakanan = hargaMakanan;
        this.fotoMakanan = fotoMakanan;
    }

    public String getNamaMakanan() {
        return namaMakanan;
    }

    public void setNamaMakanan(String namaMakanan) {
        this.namaMakanan = namaMakanan;
    }

    public String getHargaMakanan() {
        return hargaMakanan;
    }

    public void setHargaMakanan(String hargaMakanan) {
        this.hargaMakanan = hargaMakanan;
    }

    public int getFotoMakanan() {
        return fotoMakanan;
    }

    public void setFotoMakanan(int fotoMakanan) {
        this.fotoMakanan = fotoMakanan;
    }
}

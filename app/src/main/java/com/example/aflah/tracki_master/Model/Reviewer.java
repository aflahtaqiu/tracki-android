package com.example.aflah.tracki_master.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reviewer {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("date_of_birth")
    @Expose
    private String date_of_birth;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("provider")
    @Expose
    private String provider;
    @SerializedName("provider_id")
    @Expose
    private String provider_id;
    @SerializedName("pivot")
    @Expose
    private PivotReview pivot;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public PivotReview getPivot() {
        return pivot;
    }

    public void setPivot(PivotReview pivot) {
        this.pivot = pivot;
    }
}

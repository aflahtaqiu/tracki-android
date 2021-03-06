package com.example.aflah.tracki_master.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Promotion {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("banner")
    @Expose
    private String banner;
    @SerializedName("terms_and_policies")
    @Expose
    private String terms_and_policies;
    @SerializedName("expired_date")
    @Expose
    private String expired_date;
    @SerializedName("pivot")
    @Expose
    private PivotPromo pivot;
    @SerializedName("stores")
    @Expose
    private List<Store> stores;
    @SerializedName("saved")
    @Expose
    private Boolean saved;
    @SerializedName("used")
    @Expose
    private Boolean used;

    private Store store;

    public Boolean getSaved() {
        return saved;
    }

    public void setSaved(Boolean saved) {
        this.saved = saved;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }


    public PivotPromo getPivot() {
        return pivot;
    }

    public void setPivot(PivotPromo pivot) {
        this.pivot = pivot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getTerms_and_policies() {
        return terms_and_policies;
    }

    public void setTerms_and_policies(String terms_and_policies) {
        this.terms_and_policies = terms_and_policies;
    }

    public String getExpired_date() {
        return expired_date;
    }

    public void setExpired_date(String expired_date) {
        this.expired_date = expired_date;
    }
}

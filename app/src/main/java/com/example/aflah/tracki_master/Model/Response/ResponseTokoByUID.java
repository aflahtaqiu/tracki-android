package com.example.aflah.tracki_master.Model.Response;

import com.example.aflah.tracki_master.Model.Store;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseTokoByUID {

    @SerializedName("stores")
    @Expose
    private List<Store> stores;

    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }
}

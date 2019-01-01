package com.example.aflah.tracki_master.Model.Response;


import com.example.aflah.tracki_master.Model.SearchName;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseSearchNameStore {
    @SerializedName("stores")
    @Expose

    private List<SearchName> searchNames;

    public List<SearchName> getSearchNamesStore() {return searchNames;}

    public void setSearchNames(List<SearchName> searchNames) {
        this.searchNames = searchNames;
    }
}

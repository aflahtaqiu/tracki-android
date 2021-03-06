package com.example.aflah.tracki_master.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Store {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("advertisement_id")
    @Expose
    private int advertisement_id;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("open")
    @Expose
    private String open;
    @SerializedName("close")
    @Expose
    private String close;
    @SerializedName("promo")
    @Expose
    private boolean promo;
    @SerializedName("store_type")
    @Expose
    private StoreType store_type;
    @SerializedName("products")
    @Expose
    private List<Product> products;
    @SerializedName("galleries")
    @Expose
    private List<Galery> galleries;
    @SerializedName("rating")
    @Expose
    private double rating;
    @SerializedName("reviewers")
    @Expose
    private List<Reviewer> reviewers;
    @SerializedName("promotions")
    @Expose
    private List<Promotion> promotions;

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public List<Reviewer> getReviewers() {
        return reviewers;
    }

    public void setReviewers(List<Reviewer> reviewers) {
        this.reviewers = reviewers;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<Galery> getGalleries() {
        return galleries;
    }

    public void setGalleries(List<Galery> galleries) {
        this.galleries = galleries;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAdvertisement_id() {
        return advertisement_id;
    }

    public void setAdvertisement_id(int advertisement_id) {
        this.advertisement_id = advertisement_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public boolean isPromo() {
        return promo;
    }

    public void setPromo(boolean promo) {
        this.promo = promo;
    }

    public StoreType getStore_type() {
        return store_type;
    }

    public void setStore_type(StoreType store_type) {
        this.store_type = store_type;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", advertisement_id=" + advertisement_id +
                ", uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", logo='" + logo + '\'' +
                ", location='" + location + '\'' +
                ", open='" + open + '\'' +
                ", close='" + close + '\'' +
                ", promo=" + promo +
                ", store_type=" + store_type +
                ", products=" + products +
                ", galleries=" + galleries +
                ", rating=" + rating +
                '}';
    }
}

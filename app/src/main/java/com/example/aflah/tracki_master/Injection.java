package com.example.aflah.tracki_master;

import com.example.aflah.tracki_master.Data.DetailPromoRepository;
import com.example.aflah.tracki_master.Data.ProductRepository;
import com.example.aflah.tracki_master.Data.PromotionRepository;
import com.example.aflah.tracki_master.Data.ReviewTokoRepository;
import com.example.aflah.tracki_master.Data.StoreRepository;
import com.example.aflah.tracki_master.Data.UserRepository;
import com.example.aflah.tracki_master.Data.remote.DetailPromoRemoteDataSource;
import com.example.aflah.tracki_master.Data.remote.ProductRemoteDataSource;
import com.example.aflah.tracki_master.Data.remote.PromotionRemoteDataSource;
import com.example.aflah.tracki_master.Data.remote.ReviewRemoteDataSource;
import com.example.aflah.tracki_master.Data.remote.StoreRemoteDataSource;
import com.example.aflah.tracki_master.Data.remote.UserRemoteDataSource;

public class Injection {

    public static ProductRepository provideProductRepository(){
        return new ProductRepository(new ProductRemoteDataSource());
    }

    public static PromotionRepository providePromotionRepository(){
        return new PromotionRepository(new PromotionRemoteDataSource());
    }

    public static ReviewTokoRepository provideReviewRepository() {
        return new ReviewTokoRepository(new ReviewRemoteDataSource());
    }

    public static DetailPromoRepository provideDetailPromoRepository() {
        return new DetailPromoRepository(new DetailPromoRemoteDataSource());
    }

    public static StoreRepository provideStoreRepository() {
        return new StoreRepository(new StoreRemoteDataSource());
    }

    public static UserRepository provideUserRepository() {
        return new UserRepository(new UserRemoteDataSource());
    }
}

package com.example.aflah.tracki_master;

import com.example.aflah.tracki_master.Data.ProductRepository;
import com.example.aflah.tracki_master.Data.PromotionRepository;
import com.example.aflah.tracki_master.Data.remote.ProductRemoteDataSource;
import com.example.aflah.tracki_master.Data.remote.PromotionRemoteDataSource;

public class Injection {

    public static ProductRepository provideProductRepository(){
        return new ProductRepository(new ProductRemoteDataSource());
    }

    public static PromotionRepository providePromotionRepository(){
        return new PromotionRepository(new PromotionRemoteDataSource());
    }
}

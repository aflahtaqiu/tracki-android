package com.example.aflah.tracki_master;

import com.example.aflah.tracki_master.Data.ProductRepository;
import com.example.aflah.tracki_master.Data.remote.ProductRemoteDataSource;

public class Injection {

    public static ProductRepository provideProductRepository(){
        return new ProductRepository(new ProductRemoteDataSource());
    }
}

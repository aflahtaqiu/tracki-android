package com.example.aflah.tracki_master.Retrofit;

import com.example.aflah.tracki_master.Model.Advertisements;
import com.example.aflah.tracki_master.Model.Response.ResponseDeletePromo;
import com.example.aflah.tracki_master.Model.Response.ResponseLogin;
import com.example.aflah.tracki_master.Model.Response.ResponseLogout;
import com.example.aflah.tracki_master.Model.Response.ResponseRegister;
import com.example.aflah.tracki_master.Model.Response.ResponseSearchNameProduct;
import com.example.aflah.tracki_master.Model.Response.ResponseSearchNameStore;
import com.example.aflah.tracki_master.Model.Response.ResponseSearchProduct;
import com.example.aflah.tracki_master.Model.Response.ResponseUserById;
import com.example.aflah.tracki_master.Model.Response.ResponseTokoByUID;

import java.util.Date;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRequest {

    @GET("store/uid/{numberUID}")
    Call<ResponseTokoByUID> getStoreByUID(
        @Path("numberUID") int numberUID
    );

    @GET("user/{id}")
    Call<ResponseUserById> getSavedPromo(
            @Path("id") int id
    );

    @GET("search-product")
    Call<ResponseSearchProduct> getProductList(@Query("name") String name);

    @GET("search-store")
    Call<ResponseTokoByUID> getStore(@Query("name") String name);

    @GET("advertisement")
    Call<Advertisements> getAdvertisements();

    @DELETE("redeem/{id}")
    Call<ResponseDeletePromo> deletePromo(
            @Header("Authorization") String token,
            @Path("id") int id
    );

    @GET("all-stores")
    Call<ResponseSearchNameStore> getSearchNamesStore();

    @GET("all-products")
    Call<ResponseSearchNameProduct> getSearchNamesProduct();

    @FormUrlEncoded
    @POST("user/register")
    Call<ResponseRegister> sendRegister(
            @Field("name") String name,
            @Field("email") String email,
            @Field("date_of_birth") Date dateOfBirth,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation
    );

    @POST("user/logout")
    Call<ResponseLogout> sendLogout(
    );

    @FormUrlEncoded
    @POST("user/login")
    Call<ResponseLogin> sendLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @Multipart
    @POST("user/photo/{id}")
    Call<ResponseUserById> updateProfilPicture(
            @Path("id") int idUser,
            @Header("Authorization") String token,
            @Part MultipartBody.Part file,
            @Part("_method") RequestBody reqMethod
    );
}

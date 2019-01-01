package com.example.aflah.tracki_master.Data.remote.API;

import com.example.aflah.tracki_master.Model.Response.ResponseAddReview;
import com.example.aflah.tracki_master.Model.Response.ResponseDeletePromo;
import com.example.aflah.tracki_master.Model.Response.ResponseDetailToko;
import com.example.aflah.tracki_master.Model.Response.ResponseForgotPassword;
import com.example.aflah.tracki_master.Model.Response.ResponseLogin;
import com.example.aflah.tracki_master.Model.Response.ResponseLogout;
import com.example.aflah.tracki_master.Model.Response.ResponsePromotionById;
import com.example.aflah.tracki_master.Model.Response.ResponseRedeemPromotion;
import com.example.aflah.tracki_master.Model.Response.ResponseRegister;
import com.example.aflah.tracki_master.Model.Response.ResponseSearchNameProduct;
import com.example.aflah.tracki_master.Model.Response.ResponseSearchNameStore;
import com.example.aflah.tracki_master.Model.Response.ResponseSearchProduct;
import com.example.aflah.tracki_master.Model.Response.ResponseTokoByUID;
import com.example.aflah.tracki_master.Model.Response.ResponseUserById;
import com.example.aflah.tracki_master.Model.Response.ResponseProductById;

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
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("search-store")
    Call<ResponseTokoByUID> getStore(@Query("name") String name);

    @GET("search-product")
    Call<ResponseSearchProduct> getProductList(@Query("name") String name);

    @GET("product/{id}")
    Call<ResponseProductById> getProductById(
            @Path("id") int id
    );

    @GET("promotion")
    Call<ResponseRedeemPromotion> getPromotions();

    @GET("store/{id}")
    Call<ResponseDetailToko> getStoreByID(
            @Path("id") int id
    );

    @FormUrlEncoded
    @POST("review")
    Call<ResponseAddReview> addReview(
            @Header("Authorization") String token,
            @Field("store_id") int store_id,
            @Field("rating") double rating,
            @Field("description") String description
    );

    @GET("promotion/{id}")
    Call<ResponsePromotionById> getPromotionById(
            @Header("Authorization") String token,
            @Path("id") int id
    );

    @FormUrlEncoded
    @POST("redeem")
    Call<ResponseRedeemPromotion> sendRedeemData (
            @Header("Authorization") String token,
            @Field("promotion_id") int promotion_id
    );

    @FormUrlEncoded
    @PATCH("user/{id}")
    Call<ResponseUserById> updateProfile(
            @Path("id") int idUser,
            @Header("Authorization") String token,
            @Field("name") String name,
            @Field("date_of_birth") Date dateOfBirth
    );

    @GET("user/{id}")
    Call<ResponseUserById> getUserById(
            @Path("id") int id
    );

    @FormUrlEncoded
    @POST("password-reset/create")
    Call<ResponseForgotPassword> changePassword(
            @Field("email") String email
    );


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


    @GET("store/uid/{numberUID}")
    Call<ResponseTokoByUID> getStoreByUID(
            @Path("numberUID") int numberUID
    );

    @GET("user/{id}")
    Call<ResponseUserById> getSavedPromo(
            @Path("id") int id
    );

    @DELETE("redeem/{id}")
    Call<ResponseDeletePromo> deletePromo(
            @Header("Authorization") String token,
            @Path("id") int id
    );

    @GET("all-stores")
    Call<ResponseSearchNameStore> getSearchNamesStore();

    @GET("all-products")
    Call<ResponseSearchNameProduct> getSearchNamesProduct();

    @Multipart
    @POST("user/photo/{id}")
    Call<ResponseUserById> updateProfilPicture(
            @Path("id") int idUser,
            @Header("Authorization") String token,
            @Part MultipartBody.Part file,
            @Part("_method") RequestBody reqMethod
    );
}

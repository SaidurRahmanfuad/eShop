package com.saidur.eshop.network;
import retrofit2.Call;
import retrofit2.http.GET;


public interface MainApi {
    @GET("api/ecom/v1/banner/get-banners")
    Call<BannerResponse> GetBanner();

}

package com.saidur.eshop.network;
import com.saidur.eshop.request.ORequest;
import com.saidur.eshop.request.Obody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface MainApi {
    @GET("api/ecom/v1/banner/get-banners")
    Call<BannerResponse> GetBanner();

    @GET("api/v1/category/get-categorys")
    Call<CategoryResponse> GetCategory();

    @GET("api/ecom/v1/product/get-products")
    Call<ProductResponse> GetProducts();

    @POST("api/ecom/v1/order/create")
    Call<GResponse> Submit_order(@Body ORequest req);
}

package com.saidur.eshop.presentar;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.saidur.eshop.interfac.IHome;
import com.saidur.eshop.network.BannerResponse;
import com.saidur.eshop.network.CategoryResponse;
import com.saidur.eshop.network.MainApi;
import com.saidur.eshop.network.ProductResponse;
import com.saidur.eshop.network.RetrofitClientInstance;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterHome implements IHome.Presenter {
    IHome.view view;
    Context context;

    public PresenterHome(IHome.view view, Context context) {
        this.view = view;
        this.context = context;
    }


    @Override
    public void getBanner() {
        try {
            MainApi service = RetrofitClientInstance.getRetrofitInstance().create(MainApi.class);
            Call<BannerResponse> call = service.GetBanner();
            call.enqueue(new Callback<BannerResponse>() {
                @Override
                public void onResponse(@NonNull Call<BannerResponse> call, @NonNull Response<BannerResponse> response) {
                 if(response.isSuccessful())
                 {
                     view.onViewBanner(response.body().getResult());
                 }
                }

                @Override
                public void onFailure(@NonNull Call<BannerResponse> call, @NonNull Throwable t) {
                    Log.e("TAG", "getBanner: ",t.getCause() );
                }
            });
        } catch (Exception exception) {
            Log.e("TAG", "getBanner: ",exception.getCause() );
        }
    }

    @Override
    public void getCategory() {
        try {
            MainApi service = RetrofitClientInstance.getRetrofitInstance().create(MainApi.class);
            Call<CategoryResponse> call = service.GetCategory();
            call.enqueue(new Callback<CategoryResponse>() {
                @Override
                public void onResponse(@NonNull Call<CategoryResponse> call, @NonNull Response<CategoryResponse> response) {
                    if(response.isSuccessful())
                    {
                        view.onViewCategory(response.body().getResult());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<CategoryResponse> call, @NonNull Throwable t) {
                    Log.e("TAG", "getBanner: ",t.getCause() );
                }
            });
        } catch (Exception exception) {
            Log.e("TAG", "getBanner: ",exception.getCause() );
        }
    }

    @Override
    public void getProductList() {
        try {
            MainApi service = RetrofitClientInstance.getRetrofitInstance().create(MainApi.class);
            Call<ProductResponse> call = service.GetProducts();
            call.enqueue(new Callback<ProductResponse>() {
                @Override
                public void onResponse(@NonNull Call<ProductResponse> call, @NonNull Response<ProductResponse> response) {
                    if(response.isSuccessful())
                    {
                        view.onViewProductList(response.body().getResult());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ProductResponse> call, @NonNull Throwable t) {
                    Log.e("TAG", "getProduct: ",t.getCause() );
                }
            });
        } catch (Exception exception) {
            Log.e("TAG", "getProduct: ",exception.getCause() );
        }
    }
}

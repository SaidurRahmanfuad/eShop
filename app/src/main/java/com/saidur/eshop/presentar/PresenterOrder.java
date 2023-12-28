package com.saidur.eshop.presentar;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.saidur.eshop.interfac.IHome;
import com.saidur.eshop.interfac.IOrder;
import com.saidur.eshop.network.BannerResponse;
import com.saidur.eshop.network.CategoryResponse;
import com.saidur.eshop.network.GResponse;
import com.saidur.eshop.network.MainApi;
import com.saidur.eshop.network.ProductResponse;
import com.saidur.eshop.network.RetrofitClientInstance;
import com.saidur.eshop.request.ORequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterOrder implements IOrder.Presenter {
    IOrder.view view;
    Context context;

    public PresenterOrder(IOrder.view view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void orderSubmit(ORequest req) {
        try {
            Gson gson=new Gson();
            String data=gson.toJson(req);
            System.out.println(data);
            MainApi service = RetrofitClientInstance.getRetrofitInstance().create(MainApi.class);
            Call<GResponse> call = service.Submit_order(req);
            call.enqueue(new Callback<GResponse>() {
                @Override
                public void onResponse(@NonNull Call<GResponse> call, @NonNull Response<GResponse> response) {
                    if(response.body().getResult()>0)
                    {
                        view.onPlaceOrderStatus(response.body().getMessage());
                    }else {
                        view.onPlaceOrderStatus(response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<GResponse> call, @NonNull Throwable t) {
                    Log.e("TAG", "getProduct: ",t.getCause() );
                }
            });
        } catch (Exception exception) {
            Log.e("TAG", "getProduct: ",exception.getCause() );
        }
    }
}

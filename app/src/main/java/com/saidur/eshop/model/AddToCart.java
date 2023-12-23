package com.saidur.eshop.model;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.graphics.drawable.DrawableCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.saidur.eshop.BaseActivity;
import com.saidur.eshop.MainActivity;
import com.saidur.eshop.R;
import com.saidur.eshop.db.MainDB;
import com.saidur.eshop.utils.Consts;
import com.saidur.eshop.utils.SnackBarManagement;
import com.saidur.eshop.view.FragmentCart;

public class AddToCart {

    private final Activity activity;
    MainDB maindb;
    ImageView tvAddToCart;
    private ModelProduct product;
    public AddToCart(Activity activity) {
        this.activity = activity;
        this.maindb = new MainDB(activity);
        //this.toast = new CustomToast(activity);
    }

    public void addToCart(final ImageView tvAddToCart, String data) {

        this.tvAddToCart = tvAddToCart;
        Drawable unwrappedDrawable = tvAddToCart.getBackground();
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, (Color.parseColor(((BaseActivity) activity).getPreferences().getString(Consts.SECOND_COLOR, Consts.SECONDARY_COLOR))));

        this.product=new Gson().fromJson(data,ModelProduct.class);
        tvAddToCart.setOnClickListener(v -> {
            tvAddToCart.setImageResource(R.drawable.ic_check);
            ModelCart cart = new ModelCart();
            cart.setQuantity(1);
            cart.setProduct(new Gson().toJson(product));
            cart.setProductid(product.getId() + "");
            cart.setBuyNow(0);
            if (maindb.getProductFromCartById(product.getId() + "") != null) {
                maindb.addToCart(cart);
              /*  Intent intent = new Intent(activity, CartActivity.class);
                intent.putExtra("buynow", 0);
                activity.startActivity(intent);*/


                FragmentCart cartz=new FragmentCart();
                ((BaseActivity) activity).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, cartz)
                        .addToBackStack(MainActivity.class.getSimpleName())
                        .commit();

                Bundle bn=new Bundle();
                bn.putInt("buynow",0);
                cartz.setArguments(bn);

            } else {
                maindb.addToCart(cart);
                ((BaseActivity) activity).showCart();
                ((BaseActivity) activity).showCartAnimation();
                ((BaseActivity) activity).vibrateononadd();

                Toast.makeText(activity, "Item added to cart", Toast.LENGTH_SHORT).show();
            }
        });


    }
}

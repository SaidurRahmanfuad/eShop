package com.saidur.eshop.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.saidur.eshop.BaseActivity;
import com.saidur.eshop.R;
import com.saidur.eshop.customlistener.OnItemClickListener;
import com.saidur.eshop.db.MainDB;
import com.saidur.eshop.model.ModelCart;
import com.saidur.eshop.model.ModelProduct;
import com.saidur.eshop.utils.Consts;
import com.saidur.eshop.utils.RequestParamUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<ModelCart> list = new ArrayList<>();
    private final Activity activity;
    private final OnItemClickListener onItemClickListener;
    private int width = 0, height = 0;
    //private final ViewBinderHelper binderHelper = new ViewBinderHelper();
    private final MainDB databaseHelper;
    String value;
    private int isBuyNow = 0;

    public CartAdapter(Activity activity, OnItemClickListener onItemClickListener) {
        this.activity = activity;
        this.onItemClickListener = onItemClickListener;
        databaseHelper = new MainDB(activity);
       // binderHelper.setOpenOnlyOne(true);
    }

    public void addAll(List<ModelCart> list) {
        this.list = list;
        getWidthAndHeight();
        notifyDataSetChanged();
    }

    public void isFromBuyNow(int isBuyNow) {
        this.isBuyNow = isBuyNow;
    }

    public List<ModelCart> getList() {
        return list;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv_cart, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        if (list != null && 0 <= position && position < list.size()) {
            ModelCart data=list.get(position);
            String jproduct = list.get(position).getProduct();
           /* List<ModelProduct> productList = new Gson().fromJson(product, new TypeToken<ModelProduct>() {
            }.getType());*/
            ModelProduct product=new Gson().fromJson(jproduct,ModelProduct.class);

            Drawable tvIncrement = holder.tvIncrement.getBackground();
            Drawable rappedDrawable = DrawableCompat.wrap(tvIncrement);
            DrawableCompat.setTint(rappedDrawable, Color.parseColor(((BaseActivity) activity).getPreferences().getString(Consts.SECOND_COLOR, Consts.SECONDARY_COLOR)));

            Drawable tvDecrement = holder.tvDecrement.getBackground();
            Drawable rappedDrawables = DrawableCompat.wrap(tvDecrement);
            DrawableCompat.setTint(rappedDrawables, Color.parseColor(((BaseActivity) activity).getPreferences().getString(Consts.SECOND_COLOR, Consts.SECONDARY_COLOR)));

            holder.ivDeleteRight.setColorFilter(activity.getResources().getColor(R.color.color_main));

            // Use ViewBindHelper to restore and save the open/close state of the SwipeRevealView
            // put an unique string id as value, can be any string which uniquely define the data
            holder.tvPrice.setTextColor(Color.parseColor(((BaseActivity) activity).getPreferences().getString(Consts.SECOND_COLOR, Consts.SECONDARY_COLOR)));
            //holder.llDeleteBackground.setBackgroundColor(Color.parseColor(((BaseActivity) activity).getPreferences().getString(Constant.SECOND_COLOR, Constant.SECONDARY_COLOR)));
            holder.tvDeleteCartBG.setTextColor(activity.getResources().getColor(R.color.color_main));
           /* if (!productList.get(position).getReview().equals("")) {
                holder.ratingBar.setRating(Float.parseFloat(data.getProduct().));
            } else {
                holder.ratingBar.setRating(0);
            }*/
            if(product.getRatings()>0)
            {
                holder.ratingBar.setRating(Float.parseFloat(String.valueOf(product.getRatings())));
            }else {
                holder.ratingBar.setRating(0);
            }

            if(product.getPictures().size()>0)
            {
                String image = product.getPictures().get(0).getUrl();
                Glide.with(activity.getBaseContext())
                        .load(image)
                        .error(R.drawable.no_image_available)
                        .into(holder.ivImage);
            }else {
                holder.ivImage.setImageResource(R.drawable.placeholder);
            }

            holder.tvName.setText(product.getName());
            holder.tvPrice.setText(String.valueOf(product.getSale_price()));
            holder.tvPrice.setTextSize(15);
            holder.tvQuantity.setText(String.valueOf(data.getQuantity()));
            holder.tvIncrement.setOnClickListener(v -> {
                int quantity = Integer.parseInt(holder.tvQuantity.getText().toString());
                quantity = quantity + 1;

                holder.tvQuantity.setText(String.valueOf(quantity));
                databaseHelper.updateQuantity(quantity, data.getProductid());
                list.get(position).setQuantity(quantity);
                onItemClickListener.onItemClick(position, RequestParamUtils.increment, quantity);
            });
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class CartViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImage, tvDecrement, tvIncrement, ivDeleteRight;
        LinearLayout llDelete;
        public RelativeLayout llMain, llDeleteBackground;
        TextView tvQuantity,tvName,tvPrice,tvDeleteCartBG;
        RatingBar ratingBar;


        public CartViewHolder(View view) {
            super(view);
            ivDeleteRight = view.findViewById(R.id.ivDeleteRight);
            tvDeleteCartBG = view.findViewById(R.id.tvDeleteCartBG);
            ivImage = view.findViewById(R.id.ivImage);
            tvDecrement = view.findViewById(R.id.tvDecrement);
            tvIncrement = view.findViewById(R.id.tvIncrement);
            llDelete = view.findViewById(R.id.ll_Delete);
            llMain = view.findViewById(R.id.llMain);
            tvQuantity = view.findViewById(R.id.tvQuantity);
            tvPrice = view.findViewById(R.id.tvPrice);
            tvName = view.findViewById(R.id.tvName);
            ratingBar = view.findViewById(R.id.ratingBar);
            llDeleteBackground = view.findViewById(R.id.llDeleteBackground);
        }
    }
    public void getWidthAndHeight() {
        int height_value = 90;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels / 2 - 20;
        height = width - height_value;
    }
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
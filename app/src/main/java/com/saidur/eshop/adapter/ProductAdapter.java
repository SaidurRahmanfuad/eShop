package com.saidur.eshop.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.saidur.eshop.BaseActivity;
import com.saidur.eshop.R;
import com.saidur.eshop.customlistener.OnDtlsListenerProduct;
import com.saidur.eshop.model.ModelImage;
import com.saidur.eshop.model.ModelProduct;
import com.saidur.eshop.model.ModelProductDtls;
import com.saidur.eshop.utils.Consts;
import com.saidur.eshop.utils.RequestParamUtils;
import com.saidur.eshop.view.ActivityProductDetail;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> implements OnDtlsListenerProduct {

    public static final String TAG = "ChangeLanguageItemAdapter";
    private final LayoutInflater inflater;
    List<ModelProduct> list;
    private final Activity activity;

    private int width = 0, height = 0;

    ImageView imageView;
    OnDtlsListenerProduct listener;

    public ProductAdapter(Activity activity, List<ModelProduct> list) {
        inflater = LayoutInflater.from(activity);
        this.activity = activity;
        this.list = list;
        getWidthAndHeight();
    }

   /* public void addAll(List<ModelProduct> list) {
        this.list = list;
        getWidthAndHeight();
        notifyDataSetChanged();
    }*/

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_rv_product, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ModelProduct product = list.get(position);
        holder.llMain.getLayoutParams().height = height;
        ((BaseActivity) activity).showDiscount(holder.tvDiscount, String.valueOf(product.getSale_price()), String.valueOf(product.getPrice()));
          List<ModelImage> imgList=product.getPictures();
        if (imgList.size()==0) {
            holder.ivImage.setImageResource(R.drawable.no_image_available);
        }else {
            String image = product.getPictures().get(0).getUrl();
            Glide.with(activity.getBaseContext())
                    .load(image)
                    .error(R.drawable.no_image_available)
                    .into(holder.ivImage);
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            holder.tvName.setText(Html.fromHtml(product.getName() + "", Html.FROM_HTML_MODE_LEGACY));
        } else {
            holder.tvName.setText(Html.fromHtml(product.getName() + ""));
        }
        holder.tvPrice.setTextSize(15);
        ((BaseActivity) activity).setPrice(holder.tvPrice, String.valueOf(product.getPrice()));
        if (String.valueOf(product.getRatings()) != null && product.getRatings() > 0) {
            holder.ratingBar.setRating(Float.parseFloat(String.valueOf(product.getRatings())));
        } else {
            holder.ratingBar.setRating(0);
        }
        // holder.main.setOnClickListener(view -> getProductDetail(String.valueOf(list.get(position).id)));
    }

    public void getWidthAndHeight() {
        int height_value = 90;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels / 2 - 10;
        height = width;
    }


    @Override
    public int getItemCount() {
        /*   return Math.min(list.size(), 8);*/
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public void onProductDetails(ModelProductDtls dtls) {

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llMain, main, ll_content;
        ImageView ivAddToCart, ivImage;
        TextView tvPrice, tvName, tvPrice1, tvDiscount;
        RatingBar ratingBar;
        //SparkButton ivWishList;

        public MyViewHolder(View view) {
            super(view);
            llMain = view.findViewById(R.id.llMain);
            ll_content = view.findViewById(R.id.ll_content);
            ivAddToCart = view.findViewById(R.id.ivAddToCart);
            ivImage = view.findViewById(R.id.ivImage);
            tvPrice = view.findViewById(R.id.tvPrice);
            tvName = view.findViewById(R.id.tvName);
            tvPrice1 = view.findViewById(R.id.tvPrice1);
            tvDiscount = view.findViewById(R.id.tvDiscount);
            ratingBar = view.findViewById(R.id.ratingBar);
            //ivWishList = view.findViewById(R.id.ivWishList);
        }
    }


}

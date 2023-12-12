package com.saidur.eshop.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.saidur.eshop.R;


public class CustomPD {

    public static CustomPD customPD;
    public Context context;
    public Dialog dialogView;
    ImageView iv1;
    AnimationDrawable Anim;

    public CustomPD(Context context) {
        this.context = context;
    }

    public static CustomPD getCustomPD(Context context) {
        if (customPD == null) {
            customPD = new CustomPD(context);
        }
        return customPD;
    }



    public void showCustomDialog(Activity context) {
        dialogView = new Dialog(context);
        dialogView.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogView.setContentView(R.layout.lay_pd);

        iv1 = dialogView.findViewById(R.id.iv1);
       // iv1 = dialogView.findViewById(R.id.iv1);
        if (!context.isDestroyed()) {
//            GlideDrawableImageViewTarget ivSmily = new GlideDrawableImageViewTarget(iv1);
            Glide.with(context.getBaseContext()).load(R.raw.shopping).into(iv1);



        }

        dialogView.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogView.show();
    }

    public Boolean isShowing() {

        try {
            if (dialogView.isShowing()) {
                return true;
            }
        } catch (Exception e) {
            Log.e("Exception is ", e.getMessage());
            return false;
        }
        return false;
    }


    //TODO : Dissmiss Dialog
    public void dissmissDialog() {
        try {
            if (dialogView.isShowing() && dialogView != null) {
                dialogView.dismiss();
            }
        } catch (Exception e) {
            Log.e("Exception is ", e.getMessage());
        }
    }
}

package com.saidur.eshop;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.saidur.eshop.db.MainDB;
import com.saidur.eshop.utils.Consts;
import com.saidur.eshop.utils.CustomPD;
import com.saidur.eshop.utils.RequestParamUtils;
import com.saidur.eshop.view.FragmentCart;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

public class BaseActivity extends AppCompatActivity {
    String language;
    MainDB dbMain;
    public ImageView ivBack, ivLogo;
    public SharedPreferences sharedpreferences;
    public CustomPD pd;
    public LinearLayout llHome, llSearchFromBottom, llCart, llAccount, llWishList, llBottomBar, llBottomLine;
    FrameLayout flCart;
    TextView tvToolCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Thread.UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    }
    @Override
    public void attachBaseContext(Context base) {
        Log.e("Attache", "called");
        super.attachBaseContext(updateBaseContextLocale(base));
    }
    public SharedPreferences getPreferences() {
        sharedpreferences = getSharedPreferences(Consts.MyPREFERENCES, Context.MODE_PRIVATE);
        return sharedpreferences;
    }
    //ToDO: check infinite scroll enable from backend or not .
    public boolean isInfiniteScrollEnable() {
        return getPreferences().getBoolean(RequestParamUtils.INFINITESCROLL, false);
    }
    public void ChangeStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

 /*   @SuppressLint("ClickableViewAccessibility")
    private void setWhatsAppButton() {
        ivWhatsappDrag = findViewById(R.id.ivWhatsappDrag);

        if (Consts.WHATSAPPENABLE.equals("enable") && !Consts.WHATSAPP.isEmpty()) {
            Log.e(TAG, "setWhatsAppButton: " + "enable");
            ivWhatsappDrag.setVisibility(View.VISIBLE);
        } else {
            ivWhatsappDrag.setVisibility(View.GONE);
        }

        ivWhatsappDrag.setColorFilter(Color.parseColor(getPreferences().getString(Consts.SECOND_COLOR, Consts.SECONDARY_COLOR)));
        final GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureTap());
        ivWhatsappDrag.setOnTouchListener((view, motionEvent) -> {
            gestureDetector.onTouchEvent(motionEvent);
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            screenHeight = displaymetrics.heightPixels - 230;
            screenWidth = displaymetrics.widthPixels;

            switch (motionEvent.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    dX = view.getX() - motionEvent.getRawX();
                    dY = view.getY() - motionEvent.getRawY();
                    lastAction = MotionEvent.ACTION_DOWN;
                    break;
                case MotionEvent.ACTION_MOVE:
                    float newX = motionEvent.getRawX() + dX;
                    float newY = motionEvent.getRawY() + dY;
                    // check if the view out of screen
                    if ((newX <= 0 || newX >= screenWidth - view.getWidth()) || (newY <= 0 || newY >= screenHeight - view.getHeight())) {
                        lastAction = MotionEvent.ACTION_MOVE;
                        break;
                    }
                    view.setX(newX);
                    view.setY(newY);
                    lastAction = MotionEvent.ACTION_MOVE;
                    view.animate().x(newX).y(newY).setDuration(0).start();
                    break;
                case MotionEvent.ACTION_UP:
                    if (lastAction == MotionEvent.ACTION_DOWN) break;
                default:
                    return false;
            }
            return true;
        });
    }*/

    //Language
    public Context updateBaseContextLocale(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(Consts.MyPREFERENCES, Context.MODE_PRIVATE);
        // String language = sharedPref.getString(RequestParamUtils.LANGUAGE, "en");
        if (sharedPref.getString(RequestParamUtils.LANGUAGE, "").isEmpty()) {
            if (!sharedPref.getString(RequestParamUtils.DEFAULTLANGUAGE, "").isEmpty()) {
                language = sharedPref.getString(RequestParamUtils.DEFAULTLANGUAGE, "");
            } else {
                language = "en";
            }
        } else {
            language = sharedPref.getString(RequestParamUtils.LANGUAGE, "");
        }
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResourcesLocale(context, locale);
        }
        return updateResourcesLocaleLegacy(context, locale);
    }

    @TargetApi(Build.VERSION_CODES.N)
    private Context updateResourcesLocale(Context context, Locale locale) {
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }

    private Context updateResourcesLocaleLegacy(Context context, Locale locale) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return context;
    }
    public String getlanuage() {
        String lng = getPreferences().getString(RequestParamUtils.LANGUAGE, "");
        if (lng != null && !lng.equals("")) {
            return lng;
        } else {
            if (Consts.IS_WPML_ACTIVE) {
                String defaultLng = getPreferences().getString(RequestParamUtils.DEFAULTLANGUAGE, "");
                if (defaultLng != null && !defaultLng.equals("")) {
                    return defaultLng;
                } else {
                    return "";
                }
            } else {
                return "";
            }
        }
    }
    public void setLocaleByLanguageChange(String lang) {
        String languageToLoad; // your language
        if (lang.contains("-")) {
            String[] array = lang.split("-");
            if (array.length > 0) {
                languageToLoad = array[0];
            } else {
                languageToLoad = lang;
            }
        } else {
            languageToLoad = lang;
        }
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        recreate();
        Intent intent = new Intent(BaseActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    //year
    public void setCurrentYear(TextView tvSplashText){
        tvSplashText.setTextColor(Color.parseColor(getPreferences().getString(Consts.SECOND_COLOR, Consts.SECONDARY_COLOR)));
        int year = Calendar.getInstance().get(Calendar.YEAR);
        if(year != 0){
            tvSplashText.setText(getString(R.string.footer).replace("2023",""+year));
        }
    }

    public void clearUser() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(RequestParamUtils.CUSTOMER, "");
        editor.apply();
    }
    //TODO : Show Progress
    public void showProgress(String val) {
        if (pd != null) {
            pd.dissmissDialog();
        }
        pd = new CustomPD(BaseActivity.this);
        if (!isDestroyed()) {
            pd.showCustomDialog(BaseActivity.this);
        }
    }

    //TODO : Dismiss progress
    public void dismissProgress() {
        if (pd != null) {
            pd.dissmissDialog();
        }
    }

    public void showDiscount(TextView tvDiscount, String salePrice, String regularPrice) {
        tvDiscount.setVisibility(View.VISIBLE);
        Drawable unwrappedDrawable = tvDiscount.getBackground();
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, (Color.parseColor(getPreferences().getString(Consts.SECOND_COLOR, Consts.SECONDARY_COLOR))));
        if (salePrice != null && !salePrice.equals("") && !salePrice.equals("0.0")) {
            String discount = getDiscount(regularPrice, salePrice);
            if (!discount.equals("")) {
                String strDiscount = discount + " Off";
                tvDiscount.setText(strDiscount);
            } else {
                tvDiscount.setVisibility(View.GONE);
            }
        } else {
            tvDiscount.setVisibility(View.GONE);
        }
    }

    public String getDiscount(String originalPrice, String salePrice) {
        try {
            Float originalPrices = Float.parseFloat(getPrice(originalPrice));
            Float salePrices = Float.parseFloat(getPrice(salePrice));
            Float priceDiff = originalPrices - salePrices;
            Double discount = (double) (priceDiff / originalPrices * 100);
            return Consts.setDecimalTwo(discount) + "%";
        } catch (Exception e) {
            Log.e("Exception is =", e.getMessage() + "");
            return "";
        }
    }

    public String getPrice(String price) {
        price = price.replace("\\s+", "");
        price = price.replace(Consts.THOUSANDSSEPRETER, "");
        price = price.replace(Consts.CURRENCYSYMBOL, "");
        return price;
    }

    public void setPrice(TextView tvPrice, String price) {
        Log.e(TAG, "setPrice: " + price);
        if (price != null) if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvPrice.setText(Html.fromHtml(price + "", Html.FROM_HTML_MODE_COMPACT));
            tvPrice.setText(Consts.CURRENCYSYMBOL+""+getPrice(price));
        } else {
            tvPrice.setText(Html.fromHtml(price));
        }

    }

    public void setCount() {
        ImageView ivBottomCart = findViewById(R.id.ivBottomCart);
        TextView tvBottomCartCount = findViewById(R.id.tvBottomCartCount);
        TextView tvBottomCart = findViewById(R.id.tv_cart_title);
        if (Consts.Is_what) {
            llCart.setVisibility(View.VISIBLE);
            tvBottomCartCount.setVisibility(View.GONE);
           // ivBottomCart.setImageResource(R.drawable.ic_coupon);
           // tvBottomCart.setText(getResources().getString(R.string.my_reward));
        } else {
            llCart.setVisibility(View.VISIBLE);
            if (new MainDB(this).getFromCart(0).size() > 0) {
                tvBottomCartCount.setText(String.valueOf(new MainDB(this).getFromCart(0).size()));
                tvBottomCartCount.setVisibility(View.VISIBLE);
            } else {
                tvBottomCartCount.setVisibility(View.GONE);
            }

           // ivBottomCart.setImageResource(R.drawable.ic_cart_gray);
           // tvBottomCart.setText(getResources().getString(R.string.cart));
        }
    }


    public void showCart() {
        flCart = findViewById(R.id.flCart);
        tvToolCart = findViewById(R.id.tvToolCart);
        dbMain = new MainDB(this);
        if (tvToolCart != null && flCart != null) {
            Drawable unwrappedDrawable = tvToolCart.getBackground();
            Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
            DrawableCompat.setTint(wrappedDrawable, Color.parseColor(getPreferences().getString(Consts.SECOND_COLOR, Consts.SECONDARY_COLOR)));
            ((ImageView) findViewById(R.id.ivCart)).setColorFilter(Color.parseColor(getPreferences().getString(Consts.SECOND_COLOR, Consts.SECONDARY_COLOR)));

            if (dbMain.getFromCart(0).size() > 0) {
                tvToolCart.setText(String.valueOf(dbMain.getFromCart(0).size()));
                tvToolCart.setVisibility(View.VISIBLE);
                flCart.setVisibility(View.VISIBLE);
            } else {
                tvToolCart.setVisibility(View.GONE);
                flCart.setVisibility(View.GONE);
            }
            flCart.setOnClickListener(view -> {
             /*   Intent intent = new Intent(BaseActivity.this, .class);
                startActivity(intent);*/
                FragmentCart cart=new FragmentCart();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, cart)
                        .addToBackStack(MainActivity.class.getSimpleName())
                        .commit();

            });
        }

        TextView tvBottomCartCount = findViewById(R.id.tvBottomCartCount);
        if (tvBottomCartCount != null) {
            if (new MainDB(this).getFromCart(0).size() > 0) {
                tvBottomCartCount.setText(String.valueOf(new MainDB(this).getFromCart(0).size()));
                tvBottomCartCount.setVisibility(View.VISIBLE);
            } else {
                tvBottomCartCount.setVisibility(View.GONE);
            }
        }

        if (tvToolCart != null && flCart != null) {
            if (Consts.IS_CATALOG_MODE_OPTION) {
                flCart.setVisibility(View.GONE);
                tvToolCart.setVisibility(View.GONE);
                if (tvBottomCartCount != null) tvBottomCartCount.setVisibility(View.GONE);
            } else {
                if (dbMain.getFromCart(0).size() > 0) {
                    tvToolCart.setVisibility(View.VISIBLE);
                    if (tvBottomCartCount != null) tvBottomCartCount.setVisibility(View.VISIBLE);
                } else {
                    tvToolCart.setVisibility(View.GONE);
                    if (tvBottomCartCount != null) tvBottomCartCount.setVisibility(View.GONE);
                }
                flCart.setVisibility(View.VISIBLE);
            }
        }
//        tvToolCart.getBackground().setColorFilter(Color.parseColor(getPreferences().getString(Constant.SECOND_COLOR, Constant.SECONDARY_COLOR)), PorterDuff.Mode.SRC_IN);
    }

    public void showCartAnimation() {


        try {
            FrameLayout flCart = findViewById(R.id.flCart);
            TextView tvToolCart = findViewById(R.id.tvToolCart);
            LinearLayout llmaincart = findViewById(R.id.llmainCart);


            YoYo.with(Techniques.BounceInUp).duration(700).repeat(0).playOn(flCart);
            YoYo.with(Techniques.BounceInUp).duration(700).repeat(0).playOn(llmaincart);


        } catch (Exception e) {
            Log.e(TAG, "showCartAnimation: " + e.getMessage());
        }


    }

    public void pulseAnimation(View view) {
        try {

            YoYo.with(Techniques.Pulse).duration(700).repeat(Animation.INFINITE).playOn(view);


        } catch (Exception e) {
            Log.e(TAG, "showCartAnimation: " + e.getMessage());
        }


    }

    public void vibrateononadd() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.EFFECT_TICK));
        } else {
            vibrator.vibrate(100);
        }

    }

    public void goto_cart(Fragment frag){
        //FragmentCart cart=new FragmentCart();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, frag)
                .addToBackStack(MainActivity.class.getSimpleName())
                .commit();
    };
}

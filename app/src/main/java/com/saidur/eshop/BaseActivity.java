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
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.widget.NestedScrollView;

import com.saidur.eshop.utils.Consts;
import com.saidur.eshop.utils.CustomPD;
import com.saidur.eshop.utils.RequestParamUtils;

import java.util.Calendar;
import java.util.Locale;

public class BaseActivity extends AppCompatActivity {
    String language;
    public ImageView ivBack, ivLogo;
    public ImageView ivWhatsappDrag;
    public SharedPreferences sharedpreferences;
    public CustomPD pd;
    public LinearLayout llHome, llSearchFromBottom, llCart, llAccount, llWishList, llBottomBar, llBottomLine;
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

   /* public void setBottomBar(final String activity, final NestedScrollView view) {
        llHome = findViewById(R.id.llHome);
        llSearchFromBottom = findViewById(R.id.llSearchFromBottom);
        llCart = findViewById(R.id.llCart);
        llAccount = findViewById(R.id.llMyAccount);
        llWishList = findViewById(R.id.llWishList);
        llBottomBar = findViewById(R.id.llBottomBar);
        llBottomLine = findViewById(R.id.llBottmLine);

        views = findViewById(R.id.view);
        final ImageView ivBottomHome = findViewById(R.id.ivBottomHome);
        ImageView ivBottomSearch = findViewById(R.id.ivBottomSearch);
        ImageView ivBottomCart = findViewById(R.id.ivBottomCart);
        ImageView ivBottomAccount = findViewById(R.id.ivBottomAccount);
        ImageView ivBottomWishList = findViewById(R.id.ivBottomWishList);
        ImageView ivCenterBg = findViewById(R.id.ivCenterBg);

        TextView tvBottomSearch = findViewById(R.id.tvBottomSearch);
        TextView tvBottomCart = findViewById(R.id.tvBottomCart);
        TextView tvBottomAccount = findViewById(R.id.tvBottomAccount);
        TextView tvBottomWishList = findViewById(R.id.tvBottomWishList);
        TextView tvBottomCartCount = findViewById(R.id.tvBottomCartCount);

        tvBottomSearch.setText(getResources().getString(R.string.searchs));
        tvBottomCart.setText(getResources().getString(R.string.cart));
        tvBottomAccount.setText(getResources().getString(R.string.account));
        tvBottomWishList.setText(getResources().getString(R.string.my_wish_list));

        if (Config.IS_CATALOG_MODE_OPTION) {
            llCart.setVisibility(View.VISIBLE);
            tvBottomCartCount.setVisibility(View.GONE);
            ivBottomCart.setImageResource(R.drawable.ic_coupon);
            tvBottomCart.setText(getResources().getString(R.string.my_reward));
        } else {
            llCart.setVisibility(View.VISIBLE);
            if (new DatabaseHelper(this).getFromCart(0).size() > 0) {
                tvBottomCartCount.setText(String.valueOf(new DatabaseHelper(this).getFromCart(0).size()));
                tvBottomCartCount.setVisibility(View.VISIBLE);
            } else {
                tvBottomCartCount.setVisibility(View.GONE);
            }
            ivBottomCart.setImageResource(R.drawable.ic_cart_gray);
            tvBottomCart.setText(getResources().getString(R.string.cart));
        }
        views.setBackgroundColor(Color.parseColor(getPreferences().getString(Consts.APP_COLOR, Consts.PRIMARY_COLOR)));
        ivBottomHome.setColorFilter(Color.parseColor(getPreferences().getString(Consts.SECOND_COLOR, Consts.SECONDARY_COLOR)));
        Drawable unwrappedDrawable = ivCenterBg.getBackground();
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, (Color.parseColor((getPreferences().getString(Consts.SECOND_COLOR, Consts.SECONDARY_COLOR)))));

        unwrappedDrawable = ivBottomHome.getBackground();
        wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, (Color.parseColor((getPreferences().getString(Consts.APP_COLOR, Consts.PRIMARY_COLOR)))));

        unwrappedDrawable = tvBottomCartCount.getBackground();
        wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, Color.parseColor(getPreferences().getString(Consts.APP_COLOR, Consts.PRIMARY_COLOR)));
        tvBottomCartCount.setTextColor(Color.parseColor(getPreferences().getString(Consts.SECOND_COLOR, Consts.SECONDARY_COLOR)));
        llBottomLine.setBackgroundColor(Color.parseColor(getPreferences().getString(Consts.SECOND_COLOR, Consts.SECONDARY_COLOR)));

        switch (activity) {
            case "home":
            case "list":
                ivBottomHome.setColorFilter(Color.parseColor(getPreferences().getString(Consts.SECOND_COLOR, Consts.SECONDARY_COLOR)));
                break;
            case "search":
                ivBottomSearch.setColorFilter(Color.parseColor(getPreferences().getString(Consts.APP_COLOR, Consts.PRIMARY_COLOR)));
                tvBottomSearch.setTextColor(Color.parseColor(getPreferences().getString(Consts.APP_COLOR, Consts.PRIMARY_COLOR)));
                break;
            case "cart":
                ivBottomCart.setColorFilter(Color.parseColor(getPreferences().getString(Consts.APP_COLOR, Consts.PRIMARY_COLOR)));
                tvBottomCart.setTextColor(Color.parseColor(getPreferences().getString(Consts.APP_COLOR, Consts.PRIMARY_COLOR)));
                break;
            case "account":
                ivBottomAccount.setColorFilter(Color.parseColor(getPreferences().getString(Consts.APP_COLOR, Consts.PRIMARY_COLOR)));
                tvBottomAccount.setTextColor(Color.parseColor(getPreferences().getString(Consts.APP_COLOR, Consts.PRIMARY_COLOR)));
                break;
            case "wishList":
                ivBottomWishList.setColorFilter(Color.parseColor(getPreferences().getString(Consts.APP_COLOR, Consts.PRIMARY_COLOR)));
                tvBottomWishList.setTextColor(Color.parseColor(getPreferences().getString(Consts.APP_COLOR, Consts.PRIMARY_COLOR)));
                break;
        }
//        tvBottomCartCount.getBackground().setColorFilter(Color.parseColor(getPreferences().getString(Constant.SECOND_COLOR, Constant.SECONDARY_COLOR)), PorterDuff.Mode.SRC_IN);
        llHome.setOnClickListener(v -> {
            if (!activity.equals("home")) {
                Intent intent = new Intent(BaseActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        llSearchFromBottom.setOnClickListener(v -> {
            if (!activity.equals("search")) {
                Intent intent = new Intent(BaseActivity.this, SearchCategoryListActivity.class);
                startActivity(intent);
                if (!activity.equals("home")) {
                    finish();
                }
            }
        });
        llCart.setOnClickListener(v -> {
            if (!Config.IS_CATALOG_MODE_OPTION) {
                if (!activity.equals("cart")) {
                    Intent intent = new Intent(BaseActivity.this, CartActivity.class);
                    startActivity(intent);
                    if (!activity.equals("home")) {
                        finish();
                    }
                }
            } else {
                Intent intent = new Intent(BaseActivity.this, RewardsActivity.class);
                startActivity(intent);
            }
        });
        llAccount.setOnClickListener(v -> {
            if (!activity.equals("account")) {
                Intent intent = new Intent(BaseActivity.this, AccountActivity.class);
                startActivity(intent);
                if (!activity.equals("home")) {
                    finish();
                }
            }
        });

        llWishList.setOnClickListener(v -> {
            if (!activity.equals("wishList")) {
                Intent intent = new Intent(BaseActivity.this, WishListActivity.class);
                startActivity(intent);
                if (!activity.equals("home")) {
                    finish();
                }
            }
        });

        ViewTreeObserver vto = llBottomBar.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llBottomBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if (view != null) {
                    view.setPadding(0, 0, 0, llBottomBar.getMeasuredHeight());
                }
            }
        });
    }*/
}

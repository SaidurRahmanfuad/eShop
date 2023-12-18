package com.saidur.eshop.view;

import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import com.saidur.eshop.BaseActivity;
import com.saidur.eshop.MainActivity;
import com.saidur.eshop.databinding.ActivitySplashBinding;
import com.saidur.eshop.utils.RequestParamUtils;

public class ActivitySplash extends BaseActivity {
ActivitySplashBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setCurrentYear(binding.tvSplashText);
        clearUser();
        homeOrIntroActivity();
    }

    public void startActivityIntent(final Intent intent) {
        new Handler().postDelayed(() -> {
            startActivity(intent);
            finish();
        }, 1000);
    }
    public void homeOrIntroActivity() {
        int slider = getPreferences().getInt(RequestParamUtils.IS_SLIDER_SHOW, -1);
        boolean login = getPreferences().getBoolean(RequestParamUtils.LOGIN_SHOW_IN_APP_START, true);
        if (slider == 0 && login) {
            startActivityIntent(new Intent(this, ActivityIntroSlider.class));
        } else if (slider == 0) {
            startActivityIntent(new Intent(this, ActivityIntroSlider.class));
        } else if (login) {
            if (!getPreferences().getString(RequestParamUtils.ID, "").equals("")) {
                startActivityIntent(new Intent(ActivitySplash.this, MainActivity.class));
            } else {
                Intent intent = new Intent(ActivitySplash.this, ActivityLogin.class);
                intent.putExtra("is_splash", true);
                startActivityIntent(intent);
            }
        } else {
            startActivityIntent(new Intent(this, MainActivity.class));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissProgress();
    }
}
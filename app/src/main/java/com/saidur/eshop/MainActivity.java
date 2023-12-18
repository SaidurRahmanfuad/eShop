package com.saidur.eshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.saidur.eshop.databinding.ActivityMainBinding;
import com.saidur.eshop.view.FragmentCart;
import com.saidur.eshop.view.FragmentHome;
import com.saidur.eshop.view.FragmentSearch;
import com.saidur.eshop.view.FragmentWish;

public class MainActivity extends BaseActivity implements View.OnClickListener {
ActivityMainBinding binding;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (savedInstanceState == null) {
            fragmentManager = getSupportFragmentManager();
            FragmentHome dashFragment = new FragmentHome();
            fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.main_container, dashFragment).commit();
        }
        fun_bottomClick();
    }
    private void fun_bottomClick() {
        binding.include.ivBottomHome.setOnClickListener(MainActivity.this);
        binding.include.ivBottomSearch.setOnClickListener(MainActivity.this);
        binding.include.ivBottomCart.setOnClickListener(MainActivity.this);
        binding.include.ivBottomWishList.setOnClickListener(MainActivity.this);
        binding.include.ivBottomAccount.setOnClickListener(MainActivity.this);

    }
    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()) {
            case R.id.ivBottomHome:
                fragment = new FragmentHome();
               /* binding.btnDashboard.setBackgroundResource(R.drawable.shape_rect);
                binding.btnReports.setBackgroundResource(0);
                binding.btnSettings.setBackgroundResource(0);

                binding.icDash.setImageResource(R.drawable.ic_dashboard);
                binding.icReports.setImageResource(R.drawable.ic_reports_unselected);
                binding.icSettings.setImageResource(R.drawable.ic_settings_unselected);

                binding.tvDash.setTextColor(Color.parseColor("#FFFFFF"));
                binding.tvReports.setTextColor(Color.parseColor("#000000"));
                binding.tvSettings.setTextColor(Color.parseColor("#000000"));*/
                break;
            case R.id.ivBottomSearch:
                fragment = new FragmentSearch();
               /* binding.btnReports.setBackgroundResource(R.drawable.shape_rect);
                binding.btnSettings.setBackgroundResource(0);
                binding.btnDashboard.setBackgroundResource(0);

                binding.icDash.setImageResource(R.drawable.ic_dashboard_unselected);
                binding.icReports.setImageResource(R.drawable.ic_reports);
                binding.icSettings.setImageResource(R.drawable.ic_settings_unselected);

                binding.tvDash.setTextColor(Color.parseColor("#000000"));
                binding.tvReports.setTextColor(Color.parseColor("#FFFFFF"));
                binding.tvSettings.setTextColor(Color.parseColor("#000000"));*/
                break;
            case R.id.ivBottomCart:
                fragment = new FragmentCart();
      /*        binding.btnSettings.setBackgroundResource(R.drawable.shape_rect);
                binding.btnReports.setBackgroundResource(0);
                binding.btnDashboard.setBackgroundResource(0);

                binding.icDash.setImageResource(R.drawable.ic_dashboard_unselected);
                binding.icReports.setImageResource(R.drawable.ic_reports_unselected);
                binding.icSettings.setImageResource(R.drawable.ic_settings);

                binding.tvDash.setTextColor(Color.parseColor("#000000"));
                binding.tvReports.setTextColor(Color.parseColor("#000000"));
                binding.tvSettings.setTextColor(Color.parseColor("#FFFFFF"));*/
                break;
            case R.id.ivBottomWishList:
                fragment = new FragmentWish();

                break;
          /*  case R.id.ivBottomAccount:
                fragment = new FragmentSettings();

                break;*/
        }
        if (fragment != null) {
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.main_container, fragment).commit();
        }
    }
}
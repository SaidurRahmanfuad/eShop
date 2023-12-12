package com.saidur.eshop.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.saidur.eshop.databinding.ActivityIntroSliderBinding;

public class ActivityIntroSlider extends AppCompatActivity {
ActivityIntroSliderBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityIntroSliderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
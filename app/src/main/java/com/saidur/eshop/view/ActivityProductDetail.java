package com.saidur.eshop.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.saidur.eshop.databinding.ActivityProductDetailBinding;

public class ActivityProductDetail extends AppCompatActivity {
ActivityProductDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
package com.saidur.eshop.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.saidur.eshop.databinding.ActivityCheckoutBinding;

public class ActivityCheckout extends AppCompatActivity {
ActivityCheckoutBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
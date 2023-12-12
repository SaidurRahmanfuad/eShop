package com.saidur.eshop.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.saidur.eshop.databinding.ActivityLoginBinding;

public class ActivityLogin extends AppCompatActivity {
ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
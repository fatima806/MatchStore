package com.example.matchstore.ui.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.matchstore.R;
import com.example.matchstore.databinding.ActivityMatchDetailsBinding;
import com.example.matchstore.model.Product;
import com.example.matchstore.ui.home.HomeViewModel;

public class MatchDetailsActivity extends AppCompatActivity {
    ActivityMatchDetailsBinding binding;
    DetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMatchDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(DetailViewModel.class);

        Product product = (Product) getIntent().getSerializableExtra("item");

        binding.productPrice.setText(getString(R.string.price, product.getPrice()));
        Glide.with(this).load(product.getImage()).into(binding.productImage);

        binding.back.setOnClickListener(v -> {
            super.onBackPressed();
        });
    }
}
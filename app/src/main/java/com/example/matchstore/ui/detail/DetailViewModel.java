package com.example.matchstore.ui.detail;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.matchstore.model.Product;
import com.example.matchstore.repo.ProductRepo;

import java.util.List;

public class DetailViewModel extends ViewModel {
    private ProductRepo repository;

    public DetailViewModel() {
        //look for the best way to pass context
        repository = new ProductRepo();
    }
}

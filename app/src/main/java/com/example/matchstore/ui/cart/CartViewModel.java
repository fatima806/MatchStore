package com.example.matchstore.ui.cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.matchstore.model.Cart;
import com.example.matchstore.model.Product;
import com.example.matchstore.repo.ProductRepo;

import java.util.List;

public class CartViewModel extends ViewModel {
    private ProductRepo repository;

    public CartViewModel() {
        super();
        repository = new ProductRepo();
        repository.callCartProducts();
    }

    public LiveData<List<Product>> getCartProducts() {
        return repository.getOnlineCartProducts();
    }

    public LiveData<Boolean> isSpinning() {
        return repository.getSpinning();
    }
}
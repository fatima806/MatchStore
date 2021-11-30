package com.example.matchstore.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.matchstore.model.Product;
import com.example.matchstore.repo.ProductRepo;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private ProductRepo repository;


    public HomeViewModel() {
        //look for the best way to pass context
        repository = new ProductRepo();
        //get categories
        repository.getCategories();
    }

    public LiveData<Boolean> isSpinning() {
        return repository.getSpinning();
    }

    public LiveData<List<String>> getAllCategories() {
        return repository.getAllCategories();
    }
    public void callProductsByCategory(String categoryName){
        repository.callProductsByCategory(categoryName);
    }

    public LiveData<List<Product>> getProductsByCategory(){
        return repository.getProductsByCategory();
    }

}
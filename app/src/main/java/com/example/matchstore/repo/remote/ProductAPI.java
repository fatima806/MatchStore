package com.example.matchstore.repo.remote;

import com.example.matchstore.model.Cart;
import com.example.matchstore.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductAPI {
    @GET("products/categories")
    Call<List<String>> callCategories();

    @GET("products/category/{category_name}")
    Call<List<Product>> CallProductsByCategory(@Path(value = "category_name") String category);

    @GET("carts/{user_id}")
    Call<Cart> callCartProducts(@Path(value = "user_id") int id);

    @GET("products/{product_id}")
    Call<Product> CallProductsById(@Path(value = "product_id") int category);

}


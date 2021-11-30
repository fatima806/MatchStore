package com.example.matchstore.repo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.matchstore.BuildConfig;
import com.example.matchstore.model.Cart;
import com.example.matchstore.model.Product;
import com.example.matchstore.repo.remote.ProductAPI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductRepo {
    private static final String BASE_URL = BuildConfig.BASE_URL;
    private Retrofit retrofit;
    private ProductAPI service;
    private MutableLiveData<Boolean> spinning;
    private MutableLiveData<List<String>> categories;
    private MutableLiveData<List<Product>> productList;
    private MutableLiveData<List<Product>> productsByIdList;

    public ProductRepo() {
        createRetrofitInstance();
        service = retrofit.create(ProductAPI.class);
        spinning = new MutableLiveData<>();
        categories = new MutableLiveData<>();
        productList = new MutableLiveData<>();
        productsByIdList = new MutableLiveData<>();
    }


    public LiveData<Boolean> getSpinning() {
        return spinning;
    }

    public LiveData<List<String>> getAllCategories() {
        return categories;
    }

    public LiveData<List<Product>> getProductsByCategory() {
        return productList;
    }

    public LiveData<List<Product>> getOnlineCartProducts() {
        return productsByIdList;
    }


    public void getCategories() {
        spinning.setValue(true);
        service.callCategories().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                spinning.setValue(false);
                if (response.isSuccessful()) {
                    String data = response.body().toString().substring(1, response.body().toString().length() - 1);
                    List<String> myList = new ArrayList<>(Arrays.asList(data.split(",")));
                    categories.postValue(myList);
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                spinning.setValue(false);
            }
        });
    }

    public void callProductsByCategory(String categoryName) {
        spinning.setValue(true);
        service.CallProductsByCategory(categoryName).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                spinning.setValue(false);
                if (response.isSuccessful()) {
                    productList.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                spinning.setValue(false);
            }
        });
    }

    public void callCartProducts() {
        spinning.setValue(true);
        service.callCartProducts(1).enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {

                if (response.isSuccessful()) {
                    callProductById(response.body());
                } else {
                    spinning.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {
                spinning.setValue(false);
            }
        });
    }

    public void callProductById(Cart cartList) {
        ArrayList<Product> _productList = new ArrayList<>();
        for (int i = 0; i < cartList.getCartProduct().size(); i++) {
            service.CallProductsById(cartList.getCartProduct().get(i).getProductId()).enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    spinning.setValue(false);
                    if (response.isSuccessful()) {
                        _productList.add(response.body());
                        productsByIdList.setValue(_productList);
                    }
                }

                @Override
                public void onFailure(Call<Product> call, Throwable t) {
                    spinning.setValue(false);
                }
            });
        }

    }


    public void createRetrofitInstance() {
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
    }
}

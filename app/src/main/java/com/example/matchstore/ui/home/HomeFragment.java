package com.example.matchstore.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.matchstore.R;
import com.example.matchstore.adapter.CategoryMatchAdapter;
import com.example.matchstore.adapter.ProductMatchAdapter;
import com.example.matchstore.databinding.FragmentHomeBinding;
import com.example.matchstore.model.Product;

import java.util.List;

public class HomeFragment extends Fragment implements CategoryMatchAdapter.ItemClickListener {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    ProductMatchAdapter productMatchAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //init recycler view and adapter
        CategoryMatchAdapter categoryMatchAdapter = new CategoryMatchAdapter(this);
        binding.categoryRv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        binding.categoryRv.setAdapter(categoryMatchAdapter);

        productMatchAdapter = new ProductMatchAdapter();
        binding.productsRv1.setAdapter(productMatchAdapter);


        //get category data
        homeViewModel.getAllCategories().observe(getActivity(), categoryMatchAdapter::submitList);

        //spinning
        homeViewModel.isSpinning().observe(getActivity(), flag -> {
            if (flag) {
                if (binding != null)
                    binding.spinKit.setVisibility(View.VISIBLE);

            } else {
                if (binding != null)
                    binding.spinKit.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onItemClick(String name) {
        binding.productsRv1.removeAllViewsInLayout();
        homeViewModel.callProductsByCategory(name.trim());
        homeViewModel.getProductsByCategory().observe(getActivity(), products -> productMatchAdapter.submitList(products));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
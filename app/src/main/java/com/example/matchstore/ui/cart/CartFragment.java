package com.example.matchstore.ui.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.matchstore.R;
import com.example.matchstore.adapter.CartMatchAdapter;
import com.example.matchstore.databinding.FragmentCartBinding;
import com.example.matchstore.model.Product;

import java.util.List;

public class CartFragment extends Fragment {

    private static final double SHIPPING_FEE = 5.0 ;
    private CartViewModel cartViewModel;
    private FragmentCartBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cartViewModel =
                new ViewModelProvider(this).get(CartViewModel.class);

        binding = FragmentCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CartMatchAdapter adapter = new CartMatchAdapter();
        binding.cartRv.setLayoutManager(new LinearLayoutManager(getParentFragment().getContext(), LinearLayoutManager.VERTICAL, false));
        binding.cartRv.setAdapter(adapter);

        cartViewModel.getCartProducts().observe(getActivity(), products -> {
            adapter.submitList(products);
            adapter.notifyDataSetChanged();
            setProductValues(products);
        });

        cartViewModel.isSpinning().observe(getActivity(), spin -> {
            if (spin) {
                if (binding != null)
                    binding.spinKit.setVisibility(View.VISIBLE);
            } else {
                if (binding != null) {
                    binding.spinKit.setVisibility(View.GONE);
                    binding.cartLayout.setVisibility(View.VISIBLE);
                }

            }
        });

    }

    public void setProductValues(List<Product> products){
        double sum = 0;
        for (Product product:
             products) {
            sum += product.getPrice();
        }
        double total_price = sum+SHIPPING_FEE;
        binding.cartSubtotalPrice.setText(getString(R.string.price,sum));
        binding.cartTotalPrice.setText(getString(R.string.price,total_price));
        binding.cartSubtitle.setText(getString(R.string.item,products.size()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
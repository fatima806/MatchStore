package com.example.matchstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.example.matchstore.R;
import com.example.matchstore.model.Product;

import java.util.StringTokenizer;

public class CartMatchAdapter extends ListAdapter<Product, CartMatchAdapter.viewHolder> {
    Context context;

    public CartMatchAdapter() {
        super(Product.DIFF_CALLBACK);
    }


    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.match_cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(CartMatchAdapter.viewHolder holder, int position) {
        Product product = getCurrentList().get(position);
        StringTokenizer tokenizer = new StringTokenizer(product.getTitle()," ");
        String name = tokenizer.nextToken()+ " "+tokenizer.nextToken()+" "+tokenizer.nextToken();
        holder.productName.setText(name);
        holder.productPrice.setText(context.getString(R.string.price,product.getPrice()));
        GlideUrl glideUrl = new GlideUrl(product.getImage());
        Glide.with(context).load(glideUrl).into(holder.productImage);
        holder.cartAdd.setOnClickListener(v -> {
            holder.cartNumber.setText(String.valueOf(Integer.parseInt(holder.cartNumber.getText().toString()) + 1));
        });
        holder.cartSubtract.setOnClickListener(v -> {
            holder.cartNumber.setText(String.valueOf(Integer.parseInt(holder.cartNumber.getText().toString()) - 1));
        });
    }

    @Override
    public int getItemCount() {
        return getCurrentList().size();
    }


    class viewHolder extends RecyclerView.ViewHolder {
        ImageView productImage,cartAdd, cartSubtract, cartDelete;
        TextView cartNumber,productName,productPrice;

        public viewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_imageview);
            cartAdd = itemView.findViewById(R.id.cart_add);
            cartSubtract = itemView.findViewById(R.id.cart_subtract);
            cartDelete = itemView.findViewById(R.id.cart_delete);
            cartNumber = itemView.findViewById(R.id.cart_number);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
        }
    }
}


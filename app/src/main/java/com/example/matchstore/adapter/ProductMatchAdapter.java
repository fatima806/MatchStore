package com.example.matchstore.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.matchstore.ui.detail.MatchDetailsActivity;

import java.util.StringTokenizer;

public class ProductMatchAdapter extends ListAdapter<Product, ProductMatchAdapter.ViewHolder> {

    static boolean toggle = false;
    Context context;

    public ProductMatchAdapter() {
        super(Product.DIFF_CALLBACK
        );
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.match_product_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ProductMatchAdapter.ViewHolder holder, int position) {
        Product product = getCurrentList().get(position);
        StringTokenizer tokenizer = new StringTokenizer(product.getTitle()," ");
        String name = tokenizer.nextToken()+ " "+tokenizer.nextToken()+" "+tokenizer.nextToken();
        holder.productName.setText(name);
        holder.productPrice.setText(context.getString(R.string.price,product.getPrice()));
        GlideUrl glideUrl = new GlideUrl(product.getImage());
        Glide.with(context).load(glideUrl).into(holder.productImage);

        holder.favorite.setOnClickListener(v -> {
            toggle = !toggle;

            if (toggle) {
                holder.favorite.setImageResource(R.drawable.ic_favorite);
            } else {
                holder.favorite.setImageResource(R.drawable.ic_favorite_border);
            }
        });

    }

    @Override
    public int getItemCount() {
        return getCurrentList().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView favorite;
        ImageView productImage;
        TextView productName;
        TextView productPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            favorite = itemView.findViewById(R.id.favorite);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, MatchDetailsActivity.class);
                intent.putExtra("item",getCurrentList().get(getAdapterPosition()));
                context.startActivity(intent);
            });

        }
    }
}


package com.example.matchstore.adapter;

import static com.example.matchstore.model.Product.DIFF_CATEGORY_CALLBACK;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.matchstore.R;

import java.util.ArrayList;


public class CategoryMatchAdapter extends ListAdapter<String, CategoryMatchAdapter.ViewHolder> {
    Context context;
    private int selectedItem;
    private int selectedCategory = 0;
    private ItemClickListener itemClickListener;

    public CategoryMatchAdapter(ItemClickListener itemClickListener) {
        super(DIFF_CATEGORY_CALLBACK);
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.match_category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.categoryName.setText(getCurrentList().get(i));
        holder.categoryName.setTextColor(Color.BLACK);
        holder.categoryLayout.setBackground(context.getDrawable(R.drawable.category_item_bg));
        //fetch first category
        if (selectedCategory == 0) {
            itemClickListener.onItemClick(getCurrentList().get(i));
            selectedCategory = 1;
        }

        if (selectedItem == i) {
            holder.categoryName.setTextColor(Color.WHITE);
            holder.categoryLayout.setBackground(context.getDrawable(R.drawable.category_item_selected_bg));
        }
        holder.categoryLayout.setOnClickListener(v -> {
            int previousItem = selectedItem;
            selectedItem = i;
            itemClickListener.onItemClick(getCurrentList().get(i));

            notifyItemChanged(previousItem);
            notifyItemChanged(i);
        });
    }

    @Override
    public int getItemCount() {

        return getCurrentList().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout categoryLayout;
        TextView categoryName;

        public ViewHolder(View itemView) {
            super(itemView);
            categoryLayout = itemView.findViewById(R.id.category_item_layout);
            categoryName = itemView.findViewById(R.id.category);
        }
    }

    public interface ItemClickListener {
        void onItemClick(String name);
    }
}

package com.scube.localnews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.scube.localnews.ItemCallback;
import com.scube.localnews.R;
import com.scube.localnews.model.NewsItem;


import java.util.List;

public class VerticalViewPager2Adapter extends RecyclerView.Adapter<VerticalViewPager2Adapter.ViewHolder> {

    private Context ctx;
    List<NewsItem> slideItems;
    public VerticalViewPager2Adapter(Context ctx, List<NewsItem> slideItems ) {
        this.ctx = ctx;
        System.out.println(slideItems);
        this.slideItems = slideItems;
    }
    public void setSlideItems(List<NewsItem> slideItems){
        this.slideItems=slideItems;
    }

    public List<NewsItem> getSlideItems() {
       return this.slideItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.vertical_container, parent, false);
        return new ViewHolder(view);
    }

    // This method binds the screen with the view
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        this.itemCallback.putItem(slideItems.get(position));
        NewsItem m=slideItems.get(position);
        System.out.println("===="+m+"====");
        Glide.with(ctx)
                .load(slideItems.get(position).getImage())
                .into(holder.image);
        holder.header.setText(slideItems.get(position).getHeader());
        holder.desc.setText(slideItems.get(position).getDescription());


    }

    // This Method returns the size of the Array
    @Override
    public int getItemCount() {
        return slideItems.size();
    }

    // The ViewHolder class holds the view
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView header;
        TextView desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            header = itemView.findViewById(R.id.headline);
            desc = itemView.findViewById(R.id.desc);

        }
    }
}

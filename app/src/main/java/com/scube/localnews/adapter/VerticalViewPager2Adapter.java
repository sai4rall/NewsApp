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
import com.scube.localnews.R;
import com.scube.localnews.model.NewsItem;


import java.util.List;

public class VerticalViewPager2Adapter extends RecyclerView.Adapter<VerticalViewPager2Adapter.ViewHolder> {

    private Context ctx;
    List<NewsItem> newsItems;
    public VerticalViewPager2Adapter(Context ctx, List<NewsItem> newsItems ) {
        this.ctx = ctx;
        this.newsItems = newsItems;
    }
    public void setNewsItems(List<NewsItem> newsItems){
        this.newsItems = newsItems;
    }

    public List<NewsItem> getNewsItems() {
       return this.newsItems;
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
        NewsItem m= newsItems.get(position);
        Glide.with(ctx)
                .load(newsItems.get(position).getImage())
                .into(holder.image);
        holder.header.setText(newsItems.get(position).getHeader());
        holder.desc.setText(newsItems.get(position).getInsertTimestam().toDate()+":::"+newsItems.get(position).getDescription());


    }

    // This Method returns the size of the Array
    @Override
    public int getItemCount() {
        return newsItems.size();
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

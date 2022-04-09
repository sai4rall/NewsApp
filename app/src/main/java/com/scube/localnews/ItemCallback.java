package com.scube.localnews;

import com.scube.localnews.model.NewsItem;

import java.util.ArrayList;
import java.util.List;

public interface ItemCallback {
    NewsItem getItem();
    void  putItem(NewsItem slideItem);
    ArrayList<NewsItem> getAllNewsItems();
    void loadData();
}

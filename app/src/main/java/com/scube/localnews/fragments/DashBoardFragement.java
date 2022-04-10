package com.scube.localnews.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scube.localnews.IVerticalAdapter;
import com.scube.localnews.ItemCallback;
import com.scube.localnews.NewsApp;
import com.scube.localnews.R;
import com.scube.localnews.transformers.ZoomOutPageTransformer;


public class DashBoardFragement extends Fragment {
    IVerticalAdapter iVerticalAdapter;
    ItemCallback itemCallback;
    NewsApp newsApp;
    public DashBoardFragement(ItemCallback itemCallback, IVerticalAdapter iVerticalAdapter,NewsApp newsApp) {
        this.itemCallback=itemCallback;
        this.iVerticalAdapter=iVerticalAdapter;
        this.newsApp=newsApp;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_dash_board_fragement, container, false);
        ViewPager2 viewPager2=v.findViewById(R.id.vertical_container_v2);
        viewPager2.setClipToPadding(true);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        viewPager2.setPageTransformer(new ZoomOutPageTransformer());
        viewPager2.setAdapter(newsApp.getVerticalViewPager2Adapter());
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
               itemCallback.putItem(iVerticalAdapter.getVerticalViewPager2Adapter().getNewsItems().get(position));
                if(position==  newsApp.getNewsItemList().size()-2){
                    newsApp.loadData(newsApp.getNewsItemList().get(newsApp.getNewsItemList().size()-1).getInsertTimestam(),position);

                }
            }
        });
        return v;
    }


}
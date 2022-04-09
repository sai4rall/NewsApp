package com.scube.localnews.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.scube.localnews.IVerticalAdapter;
import com.scube.localnews.ItemCallback;
import com.scube.localnews.config.Constants;
import com.scube.localnews.fragments.DashBoardFragement;
import com.scube.localnews.fragments.WebViewFragment;
import com.scube.localnews.model.NewsItem;

import java.util.List;

public class HorizontalViewPager2Adapter  extends FragmentStateAdapter {
    ItemCallback itemCallback;
    IVerticalAdapter iVerticalAdapter;
    public HorizontalViewPager2Adapter(FragmentActivity activity, ItemCallback itemCallback, IVerticalAdapter iVerticalAdapter) {
        super(activity);
        this.itemCallback=itemCallback;
        this.iVerticalAdapter=iVerticalAdapter;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a NEW fragment instance in createFragment(int)
        if(position==0){
            return new DashBoardFragement(itemCallback,iVerticalAdapter);
        }else{
            Fragment webViewFragment= new WebViewFragment(this.itemCallback);
            Bundle args = new Bundle();
            args.putString(Constants.KEY_WEB_VIEW_URL, itemCallback.getItem().getLinkAddress());
            webViewFragment.setArguments(args);
           return webViewFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

// Instances of this class are fragments representing a single
// object in our collection.

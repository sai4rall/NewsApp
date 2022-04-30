package com.scube.localnews.activities;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.messaging.FirebaseMessaging;
import com.scube.localnews.IVerticalAdapter;
import com.scube.localnews.ItemCallback;
import com.scube.localnews.NewsApp;
import com.scube.localnews.R;
import com.scube.localnews.adapter.HorizontalViewPager2Adapter;
import com.scube.localnews.adapter.VerticalViewPager2Adapter;
import com.scube.localnews.model.NewsItem;
import com.scube.localnews.service.FcmReceiverService;

@Deprecated
public class MainActivity extends FragmentActivity implements ItemCallback, IVerticalAdapter {
    NewsApp newsApp;
    ViewPager2 viewPager2;
    NewsItem newsItem;
    FirebaseFirestore db;
    private DatabaseReference mDatabase;
    HorizontalViewPager2Adapter horizontalViewPager2Adapter;
    VerticalViewPager2Adapter verticalViewPager2Adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horizantal_container);
        FirebaseMessaging.getInstance().subscribeToTopic("local-news");

        getActionBar().setTitle("LocalNews");
        newsApp=(NewsApp)getApplication();
        db = newsApp.getDb();

        initHorizontalViewPager();
        verticalViewPager2Adapter = new VerticalViewPager2Adapter( getApplicationContext(), newsApp.getNewsItemList());
        newsApp.setVerticalViewPager2Adapter(verticalViewPager2Adapter);

    }
    public  void initHorizontalViewPager(){
        viewPager2=findViewById(R.id.horizantal_viewpager);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        horizontalViewPager2Adapter=new HorizontalViewPager2Adapter(this,MainActivity.this,this,newsApp);
        viewPager2.setAdapter(horizontalViewPager2Adapter);
    }

    @Override
    public NewsItem getItem() {
        return this.newsItem;
    }

    @Override
    public void putItem(NewsItem slideItem) {
        this.newsItem =slideItem;
    }

    @Override
    public VerticalViewPager2Adapter getVerticalViewPager2Adapter() {
        return this.verticalViewPager2Adapter;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_logout:
                FcmReceiverService.unsubscribeTopic(getApplicationContext(),"localNewsApp");

                newsApp.getmAuth().signOut();
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
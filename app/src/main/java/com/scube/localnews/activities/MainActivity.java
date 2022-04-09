package com.scube.localnews.activities;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.scube.localnews.IVerticalAdapter;
import com.scube.localnews.ItemCallback;
import com.scube.localnews.R;
import com.scube.localnews.adapter.HorizontalViewPager2Adapter;
import com.scube.localnews.adapter.VerticalViewPager2Adapter;
import com.scube.localnews.model.NewsItem;
import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements ItemCallback, IVerticalAdapter,OnCompleteListener<QuerySnapshot> {
ViewPager2 viewPager2;
NewsItem newsItem;
    FirebaseFirestore db;
    private DatabaseReference mDatabase;
    ArrayList<NewsItem> newsItemList =new ArrayList<>();
    HorizontalViewPager2Adapter horizontalViewPager2Adapter;
    VerticalViewPager2Adapter verticalViewPager2Adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horizantal_container);
        FirebaseApp.initializeApp(getApplicationContext());
        db = FirebaseFirestore.getInstance();
        db.collection("newsItems")
                .limit(5)
                .get()
                .addOnCompleteListener(MainActivity.this);

        initHorizontalViewPager();
         verticalViewPager2Adapter = new VerticalViewPager2Adapter( getApplicationContext(), newsItemList);


    }
    public  void initHorizontalViewPager(){
        viewPager2=findViewById(R.id.horizantal_viewpager);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        horizontalViewPager2Adapter=new HorizontalViewPager2Adapter(this,MainActivity.this,this);
        viewPager2.setAdapter(horizontalViewPager2Adapter);
//        viewPager2.setAdapter(new Vertical2adapter(this,this,generateData(0)));
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
    public ArrayList<NewsItem> getAllNewsItems() {
        return this.newsItemList;
    }

    @Override
    public void loadData() {
        db.collection("newsItems")
                .limit(5)
                .get()
                .addOnCompleteListener(MainActivity.this);
    }


    @Override
    public VerticalViewPager2Adapter getVerticalViewPager2Adapter() {
        return this.verticalViewPager2Adapter;
    }


    @Override
    public void onComplete(@NonNull Task<QuerySnapshot> task) {
        if (task.isSuccessful()) {
            for (QueryDocumentSnapshot document : task.getResult()) {
                Log.d("TAG", document.getId() + " => " + document.getData());
                NewsItem newsItem=document.toObject(NewsItem.class);
                newsItemList.add(newsItem);
                MainActivity.this.verticalViewPager2Adapter.notifyDataSetChanged();
                Log.d("TAG", newsItem.getHeader() + "** => " + document.getData());
            }
        } else {
            Log.w("TAG", "Error getting documents.", task.getException());
        }
    }
}
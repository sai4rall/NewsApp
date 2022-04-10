package com.scube.localnews;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.scube.localnews.activities.MainActivity;
import com.scube.localnews.adapter.VerticalViewPager2Adapter;
import com.scube.localnews.model.NewsItem;

import java.util.ArrayList;

public class NewsApp extends Application implements OnCompleteListener<QuerySnapshot> {
    FirebaseApp firebaseApp;
    FirebaseFirestore db;
    private FirebaseAuth mAuth;
    VerticalViewPager2Adapter verticalViewPager2Adapter;
    ArrayList<NewsItem> newsItemList =new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        this.firebaseApp= FirebaseApp.initializeApp(getApplicationContext());
        this.db = FirebaseFirestore.getInstance();
        this.mAuth=FirebaseAuth.getInstance();

    }
    public  FirebaseFirestore getDb(){
        return db;
    }
    public FirebaseAuth getmAuth(){
        return mAuth;
    }
    public void loadData(Timestamp timestamp,int position) {

        db.collection("newsItems")
                .whereGreaterThan("insertTimestamp",timestamp).orderBy("insertTimestamp", Query.Direction.ASCENDING).limit(position==0?5:(position*2))
                .get()
                .addOnCompleteListener(NewsApp.this);
        Toast.makeText(getApplicationContext(),timestamp+" fetching more  ",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onComplete(@NonNull Task<QuerySnapshot> task) {
        if (task.isSuccessful()) {
            for (QueryDocumentSnapshot document : task.getResult()) {
                Log.d("TAG", document.getId() + " => " + document.getData());
                NewsItem newsItem=document.toObject(NewsItem.class);
                Timestamp t=(Timestamp) document.getData().get("insertTimestamp");
                newsItem.setInsertTimestam(t);
                newsItemList.add(newsItem);
                Log.d("TAG", newsItem.getHeader() + "** => " + document.getData());
                this.getVerticalViewPager2Adapter().notifyDataSetChanged();
            }
        } else {
            Log.w("TAG", "Error getting documents.", task.getException());
        }
    }

    public ArrayList<NewsItem> getNewsItemList() {
        return newsItemList;
    }

    public void setNewsItemList(ArrayList<NewsItem> newsItemList) {
        this.newsItemList = newsItemList;
    }

    public VerticalViewPager2Adapter getVerticalViewPager2Adapter() {
        return verticalViewPager2Adapter;
    }

    public void setVerticalViewPager2Adapter(VerticalViewPager2Adapter verticalViewPager2Adapter) {
        this.verticalViewPager2Adapter = verticalViewPager2Adapter;
    }


}

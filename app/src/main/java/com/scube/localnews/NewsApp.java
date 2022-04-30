package com.scube.localnews;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
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
import com.scube.localnews.adapter.VerticalViewPager2Adapter;
import com.scube.localnews.model.NewsItem;

import java.util.ArrayList;
import java.util.Date;

public class NewsApp extends Application implements OnCompleteListener<QuerySnapshot> ,IDataLoadCallBack {
    private static final String CHANNEL_ID ="LocalNewsAppNotifications" ;
    FirebaseApp firebaseApp;
    FirebaseFirestore db;
    private FirebaseAuth mAuth;
    VerticalViewPager2Adapter verticalViewPager2Adapter;
    ArrayList<NewsItem> newsItemList =new ArrayList<>();
    NotificationChannel channel;
    IDataLoadCallBack callBack;

    public void setmFirstMessgaeID(String mFirstMessgaeID){
        this.mFirstMessgaeID=mFirstMessgaeID;
    }

    String mFirstMessgaeID;
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
    public void loadData(Timestamp timestamp,int position,String firstMessageId,IDataLoadCallBack mcallBack) {
        this.mFirstMessgaeID=firstMessageId;
        this.callBack=mcallBack;
        db.collection("newsItems")
                .whereLessThan("insertTimestamp",timestamp)
                .orderBy("insertTimestamp", Query.Direction.DESCENDING).limit(position==0?5:(position*2))
                .get()
                .addOnCompleteListener(NewsApp.this);

    }
    public Timestamp getMidNightTime(){
        Date date=new Date();
        date.setDate(date.getDate()+1);
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);

        Timestamp midNight=new Timestamp(date);
        return midNight;
    }

    @Override
    public void onComplete(@NonNull Task<QuerySnapshot> task) {
        if (task.isSuccessful()) {
            for (QueryDocumentSnapshot document : task.getResult()) {
                if(document.getId().equalsIgnoreCase(mFirstMessgaeID)){
                    ArrayList<NewsItem> tempList=new ArrayList<>();
                    NewsItem newsItem=document.toObject(NewsItem.class);
                    Timestamp t=(Timestamp) document.getData().get("insertTimestamp");
                    newsItem.setInsertTimestam(t);
                    tempList.add(newsItem);
                    tempList.addAll(newsItemList);
                    newsItemList=tempList;
                }else{
                    NewsItem newsItem=document.toObject(NewsItem.class);
                    Timestamp t=(Timestamp) document.getData().get("insertTimestamp");
                    newsItem.setInsertTimestam(t);
                    newsItemList.add(newsItem);
                }
                callBack.OnItemsLoaded(true);
            }
        } else {
            callBack.OnItemsLoaded(false);
            Log.w("TAG", "Error getting documents.", task.getException());
        }
    }

    public ArrayList<NewsItem> getNewsItemList() {
        return newsItemList;
    }

    public VerticalViewPager2Adapter getVerticalViewPager2Adapter() {
        return verticalViewPager2Adapter;
    }

    public void setVerticalViewPager2Adapter(VerticalViewPager2Adapter verticalViewPager2Adapter) {
        this.verticalViewPager2Adapter = verticalViewPager2Adapter;
    }
    public void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            this.channel = new NotificationChannel(CHANNEL_ID, name, importance);
            this.channel.setDescription(description);
            Log.d("====>channelId",channel.getId());
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    public NotificationChannel getNotificationChannel(){
        if(this.channel==null){
            createNotificationChannel();
        }
        return this.channel;
    }

    @Override
    public void OnItemsLoaded(boolean isSuccess){
        this.getVerticalViewPager2Adapter().notifyDataSetChanged();
    }
}

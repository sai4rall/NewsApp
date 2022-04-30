package com.scube.localnews.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.scube.localnews.IDataLoadCallBack;
import com.scube.localnews.ItemCallback;
import com.scube.localnews.NewsApp;
import com.scube.localnews.config.Constants;

import java.util.Map;

public class FcmReceiverService extends FirebaseMessagingService{
    NewsApp newsApp;
    private static String TAG="FcmReceiverService";
    public FcmReceiverService(){
        super();
    }


    public static void subscribeTopic(Context context, String topic){
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnSuccessListener(unused ->
                        Log.d(TAG,"Subscribed to topic"))
                .addOnFailureListener(e -> Log.d (TAG,"Failed to Subscribe $topic"));

    }

    public static void unsubscribeTopic(Context context, String topic){
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
                .addOnSuccessListener(unused ->  Log.d(TAG, "UnSubscribed $topic"))
                .addOnFailureListener(e -> Log.d(TAG, "UnSubscribed $topic failed"));
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message){
        super.onMessageReceived(message);
        newsApp=(NewsApp) getApplication();
        Log.d("Notificatin received ==>", message.getData().toString());
        Map<String,String> mmap=message.getData();
        String firstMessageId=mmap.get(Constants.KEY_FIRST_MESSAGE_ID);
        newsApp.loadData(newsApp.getMidNightTime(),0,firstMessageId,newsApp);
//        sendNotification(mmap.get("title"),mmap.get("body"));
    }

    @Override
    public void onNewToken(@NonNull String token){
        super.onNewToken(token);
        Log.d("New Token ==>", token);
    }

/*    public void sendNotification(String title, String message){

        //Get an instance of NotificationManager//
        newsApp=(NewsApp) getApplication();
        Intent notificationIntent = new Intent(getApplicationContext(), SplashScreen.class);
        notificationIntent.putExtra(Constants.KEY_FIRST_MESSAGE_ID,"this is Id from notification");
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(),
                (int) System.currentTimeMillis(), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder=new NotificationCompat.Builder(this, newsApp.getNotificationChannel().getId())
                .setSmallIcon(R.drawable.ic_logo_app)
                .setContentTitle(title)
                .setContentText(message)
                .setChannelId("LocalNewsAppNotifications")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(contentIntent);
        NotificationManagerCompat notificationManager=NotificationManagerCompat.from(newsApp.getApplicationContext());

// notificationId is a unique int for each notification that you must define
        notificationManager.notify((int) Math.random(), builder.build());
    }

    public void sendNotification2(Context context, String title, String message){
        String channel="";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            channel=createChannel();
        else{
            channel="";
        }

//        RemoteViews mContentView=new RemoteViews(getApplicationContext().getPackageName(), R.layout.general_notification_layout_new);

        NotificationManager mNotificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context, channel);
        Intent notificationIntent=new Intent(getApplicationContext(), SplashScreen.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent contentIntent=PendingIntent.getActivity(context, (int) System.currentTimeMillis(), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        builder.setSmallIcon(R.drawable.ic_logo_app)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentTitle(title)
                .setContentText(message)
//                .setSound ( notificationSound )
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
//                .setCustomContentView(mContentView)
//                .setCustomBigContentView(mContentView)
                .setContentIntent(contentIntent);


        builder.setAutoCancel(true);
        int rndId=(int)Math.random();

        Notification notification=builder.build();
        mNotificationManager.notify(rndId, notification);
    }*/
    @RequiresApi(api = Build.VERSION_CODES.O)
    private synchronized String createChannel() {
        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        String name = "dummy text for channel";
        int importance = NotificationManager.IMPORTANCE_LOW;

        NotificationChannel mChannel = new NotificationChannel("LocalNewsAppNotifications",name, importance);
                mChannel.setShowBadge(false);
        mChannel.enableLights(true);
        mChannel.setLightColor(Color.BLUE);
        if (mNotificationManager != null) {
            mNotificationManager.createNotificationChannel(mChannel);
        } else {
            stopSelf();
        }
        return "Channel";
    }


}

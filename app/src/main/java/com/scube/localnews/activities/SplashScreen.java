package com.scube.localnews.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseUser;
import com.scube.localnews.IDataLoadCallBack;
import com.scube.localnews.NewsApp;
import com.scube.localnews.R;
import com.scube.localnews.config.Constants;
import com.scube.localnews.utils.Helper;

import java.util.Date;

public class SplashScreen extends AppCompatActivity implements IDataLoadCallBack{

    ImageView logo;
    TextView appName;
    Animation topAnim, bottomAnim;
    NewsApp newsApp;
    String firstMessageId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firstMessageId= getIntent().getStringExtra(Constants.KEY_FIRST_MESSAGE_ID);
        newsApp = (NewsApp) getApplication();
        newsApp.loadData(newsApp.getMidNightTime(),0,firstMessageId,this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        initViews();
        newsApp.createNotificationChannel();
//        navigateToActivity();
    }

    private void navigateToActivity() {
        new Handler().postDelayed(() -> {
            if(Helper.isNetworkAvailable(SplashScreen.this)) {
                if (newsApp.getmAuth().getCurrentUser() != null) {
                    FirebaseUser currentUser = newsApp.getmAuth().getCurrentUser();
                    Intent mainActivity = new Intent(SplashScreen.this, HomeActivity.class);
                    startActivity(mainActivity);
                } else {
                    Intent mainActivity = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(mainActivity);
                }
            }else{
                Helper.showErrorScreen(SplashScreen.this,"SplashScreen");

            }
            finish();

        },3000);
    }

    private void initViews() {
        logo = (ImageView) findViewById(R.id.spalash_logo);
        appName = (TextView) findViewById(R.id.splash_title);
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animatotion);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        logo.setAnimation(topAnim);
        appName.setAnimation(bottomAnim);
    }

    @Override
    public void OnItemsLoaded(boolean isSuccess){
        if(isSuccess){
            navigateToActivity();
        }else{
            //TODO Navigate to Error Activity
        }
    }
}
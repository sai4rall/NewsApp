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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.scube.localnews.NewsApp;
import com.scube.localnews.R;
import com.scube.localnews.utils.Helper;

public class SplashScreen extends AppCompatActivity {

    ImageView logo;
    TextView appName;
    Animation topAnim, bottomAnim;
    NewsApp newsApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        initViews();
        navigateToActivity();
    }

    private void navigateToActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Helper.isNetworkAvailable(SplashScreen.this)) {
                    if (newsApp.getmAuth().getCurrentUser() != null) {
                        FirebaseUser currentUser = newsApp.getmAuth().getCurrentUser();
                        Toast.makeText(getApplicationContext(), currentUser.getEmail(), Toast.LENGTH_LONG).show();
                        Intent mainActivity = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(mainActivity);
                    } else {

                        Intent mainActivity = new Intent(SplashScreen.this, LoginActivity.class);
                        startActivity(mainActivity);
                    }
                }else{
                    Helper.showErrorScreen(SplashScreen.this,"SplashScreen");

                }
                finish();

            }
        }, 5000);
    }

    private void initViews() {
        logo = (ImageView) findViewById(R.id.spalash_logo);
        appName = (TextView) findViewById(R.id.splash_title);
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animatotion);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        ;
        logo.setAnimation(topAnim);
        appName.setAnimation(bottomAnim);
        newsApp = (NewsApp) getApplication();
    }
}
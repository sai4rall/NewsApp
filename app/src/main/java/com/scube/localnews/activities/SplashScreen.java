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

import com.scube.localnews.R;

public class SplashScreen extends AppCompatActivity {

    ImageView logo;
    TextView appName;
    Animation topAnim,bottomAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        initViews();
        navigateToActivity();
    }

    private void navigateToActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainActivity=new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(mainActivity);
                finish();
            }
        },5000);
    }

    private void initViews() {
        logo=(ImageView) findViewById(R.id.spalash_logo);
        appName=(TextView) findViewById(R.id.splash_title);
        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animatotion);
        bottomAnim=AnimationUtils.loadAnimation(this,R.anim.bottom_animation);;
        logo.setAnimation(topAnim);
        appName.setAnimation(bottomAnim);
    }
}
package com.scube.localnews.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import com.scube.localnews.R;

public class ErrorActivity extends AppCompatActivity {
    Button retry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_bkp);
        getSupportActionBar().setTitle("No Internet");
        retry=(Button) findViewById(R.id.retry);
        final String callback=getIntent().getStringExtra("callback");
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Class<?> act = Class.forName("com.scube.localnews.activities."+callback);
                    Intent i=new Intent(ErrorActivity.this,act);
                    startActivity(i);
                    finish();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                //Toast.makeText(ErrorActivity.this,callback,Toast.LENGTH_LONG).show();
            }
        });

    }

}
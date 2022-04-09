package com.scube.localnews;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class NewsApp extends Application {
    FirebaseApp firebaseApp;
    FirebaseFirestore db;
    private FirebaseAuth mAuth;

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

}

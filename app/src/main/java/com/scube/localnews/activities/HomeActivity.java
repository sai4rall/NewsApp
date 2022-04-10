package com.scube.localnews.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.scube.localnews.IVerticalAdapter;
import com.scube.localnews.ItemCallback;
import com.scube.localnews.NewsApp;
import com.scube.localnews.R;
import com.scube.localnews.adapter.HorizontalViewPager2Adapter;
import com.scube.localnews.adapter.VerticalViewPager2Adapter;
import com.scube.localnews.model.NewsItem;

import org.w3c.dom.Text;

import java.util.Date;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ItemCallback, IVerticalAdapter {
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
        setContentView(R.layout.main_base);
        newsApp=(NewsApp)getApplication();
        FirebaseUser currentUser = newsApp.getmAuth().getCurrentUser();
        Toast.makeText(getApplicationContext(), currentUser.getDisplayName()+"===222", Toast.LENGTH_LONG).show();

        setUpNavigationDrawer();
        db = newsApp.getDb();
        Date date=new Date();
        date.setDate(date.getDate());
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);

        Toast.makeText(getApplicationContext(),date+" date value  ",Toast.LENGTH_LONG).show();
        Timestamp MidNight=new Timestamp(date);
        Toast.makeText(getApplicationContext(),MidNight.toDate()+" newsItems  ",Toast.LENGTH_LONG).show();
        initHorizontalViewPager();
        newsApp.loadData(MidNight,0);
        verticalViewPager2Adapter = new VerticalViewPager2Adapter( getApplicationContext(), newsApp.getNewsItemList());
        newsApp.setVerticalViewPager2Adapter(verticalViewPager2Adapter);
    }
    public  void initHorizontalViewPager(){

        viewPager2=findViewById(R.id.horizantal_viewpager);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        horizontalViewPager2Adapter=new HorizontalViewPager2Adapter(this,HomeActivity.this,this,newsApp);
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


    private void setUpNavigationDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("newsApp");
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        TextView emailAddress=(TextView) navigationView.getHeaderView(0).findViewById(R.id.emailAddressMenu);
        emailAddress.setText(newsApp.getmAuth().getCurrentUser().getEmail());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.nav_logout){
            newsApp.getmAuth().signOut();
            Intent intent=new Intent(HomeActivity.this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return true;
        }
        return false;
    }
}
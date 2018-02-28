package com.eventfinder.www.eventfindermobile;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class NotificationsActivity extends AppCompatActivity {
    private TabLayout tabs;
    private ViewPager viewPager;
    private NotificationsPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        //
        // Set up tabs (TabLayout) with the Viewpager using the NotificationsPagerAdapter
        // so the correct fragment can be displayed within the viewpager
        tabs = (TabLayout) findViewById(R.id.notifications_tablayout);
        viewPager = (ViewPager) findViewById(R.id.notifications_viewpager);
        adapter = new NotificationsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        //
        //Update current tab item based on tab position
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        ImageButton homebtn = (ImageButton)findViewById(R.id.home);
        ImageButton profilebtn = (ImageButton)findViewById(R.id.profile);
        ImageButton addbtn = (ImageButton)findViewById(R.id.add);
        ImageButton notbtn = (ImageButton)findViewById(R.id.notification);
        ImageButton favbtn = (ImageButton)findViewById(R.id.favorite);

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotificationsActivity.this, HomeScreenActivity.class));
            }
        });

        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotificationsActivity.this, Profile.class));
            }
        });

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotificationsActivity.this, AddEventActivity.class));
            }
        });

        notbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotificationsActivity.this, NotificationsActivity.class));
            }
        });

        favbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotificationsActivity.this, FavoriteEventsActivity.class));
            }
        });

        Button btn = (Button)findViewById(R.id.m1);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotificationsActivity.this, ChatActivity.class));
            }
        });
    }
}

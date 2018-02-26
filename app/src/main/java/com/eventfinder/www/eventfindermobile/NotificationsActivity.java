package com.eventfinder.www.eventfindermobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

public class NotificationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        TabLayout tabs = findViewById(R.id.notification_tabs);


        //TO DO: Set up tabs with view pager so respective view can be displayed
    }
}

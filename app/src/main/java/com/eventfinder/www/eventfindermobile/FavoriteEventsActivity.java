package com.eventfinder.www.eventfindermobile;

import android.support.v4.view.PagerAdapter;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FavoriteEventsActivity extends AppCompatActivity {
    private TabLayout tabs;
    private ViewPager viewPager;
    private FavoriteEventsPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_events);

        //
        // Set up tabs (TabLayout) with the Viewpager using the NotificationsPagerAdapter
        // so the correct fragment can be displayed within the viewpager
        tabs = (TabLayout) findViewById(R.id.fav_event_tablayout);
        viewPager = (ViewPager) findViewById(R.id.fav_event_viewpager);
        adapter = new FavoriteEventsPagerAdapter(getSupportFragmentManager());
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
    }
}

package com.eventfinder.www.eventfindermobile;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.eventfinder.www.eventfindermobile.api.DataParsing;
import com.eventfinder.www.eventfindermobile.api.Requests;
import com.eventfinder.www.eventfindermobile.api.VolleyHandler;
import com.eventfinder.www.eventfindermobile.api.VolleyResponseListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class NotificationsActivity extends AppCompatActivity implements InviteBannerFragment.OnHeadlineSelectedListener, NotificationBanner.OnHeadlineSelectedListener{
    private TabLayout tabs;
    private ViewPager viewPager;
    private NotificationsPagerAdapter adapter;
    private List<Notification> notifications;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        bundle = getIntent().getExtras();
        User user = (User)bundle.getSerializable("me");

        displayNotif();

        //get user notifications

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
                Intent intent = new Intent(NotificationsActivity.this, HomeScreenActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotificationsActivity.this, AddEventActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotificationsActivity.this, Profile.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        favbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotificationsActivity.this, FavoriteEventsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    void displayNotif() {
        /*getSupportFragmentManager().beginTransaction()
                .add(R.id.invite, new InviteBannerFragment())
                .commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.message, new NotificationBanner())
                .commit();*/
    }

    @Override
    public void onArticleSelected(Notification not) {
        if(not.isInvite) {
            Intent intent = new Intent(NotificationsActivity.this, Profile.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("viewUser", not.user);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            Intent intent = new Intent(NotificationsActivity.this, ViewEventActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("event", not.event);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}

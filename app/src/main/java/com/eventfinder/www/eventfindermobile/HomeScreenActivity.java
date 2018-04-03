package com.eventfinder.www.eventfindermobile;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class HomeScreenActivity extends AppCompatActivity implements EventBanner.OnHeadlineSelectedListener{

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        User user = new User();
        final Event[] event = new Event[1];
        bundle = new Bundle();
        bundle.putSerializable("user", (Serializable)user);

        ImageButton homebtn = (ImageButton)findViewById(R.id.home);
        ImageButton profilebtn = (ImageButton)findViewById(R.id.profile);
        ImageButton addbtn = (ImageButton)findViewById(R.id.add);
        ImageButton notbtn = (ImageButton)findViewById(R.id.notification);
        ImageButton favbtn = (ImageButton)findViewById(R.id.favorite);


        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreenActivity.this, Profile.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreenActivity.this, AddEventActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        notbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreenActivity.this, NotificationsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        favbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreenActivity.this, FavoriteEventsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void onArticleSelected(Event event) {
        Intent intent = new Intent(HomeScreenActivity.this, ViewEventActivity.class);
        bundle.putSerializable("event", event);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}

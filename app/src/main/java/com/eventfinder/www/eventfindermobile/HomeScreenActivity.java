package com.eventfinder.www.eventfindermobile;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.toolbox.JsonArrayRequest;
import com.eventfinder.www.eventfindermobile.api.EventFinderAPI;
import com.eventfinder.www.eventfindermobile.api.Requests;
import com.eventfinder.www.eventfindermobile.api.VolleyHandler;
import com.eventfinder.www.eventfindermobile.api.VolleyResponseListener;

import org.json.JSONArray;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class HomeScreenActivity extends AppCompatActivity implements InterestSearch.InterestListener{

    Bundle bundle;
    ListView eventList;
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
    Event[] events;
    Button interestBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        interestBtn = (Button)findViewById(R.id.interest);

        getRecentEvents();
        Event event = new Event();
        Event event2 = new Event();
        events = new Event[] {
                event,
                event2
        };
        eventList=(ListView)findViewById(R.id.bannerlist);

        ArrayList<HashMap<String, String>> aList = new ArrayList<>();
        for(int i = 0; i < events.length; i++) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("name", events[i].eventName);
            hashMap.put("location", events[i].location);
            String formatDate = df.format(events[i].eventDate);
            hashMap.put("date", formatDate);
            aList.add(hashMap);
        }
        String[] from = {"name", "location", "date"};
        int[] to = {R.id.eventName, R.id.location, R.id.dateTime};
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, aList, R.layout.fragment_event_banner, from, to);
        eventList.setAdapter(simpleAdapter);

        User user = new User();
        bundle = getIntent().getExtras();
        bundle.putSerializable("user", (Serializable)user);

        ImageButton homebtn = (ImageButton)findViewById(R.id.home);
        ImageButton profilebtn = (ImageButton)findViewById(R.id.profile);
        ImageButton addbtn = (ImageButton)findViewById(R.id.add);
        ImageButton notbtn = (ImageButton)findViewById(R.id.notification);
        ImageButton favbtn = (ImageButton)findViewById(R.id.favorite);

        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Event eventSelected = events[i];
                Intent intent = new Intent(HomeScreenActivity.this, ViewEventActivity.class);
                bundle.putSerializable("event", eventSelected);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

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

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String selected) {
        interestBtn.setText(selected);
    }

    private void getRecentEvents(){
        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_LONG;
        VolleyResponseListener listener = new VolleyResponseListener(){
            @Override
            public void onError(String message) {
                Toast.makeText(context, "Recent events could not be loaded.", duration).show();
            }

            @Override
            public void onResponse(Object response) {
                Toast.makeText(context, "Recent events loaded successfully.", duration).show();
            }
        };
        JsonArrayRequest req = Requests.getRecentEvents(10,listener);
        VolleyHandler.getInstance(context).addToRequestQueue(req);
    }
}

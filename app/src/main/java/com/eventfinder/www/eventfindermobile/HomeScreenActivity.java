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
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.eventfinder.www.eventfindermobile.api.DataParsing;
import com.eventfinder.www.eventfindermobile.api.EventFinderAPI;
import com.eventfinder.www.eventfindermobile.api.Requests;
import com.eventfinder.www.eventfindermobile.api.VolleyHandler;
import com.eventfinder.www.eventfindermobile.api.VolleyResponseListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class HomeScreenActivity extends AppCompatActivity implements InterestSearch.InterestListener{

    Bundle bundle;
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
    Button interestBtn;
    Button searchBtn;
    SearchView searchView;
    ListView eventList;
    EventListAdapter eventListAdapter;
    List<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        interestBtn = (Button)findViewById(R.id.interest);
        searchBtn = (Button)findViewById(R.id.search_button);
        searchView = (SearchView) findViewById(R.id.searchView);

        events = new ArrayList();
        eventList=(ListView)findViewById(R.id.bannerlist);
        eventListAdapter = new EventListAdapter(this, events);
        eventList.setAdapter(eventListAdapter);
        bundle = getIntent().getExtras();
        User user = (User)bundle.getSerializable("me");

        getRecentEvents();

        ImageButton homebtn = (ImageButton)findViewById(R.id.home);
        ImageButton profilebtn = (ImageButton)findViewById(R.id.profile);
        ImageButton addbtn = (ImageButton)findViewById(R.id.add);
        ImageButton notbtn = (ImageButton)findViewById(R.id.notification);
        ImageButton favbtn = (ImageButton)findViewById(R.id.favorite);

        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Event eventSelected = events.get(i);
                Intent intent = new Intent(HomeScreenActivity.this, ViewEventActivity.class);
                bundle.putSerializable("event", eventSelected);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchEvents();
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
                System.out.println(message);
                Toast.makeText(context, "Recent events could not be loaded.", duration).show();
            }

            @Override
            public void onResponse(Object response) {
                try{
                    JSONArray data = (JSONArray) response;
                    for(int i = 0; i < data.length(); i++){
                        JSONObject eventData = data.getJSONObject(i);
                        Event e = DataParsing.EventFromJSON(eventData);
                        if(e == null){ throw new Exception();}
                        events.add(e);
                        eventListAdapter.notifyDataSetChanged();
                    }
                } catch (Exception E) {
                    System.out.println(E);
                    Toast.makeText(context, "Recent events not formatted correctly.", duration).show();
                }
                Toast.makeText(context, "Recent events loaded successfully.", duration).show();
            }
        };
        JsonArrayRequest req = Requests.getRecentEvents(10,listener);
        VolleyHandler.getInstance(context).addToRequestQueue(req);
    }

    private void searchEvents(){
        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_LONG;
        String query = searchView.getQuery().toString();

        VolleyResponseListener listener = new VolleyResponseListener(){
            @Override
            public void onError(String message) {
                System.out.println(message);
                Toast.makeText(context, "Events could not be loaded.", duration).show();
            }

            @Override
            public void onResponse(Object response) {
                try{
                    while (!events.isEmpty()) {
                        events.remove(0);
                    }
                    JSONArray data = (JSONArray) response;
                    for(int i = 0; i < data.length(); i++){
                        JSONObject eventData = data.getJSONObject(i);
                        Event e = DataParsing.EventFromJSON(eventData);
                        if(e == null){ throw new Exception();}
                        events.add(e);
                        eventListAdapter.notifyDataSetChanged();
                    }
                } catch (Exception E) {
                    System.out.println(E);
                    Toast.makeText(context, "Events not formatted correctly.", duration).show();
                }
                Toast.makeText(context, "Search complete.", duration).show();
            }
        };
        Toast.makeText(context, "Searching...", duration).show();
        JsonObjectRequest req = Requests.queryEvents(query,listener);
        VolleyHandler.getInstance(context).addToRequestQueue(req);
    }
}

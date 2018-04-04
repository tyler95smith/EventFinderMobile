package com.eventfinder.www.eventfindermobile;

import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.eventfinder.www.eventfindermobile.api.Requests;
import com.eventfinder.www.eventfindermobile.api.VolleyHandler;
import com.eventfinder.www.eventfindermobile.api.VolleyResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class FavoriteEventsActivity extends AppCompatActivity {
    private TabLayout tabs;
    private ViewPager viewPager;
    private FavoriteEventsPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_events);

        final Bundle dataBundle = getIntent().getExtras();
        User user = (User)dataBundle.getSerializable("user");

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

        ImageButton homebtn = (ImageButton)findViewById(R.id.home);
        ImageButton profilebtn = (ImageButton)findViewById(R.id.profile);
        ImageButton addbtn = (ImageButton)findViewById(R.id.add);
        ImageButton notbtn = (ImageButton)findViewById(R.id.notification);
        ImageButton favbtn = (ImageButton)findViewById(R.id.favorite);
        final Bundle bundle = getIntent().getExtras();

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FavoriteEventsActivity.this, HomeScreenActivity.class);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FavoriteEventsActivity.this, AddEventActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        notbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FavoriteEventsActivity.this, NotificationsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FavoriteEventsActivity.this, Profile.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        GetPastEvents();
    }

    void GetPastEvents(){
        final Context context = getApplicationContext();

        // to add a fragment a transaction, and possible a manager()? need to be created.
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        // Create listener to determine how to handle the response from the request
        VolleyResponseListener listener = new VolleyResponseListener() {
            int duration = Toast.LENGTH_SHORT;
            @Override
            public void onError(String message) {
                Toast.makeText(context, "Oops. There was an error making the request: " + message, duration).show();
            }

            @Override
            public void onResponse(Object response) {
                try {
                    JSONArray data = (JSONArray) response; // convert object to JSONArray
                    Vector<Event> eventArr = BuildEventArray(data); // convert the JSONArray into a Vector of Event Objects

                    //EventBanner tempFrag;
                    for (int i = 0; i < eventArr.size(); i++) {
                        //create a event_banner for each event
                        //tempFrag = new EventBanner();

                        //add event from array to event_banner

                        // pop event from eventArr

                        // add event_banner to past event fragment
                        //ft.add(R.id.past_tab, tempFrag, "event_banner_" + i);
                        //ft.commit(); // fails here
                    }

                    Toast.makeText(context, "The request was successful: " + eventArr.get(0).eventDate, duration).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "There was an error working with the response: " + e.toString(),duration).show();
                }
            }
        };

        // Make API request to create a new account with entered data
        JsonArrayRequest req = Requests.getPastEvents(2,listener);

        if(req != null) {
            VolleyHandler.getInstance(context).addToRequestQueue(req);
        }
    }

    Vector<Event> BuildEventArray(JSONArray data) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss"); // The date in the json is formatted as "yyyy-MM-ddThh:mm:ssZ" I've ignored the Z which does something to the timezone
            Vector<Event> eventArr = new Vector<Event>();
            JSONObject eventObj;

            for (int i = 0; i < data.length(); i++)
            {
                Event temp = new Event(); // init empty object

                // fill event object with data
                eventObj = data.getJSONObject(i);
                temp.dateCreated = df.parse(eventObj.getString("date_created"));
                temp.eventName = eventObj.getString("event_name");
                temp.location = eventObj.getString("location");
                temp.eventDate = df.parse(eventObj.getString("event_date"));
                temp.description = eventObj.getString("description");
                temp.ageMin = eventObj.getInt("age_min");
                temp.ageMax = eventObj.getInt("age_max");
                //temp.interests = eventObj.getJSONArray("interests"); // need to convert JSONArray to ArrayList
                //temp.attendees = eventObj.getJSONArray("attendees"); // need to convert JSONArray to ArrayList
                //temp.host = eventObj.getInt("host");                  // Does this need to be a user?
                temp.isHidden = eventObj.getBoolean("is_hidden");

                // add to results
                eventArr.add(temp);
            }
            return eventArr;
        } catch (Exception e) {
            return null;
        }
    }

    // This function needs to be modified somehow to convert based on class type ie user vs interests
     <T> ArrayList<T> JsonArrayToArrayList(Class<T> classType, JSONArray data) {
        ArrayList<T> list = new ArrayList<T>();
        try {
            if (data != null) {
                int len = data.length();
                for (int i = 0; i < len; i++) {
                    list.add((T)data.get(i));
                }
            }
            return list;
        } catch (JSONException e) {
            return null;
        }
    }
}

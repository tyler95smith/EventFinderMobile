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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class FavoriteEventsActivity extends AppCompatActivity implements EventBanner.OnHeadlineSelectedListener {
    private TabLayout tabs;
    private ViewPager viewPager;
    private FavoriteEventsPagerAdapter pagerAdapter;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_events);

        pagerAdapter = new FavoriteEventsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the FavoriteEventsPagerAdapter
        viewPager = (ViewPager)findViewById(R.id.fav_event_viewpager);
        viewPager.setOffscreenPageLimit(3);
        setupViewPager(viewPager);

        tabs = (TabLayout) findViewById(R.id.fav_event_tablayout);
        tabs.setupWithViewPager(viewPager);

        bundle = getIntent().getExtras();

        User user = (User)bundle.getSerializable("me");

        if (user != null) {
            //GetEvents(user.id);
            GetPastEvents(user.id);
            GetFutureEvents(user.id);
            GetMyEvents(user.id);
        } else {
            //make error toast
        }

/*
        TextView testText = (TextView)findViewById(R.id.testText);
        testText.setText(String.valueOf(user.id));
*/
        // once the user logged in is being passed around switch to the above code
        //int testId = 2;
        //GetPastEvents(testId);
        //GetFutureEvents(testId);
        //GetMyEvents(testId);
/*
        // code to ver
        LinearLayout linearLayout = findViewById(R.id.my_events_list);
        TextView testView = new TextView(this);
        testView.setText("IS THIS SHOWING UP?");
        linearLayout.addView(testView);
*/

        ImageButton homebtn = (ImageButton)findViewById(R.id.home);
        ImageButton profilebtn = (ImageButton)findViewById(R.id.profile);
        ImageButton addbtn = (ImageButton)findViewById(R.id.add);
        ImageButton notbtn = (ImageButton)findViewById(R.id.notification);
        ImageButton favbtn = (ImageButton)findViewById(R.id.favorite);
        //final Bundle bundle = getIntent().getExtras();

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FavoriteEventsActivity.this, HomeScreenActivity.class);
                intent.putExtras(bundle);
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
    }

    public void onArticleSelected(Event event) {
        Intent intent = new Intent(FavoriteEventsActivity.this, ViewEventActivity.class);
        bundle.putSerializable("event", event);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    void GetPastEvents(int userid){
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

                    Vector<Event> eventVect = BuildEventArray(data); // convert the JSONArray into a Vector of Event Objects

                    Event[] eventArr = eventVect.toArray(new Event[eventVect.size()]);
                    Intent intent = FavoriteEventsActivity.this.getIntent();
                    String prefix = "past_";
                    intent.putExtra("eventPrefix", prefix);
                    intent.putExtra(prefix + "events", eventArr);

                    EventBanner eventBanner = new EventBanner();
                    prefix = intent.getStringExtra("eventPrefix");
                    if (prefix != "past_") {
                        prefix = "past_";
                        intent.putExtra("eventPrefix", prefix);
                    }
                    ft.add(R.id.past_events_list, eventBanner, "past_event_banner");
                    ft.commit();

                    Toast.makeText(context, "The request was successful: " + eventVect.get(0).eventDate, duration).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "There was an error working with the response: " + e.toString(),duration).show();
                }
            }
        };

        // Make API request to create a new account with entered data
        JsonArrayRequest req = Requests.getPastEvents(userid,listener);

        if(req != null) {
            VolleyHandler.getInstance(context).addToRequestQueue(req);
        }
    }

    void GetFutureEvents(int userid){
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

                    Vector<Event> eventVect = BuildEventArray(data); // convert the JSONArray into a Vector of Event Objects

                    Event[] eventArr = eventVect.toArray(new Event[eventVect.size()]);
                    Intent intent = FavoriteEventsActivity.this.getIntent();
                    String prefix = "future_";
                    intent.putExtra("eventPrefix", prefix);
                    intent.putExtra(prefix + "events", eventArr);

                    EventBanner eventBanner = new EventBanner();
                    prefix = intent.getStringExtra("eventPrefix");
                    if (prefix != "future_") {
                        prefix = "future_";
                        intent.putExtra("eventPrefix", prefix);
                    }
                    ft.add(R.id.future_events_list, eventBanner, "future_event_banner");
                    ft.commit();

                    Toast.makeText(context, "The request was successful: " + eventVect.get(0).eventDate, duration).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "There was an error working with the response: " + e.toString(),duration).show();
                }
            }
        };

        // Make API request to create a new account with entered data
        JsonArrayRequest req = Requests.getFutureEvents(userid,listener);

        if(req != null) {
            VolleyHandler.getInstance(context).addToRequestQueue(req);
        }
    }

    void GetMyEvents(int userid){
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

                    Vector<Event> eventVect = BuildEventArray(data); // convert the JSONArray into a Vector of Event Objects

                    Event[] eventArr = eventVect.toArray(new Event[eventVect.size()]);
                    Intent intent = FavoriteEventsActivity.this.getIntent();
                    String prefix = "my_";
                    intent.putExtra("eventPrefix", prefix);
                    intent.putExtra(prefix + "events", eventArr);

                    EventBanner eventBanner = new EventBanner();
                    prefix = intent.getStringExtra("eventPrefix");
                    if (prefix != "my_") {
                        prefix = "my_";
                        intent.putExtra("eventPrefix", prefix);
                    }
                    ft.add(R.id.my_events_list, eventBanner, "fav_myevent_banner");
                    ft.commit();

                    Toast.makeText(context, "The request was successful: " + eventVect.get(0).eventDate, duration).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "There was an error working with the response: " + e.toString(),duration).show();
                }
            }
        };

        // Make API request to create a new account with entered data
        JsonArrayRequest req = Requests.getMyEvents(userid,listener);

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

    void printEventArray(Event[] arr) {
        TextView textView = findViewById(R.id.testText);

        String text = "";
        for (int i = 0; i < arr.length;i++)
        {
            text += " ";
            text += arr[i].eventName + ", ";
        }
        textView.setText(text);
    }

    private void setupViewPager(ViewPager viewPager){
        // https://www.youtube.com/watch?v=bNpWGI_hGGg
        FavoriteEventsPagerAdapter adapter = new FavoriteEventsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PastEventsListFragment(), "Past Events");
        adapter.addFragment(new FutureEventsListFragment(), "Future Events");
        adapter.addFragment(new MyEventsListFragment(), "My Events");
        viewPager.setAdapter(adapter);
    }
}

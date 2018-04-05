package com.eventfinder.www.eventfindermobile;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class AddEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        ImageButton homebtn = (ImageButton)findViewById(R.id.home);
        ImageButton profilebtn = (ImageButton)findViewById(R.id.profile);
        ImageButton addbtn = (ImageButton)findViewById(R.id.add);
        ImageButton notbtn = (ImageButton)findViewById(R.id.notification);
        ImageButton favbtn = (ImageButton)findViewById(R.id.favorite);
        final Bundle bundle = getIntent().getExtras();
        final EditText location = (EditText)findViewById(R.id.locationText);
        final EditText eventName = (EditText)findViewById(R.id.eventNameText);
        final EditText ageMax = (EditText)findViewById(R.id.ageMax);
        final EditText ageMin = (EditText)findViewById(R.id.ageMin);
        final EditText description = (EditText)findViewById(R.id.descriptionText);
        final EditText date = (EditText)findViewById(R.id.dateText);
        final EditText time = (EditText)findViewById(R.id.time);
        final EditText maxAttend = (EditText)findViewById(R.id.maxAttend);
        Button submit = (Button)findViewById(R.id.submitBtn);
        Button interest = (Button)findViewById(R.id.interests);
        final User user = (User)bundle.getSerializable("user");

        final FragmentManager fm = getFragmentManager();
        final InterestFragment intFrag = new InterestFragment();
        interest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intFrag.show(fm, "interests");
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                String dateOfEvent = date.getText().toString();
                String timeOfEvent = time.getText().toString();
                String dateTime = dateOfEvent + " " + timeOfEvent;
                DateValidator dv = new DateValidator();
                int duration = Toast.LENGTH_SHORT;
                if(0 >= eventName.getText().length() || eventName.getText().length() > 100) {
                    Toast.makeText(context, "Event Name is not valid", duration).show();
                } else if(0 >= location.getText().length() || location.getText().length() > 200) {
                    Toast.makeText(context, "Location is not valid", duration).show();
                } else if(dv.validate(dateOfEvent) == false || dv.inFuture(dv.returnDate(dateOfEvent)) == false) {
                    Toast.makeText(context, "Date is not valid", duration).show();
                } else if(dv.validateTime(timeOfEvent) == false || dv.getDateTime(dateTime) == null) {
                    Toast.makeText(context, "Time is not valid", duration).show();
                } else if(description.getText().length() <= 0 || description.getText().length() > 512) {
                    Toast.makeText(context, "Description is not valid", duration).show();
                } else if(ageMax.getText().length() <= 0 || Pattern.matches("[0-9]+", ageMax.getText()) == false ||
                ageMin.getText().length() <= 0 || Pattern.matches("[0-9]+", ageMin.getText()) == false) {
                    Toast.makeText(context, "Invalid Age Range", duration).show();
                } else if(Integer.parseInt(ageMax.getText().toString()) < Integer.parseInt(ageMin.getText().toString())) {
                    Toast.makeText(context, "Min age is Greater than Max age", duration).show();
                } else if(Pattern.matches("[0-9]+", maxAttend.getText()) == false && maxAttend.getText().toString().length() > 0) {
                    Toast.makeText(context, "Max Attendees is not valid", duration).show();
                }else {
                    Event event = new Event();
                    event.eventName = eventName.getText().toString();
                    event.ageMax = Integer.parseInt(ageMax.getText().toString());
                    event.ageMin = Integer.parseInt(ageMin.getText().toString());
                    event.dateCreated = Calendar.getInstance().getTime();
                    event.eventDate = dv.getDateTime(dateTime);
                    event.description = description.getText().toString();
                    event.host = user;
                    event.attendees.add(user);
                    if(maxAttend.getText().toString().length() > 0) {
                        event.maxAttendees = Integer.parseInt(maxAttend.getText().toString());
                    }
                    //add event to database
                    bundle.putSerializable("event", event);
                    Intent intent = new Intent(AddEventActivity.this, ViewEventActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEventActivity.this, HomeScreenActivity.class);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });

        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEventActivity.this, Profile.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        notbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEventActivity.this, NotificationsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        favbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEventActivity.this, FavoriteEventsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}

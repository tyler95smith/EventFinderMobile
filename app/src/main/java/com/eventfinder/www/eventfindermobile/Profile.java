package com.eventfinder.www.eventfindermobile;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        String url = "https://api.nthbox.com/api";

        ImageButton homebtn = (ImageButton)findViewById(R.id.home);
        ImageButton profilebtn = (ImageButton)findViewById(R.id.profile);
        ImageButton addbtn = (ImageButton)findViewById(R.id.add);
        ImageButton notbtn = (ImageButton)findViewById(R.id.notification);
        ImageButton favbtn = (ImageButton)findViewById(R.id.favorite);

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, HomeScreenActivity.class));
            }
        });

        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, Profile.class));
            }
        });

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, AddEventActivity.class));
            }
        });

        notbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, NotificationsActivity.class));
            }
        });

        favbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, FavoriteEventsActivity.class));
            }
        });

        final Button edit = (Button)findViewById(R.id.editButton);
        final EditText name = (EditText)findViewById(R.id.NameBox);
        final EditText username = (EditText)findViewById(R.id.UsernameBox);
        final EditText email = (EditText)findViewById(R.id.EmailBox);
        final Button addInt = (Button)findViewById(R.id.addInterests);

        addInt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new InterestFragment();
                newFragment.show(getFragmentManager(), "interests");
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edit.getText() == "Edit Profile") {
                    name.setEnabled(true);
                    username.setEnabled(true);
                    email.setEnabled(true);
                    addInt.setVisibility(View.VISIBLE);
                    edit.setText("Submit");
                } else {
                    edit.setText("Edit Profile");
                    username.setEnabled(false);
                    email.setEnabled(false);
                    addInt.setVisibility(View.GONE);
                    name.setEnabled(false);
                }
            }
        });






    }
}

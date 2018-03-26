package com.eventfinder.www.eventfindermobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ImageButton homebtn = (ImageButton)findViewById(R.id.home);
        ImageButton profilebtn = (ImageButton)findViewById(R.id.profile);
        ImageButton addbtn = (ImageButton)findViewById(R.id.add);
        ImageButton notbtn = (ImageButton)findViewById(R.id.notification);
        ImageButton favbtn = (ImageButton)findViewById(R.id.favorite);
        final Button edit = (Button)findViewById(R.id.editButton);
        final EditText name = (EditText)findViewById(R.id.NameBox);
        final Button addInt = (Button)findViewById(R.id.addInterests);
        final EditText about = (EditText)findViewById(R.id.aboutMe);
        final Button pass = (Button)findViewById(R.id.changePass);
        final InterestFragment newFragment = new InterestFragment();
        final Bundle bundle = getIntent().getExtras();
        User user = (User)bundle.getSerializable("user");
        //about.setText(user.bio);
        String fullName = user.firstName + " " + user.lastName;
        //name.setText(fullName);
        EditText username = (EditText)findViewById(R.id.UsernameBox);
        //username.setText(user.username);
        TextView gender = (TextView)findViewById(R.id.GenderBox);
        //gender.setText(user.gender);
        TextView age = (TextView)findViewById(R.id.AgeBox);
        long years = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate now = LocalDate.now();
            years = ChronoUnit.YEARS.between(now, user.dateOfBirth);
        }
        //age.setText((int) years);
        EditText email = (EditText)findViewById(R.id.EmailBox);
        email.setText(user.email);



        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, HomeScreenActivity.class);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, AddEventActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        notbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, NotificationsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        favbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, FavoriteEventsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!name.isEnabled()) {
                    addInt.setVisibility(VISIBLE);
                    pass.setVisibility(VISIBLE);
                    name.setEnabled(true);
                    about.setEnabled(true);
                    edit.setText("Submit");
                } else {
                    addInt.setVisibility(GONE);
                    pass.setVisibility(VISIBLE);
                    name.setEnabled(false);
                    about.setEnabled(false);
                    edit.setText("Edit Profile");
                }
            }
        });
    }
}

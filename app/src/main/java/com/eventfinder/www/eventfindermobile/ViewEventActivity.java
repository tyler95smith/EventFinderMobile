package com.eventfinder.www.eventfindermobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        Bundle bundle = getIntent().getExtras();
        User user = (User)bundle.getSerializable("user");
        Event event = (Event)bundle.getSerializable("event");

        if(event != null)
        {

        }

        final Button report = (Button)findViewById(R.id.reportEvent);
        if(event.host == user) {
            report.setVisibility(View.GONE);
        }








    }
}

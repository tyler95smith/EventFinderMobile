package com.eventfinder.www.eventfindermobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ViewEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        Bundle bundle = getIntent().getExtras();
        User user = (User)bundle.getSerializable("user");
        Event event = (Event)bundle.getSerializable("event");
        final TextView name = (TextView)findViewById(R.id.eventName);
        final TextView when = (TextView)findViewById(R.id.Date);
        final TextView time = (TextView)findViewById(R.id.Time);
        final TextView where = (TextView)findViewById(R.id.Place);
        final MultiAutoCompleteTextView des = (MultiAutoCompleteTextView)findViewById(R.id.description);
        final Button request = (Button)findViewById(R.id.request);
        final Button message = (Button)findViewById(R.id.message);

        if(event != null)
        {
            name.setText(event.eventName);
            String date = event.eventDate.toString();
            when.setText(date);
            time.setText(event.time);
            where.setText(event.location);
            des.setText(event.description);
        }

        final Button report = (Button)findViewById(R.id.reportEvent);
        if(event.host == user) {
            report.setVisibility(View.GONE);
        }








    }
}

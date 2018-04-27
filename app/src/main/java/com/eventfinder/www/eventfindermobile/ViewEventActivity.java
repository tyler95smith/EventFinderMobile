package com.eventfinder.www.eventfindermobile;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;
import com.eventfinder.www.eventfindermobile.api.DataParsing;
import com.eventfinder.www.eventfindermobile.api.EventFinderAPI;
import com.eventfinder.www.eventfindermobile.api.Requests;
import com.eventfinder.www.eventfindermobile.api.VolleyHandler;
import com.eventfinder.www.eventfindermobile.api.VolleyResponseListener;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static com.eventfinder.www.eventfindermobile.api.DataParsing.ConversationDataFromJSON;

public class ViewEventActivity extends AppCompatActivity {
    Event event;
    User user;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        bundle = getIntent().getExtras();
        user = (User)bundle.getSerializable("me");
        event = (Event)bundle.getSerializable("event");
        final EditText name = (EditText) findViewById(R.id.eventName);
        final EditText when = (EditText)findViewById(R.id.Date);
        final EditText time = (EditText)findViewById(R.id.Time);
        final EditText where = (EditText)findViewById(R.id.Place);
        final MultiAutoCompleteTextView des = (MultiAutoCompleteTextView)findViewById(R.id.description);
        final Button request = (Button)findViewById(R.id.request);
        final Button message = (Button)findViewById(R.id.message);
        final Button edit = (Button)findViewById(R.id.edit);
        System.out.println("EVENT ID: " + event.id);
        if(event != null)
        {
            name.setText(event.eventName);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String date = format.format(event.eventDate);
            when.setText(date);
            time.setText(event.time);
            where.setText(event.location);
            des.setText(event.description);
        }

        final Button report = (Button)findViewById(R.id.reportEvent);
        if(event.host != null) {
            if (event.host.id == user.id) {
                report.setVisibility(View.GONE);
                edit.setVisibility(View.VISIBLE);
                request.setVisibility(View.GONE);
                message.setVisibility(View.GONE);
            }
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!name.isEnabled()) {
                    edit.setText("Submit");
                    name.setEnabled(true);
                    when.setEnabled(true);
                    where.setEnabled(true);
                    time.setEnabled(true);
                    des.setEnabled(true);
                } else {
                    updateEvent();
                    edit.setText("Edit\nDetails");
                    name.setEnabled(false);
                    when.setEnabled(false);
                    where.setEnabled(false);
                    time.setEnabled(false);
                    des.setEnabled(false);
                }
            }
        });

        message.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                newConversationRequest(event.id, user.id);
            }
        });

        request.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                sendRequest(user,event);
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putSerializable("reportEvent", event);
                getIntent().putExtras(bundle);
                DialogFragment reportFrag = new ReportFragment();
                reportFrag.show(getSupportFragmentManager(), "Report");
            }
        });
    }

    private HashMap<String, String> getParams() {
        HashMap<String, String> params = new HashMap();
        EditText name = (EditText) findViewById(R.id.eventName);
        EditText when = (EditText)findViewById(R.id.Date);
        EditText time = (EditText)findViewById(R.id.Time);
        String dateTime = when.getText() + " " + time.getText();
        DateValidator valid = new DateValidator();
        Date date = valid.getCorrectFormat(dateTime);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        EditText where = (EditText)findViewById(R.id.Place);
        MultiAutoCompleteTextView des = (MultiAutoCompleteTextView)findViewById(R.id.description);
        params.put("id", String.valueOf(event.id));
        params.put("event_name", name.getText().toString());
        params.put("event_date", format.format(date));
        params.put("location", where.getText().toString());
        params.put("description", des.getText().toString());
        return params;
    }

    private void updateEvent() {
        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;
        VolleyResponseListener listener = new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(context, "There was an error", duration).show();
            }

            @Override
            public void onResponse(Object response) {
                Toast.makeText(context, "Your event has been updated", duration).show();
            }
        };

        JsonObjectRequest req = Requests.updateEvent(getParams(), listener);

        if(req != null) {
            VolleyHandler.getInstance(context).addToRequestQueue(req);
        }
    }

    private void sendRequest(User u, Event e) {
        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;

        HashMap<String,String> params = new HashMap();
        params.put("event", String.valueOf(e.id));
        params.put("requester", String.valueOf(u.id));

        VolleyResponseListener listener = new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(context, message, duration).show();
            }

            @Override
            public void onResponse(Object response) {
                Toast.makeText(context, "RSVP successfully sent", duration).show();
            }
        };

        JsonObjectRequest req = Requests.SendRsvpRequest(params,listener);

        if(req != null) {
            VolleyHandler.getInstance(context).addToRequestQueue(req);
        }
    }

    private void newConversationRequest(int eventID, int userID) {
        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;
        VolleyResponseListener listener = new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(context, message, duration).show();
            }

            @Override
            public void onResponse(Object response) {
                Toast.makeText(context, "Conversation successfully created.", duration).show();
                JSONObject convJSON = (JSONObject)response;
                Conversation conversation = DataParsing.ConversationDataFromJSON(convJSON);
                bundle.putSerializable("conversation", conversation);
                Intent intent = new Intent(ViewEventActivity.this, ChatActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        };
        JsonObjectRequest req = Requests.createConversation(eventID, userID, listener);
        if(req != null) {
            VolleyHandler.getInstance(context).addToRequestQueue(req);
        }
    }
}

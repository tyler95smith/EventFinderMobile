package com.eventfinder.www.eventfindermobile;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ViewEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        final Bundle bundle = getIntent().getExtras();
        User user = (User)bundle.getSerializable("user");
        final Event event = (Event)bundle.getSerializable("event");
        final EditText name = (EditText) findViewById(R.id.eventName);
        final EditText when = (EditText)findViewById(R.id.Date);
        final EditText time = (EditText)findViewById(R.id.Time);
        final EditText where = (EditText)findViewById(R.id.Place);
        final MultiAutoCompleteTextView des = (MultiAutoCompleteTextView)findViewById(R.id.description);
        final Button request = (Button)findViewById(R.id.request);
        final Button message = (Button)findViewById(R.id.message);
        final Button edit = (Button)findViewById(R.id.edit);

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
            edit.setVisibility(View.VISIBLE);
            request.setVisibility(View.GONE);
            message.setVisibility(View.GONE);
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
                    edit.setText("Edit\nDetails");
                    name.setEnabled(false);
                    when.setEnabled(false);
                    where.setEnabled(false);
                    time.setEnabled(false);
                    des.setEnabled(false);
                }
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
}

package com.eventfinder.www.eventfindermobile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class EventListAdapter extends BaseAdapter {
    private Context context;
    private List<Event> events;
    private DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");

    public EventListAdapter(Context context, List<Event> eventList) {
        this.context = context;
        this.events = eventList;
    }

    @Override
    public int getCount() {
        if(events == null) return 0;
        return events.size();
    }

    @Override
    public Object getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        Event e = events.get(position);
        convertView = mInflater.inflate(R.layout.fragment_event_banner, null);

        TextView eventName = (TextView) convertView.findViewById(R.id.eventName);
        TextView eventLocation = (TextView) convertView.findViewById(R.id.location);
        TextView eventTime = (TextView) convertView.findViewById(R.id.dateTime);

        eventName.setText(e.eventName);
        eventTime.setText(df.format(e.eventDate));
        eventLocation.setText(e.location);

        return convertView;
    }

}

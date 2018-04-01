package com.eventfinder.www.eventfindermobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by redre on 4/1/2018.
 */

public class EventBanner extends ListFragment {

    Event[] events;
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
        //pull 10 events and add them to Event[] events
        for(int i = 0; i < events.length; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            Event temp = events[i];
            hm.put("eventName", temp.eventName);
            hm.put("location", temp.location);
            String date = df.format(temp.eventDate);
            hm.put("date", date);
            aList.add(hm);
        }

        String[] from = { "eventName", "location", "date" };
        int[] to = {R.id.eventName, R.id.location, R.id.dateTime};

        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.fragment_event_banner, from, to);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        Bundle clicked = new Bundle();
        clicked.putSerializable("event", events[position]);

        Intent intent = getActivity().getIntent();
        intent.putExtras(clicked);
    }
}

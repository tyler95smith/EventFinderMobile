package com.eventfinder.www.eventfindermobile;

import android.app.Activity;
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
    Event event = new Event();
    Event[] events = new Event[] {
            event
    };
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
        //pull 10 events and add them to Event[] events
        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        //Event[] event = bundle.getSerializable("events");


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

    OnHeadlineSelectedListener mCallback;

    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(Event event);
    }

    Intent intent;
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        intent = getActivity().getIntent();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnHeadlineSelectedListener)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        mCallback.onArticleSelected(events[position]);
    }
}

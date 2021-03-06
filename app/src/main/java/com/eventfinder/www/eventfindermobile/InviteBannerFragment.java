package com.eventfinder.www.eventfindermobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;


public class InviteBannerFragment extends ListFragment {
    Notification notification;
    Notification[] notifs = new Notification[] {
            notification
    };

    private Button btnAccept;
    private Button btnDecline;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
        Intent intent = getActivity().getIntent();
        btnAccept = view.findViewById(R.id.btnAccept);
        btnDecline = view.findViewById(R.id.btnDecline);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // make api call setting the rsvp to accepted
                Toast.makeText(getActivity(), "You clicked Accept",Toast.LENGTH_SHORT).show();
            }
        });

        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // make api call setting the rsvp to declined
                Toast.makeText(getActivity(), "You clicked Decline",Toast.LENGTH_SHORT).show();
            }
        });

        for(int i = 0; i < notifs.length; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            Notification temp = notifs[i];
            hm.put("eventName", temp.event.eventName);
            String name = temp.user.firstName + " " + temp.user.lastName + "has requested an invite to:";
            hm.put("extra", name);
            aList.add(hm);
        }

        String[] from = { "event", "extra" };
        int[] to = {R.id.event, R.id.name};

        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.fragment_invitebanner, from, to);
        setListAdapter(adapter);
        return view;
    }

    OnHeadlineSelectedListener mCallback;

    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(Notification not);
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
        mCallback.onArticleSelected(notifs[position]);
    }
}

package com.eventfinder.www.eventfindermobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.eventfinder.www.eventfindermobile.api.DataParsing;
import com.eventfinder.www.eventfindermobile.api.Requests;
import com.eventfinder.www.eventfindermobile.api.VolleyHandler;
import com.eventfinder.www.eventfindermobile.api.VolleyResponseListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//==================================================================
//
//  IMPORTANT: This is just a temporary fragment as a placeholder
//  of sorts. This will actually need to be implemented as a
//  ListFragment.
//
//==================================================================

public class NotificationsListFragment extends Fragment{
    private List<Notification> notifications;
    private ListView notificiationsListView;
    private NotificationsListAdapter adapter;
    private User user;
    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notifications_list, container, false);
        Intent intent = getActivity().getIntent();
        bundle = intent.getExtras();
        notifications = new ArrayList<>();

        loadNotifications();

        user = (User)bundle.getSerializable("me");

        notificiationsListView = (ListView) rootView.findViewById(R.id.notifications_list_view);
        adapter = new NotificationsListAdapter(getContext(), notifications, user);
        notificiationsListView.setAdapter(adapter);

        return rootView;
    }
    private void addNotification(Notification n){
        notifications.add(n);
        adapter.notifyDataSetChanged();
    }
    private void loadNotifications(){
        final Context context = getContext();
        final int duration = Toast.LENGTH_SHORT;

        VolleyResponseListener listener = new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(context, message, duration).show();
            }

            @Override
            public void onResponse(Object response) {
                try {
                    JSONObject notifsJSON = (JSONObject) response;
                    JSONArray messageData = notifsJSON.getJSONArray("notifications");
                    JSONArray rsvpData = notifsJSON.getJSONArray("rsvps");
                    for(int i =0; i <rsvpData.length(); i++){
                        Notification n = DataParsing.NotificationFromRsvpJSON(rsvpData.getJSONObject(i));
                        notifications.add(n);
                    }
                    for (int i=0; i < messageData.length(); i++){
                        Notification n = DataParsing.NotificationFromNotifJSON(messageData.getJSONObject(i));
                        notifications.add(n);
                    }
                } catch (Exception e) {
                    Toast.makeText(context, "Error loading notifications.", duration).show();
                }
            }
        };

        JsonObjectRequest req = Requests.getNotifications(listener);
        if(req != null) {
            VolleyHandler.getInstance(context).addToRequestQueue(req);
        }
    }
}

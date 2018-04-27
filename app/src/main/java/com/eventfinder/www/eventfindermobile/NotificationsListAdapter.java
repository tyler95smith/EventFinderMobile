package com.eventfinder.www.eventfindermobile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.eventfinder.www.eventfindermobile.api.Requests;

import java.util.List;

/**
 * Created by Taylor on 4/27/2018.
 */

public class NotificationsListAdapter extends BaseAdapter {
    private Context context;
    private List<Notification> notifications;
    private User currentUser;
    private TextView eventText;
    private Button acceptButton;
    private Button declineButton;
    private TextView nameText;
    private TextView messageText;


    public NotificationsListAdapter(Context context, List<Notification> notifications, User currentUser) {
        this.context = context;
        this.notifications = notifications;
        this.currentUser = currentUser;
    }

    @Override
    public int getCount() {
        if(notifications == null) return 0;
        return notifications.size();
    }

    @Override
    public Object getItem(int position) {
        return notifications.get(position);
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
        final Notification n = notifications.get(position);

        if(n.isInvite){
            convertView = mInflater.inflate(R.layout.fragment_invitebanner, null);
            eventText = (TextView)convertView.findViewById(R.id.event);
            acceptButton = (Button)convertView.findViewById(R.id.btnAccept);
            declineButton = (Button)convertView.findViewById(R.id.btnDecline);
            nameText = (TextView)convertView.findViewById(R.id.name);
            eventText.setText(n.event.eventName);
            nameText.setText(n.user.username + " has\nrequested an invite to:");
            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //accept rsvp
                }
            });
            declineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //request to decline a RSVP
                }
            });
        } else{
            convertView = mInflater.inflate(R.layout.fragment_notifbanner, null);
            messageText = (TextView)convertView.findViewById(R.id.message);
            messageText.setText(n.message);
        }

        return convertView;
    }
}

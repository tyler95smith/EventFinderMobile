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
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Taylor on 4/27/2018.
 */

public class ConversationListAdapter extends BaseAdapter {
    private Context context;
    private List<Conversation> conversations;
    private User currentUser;

    public ConversationListAdapter(Context context, List<Conversation> conversations, User currentUser) {
        this.context = context;
        this.conversations = conversations;
        this.currentUser = currentUser;
    }

    @Override
    public int getCount() {
        if(conversations == null) return 0;
        return conversations.size();
    }

    @Override
    public Object getItem(int position) {
        return conversations.get(position);
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
        Conversation c = conversations.get(position);

        convertView = mInflater.inflate(R.layout.conversation_list_item, null);

        TextView convUserName = (TextView) convertView.findViewById(R.id.conversation_username);
        TextView convEventName = (TextView) convertView.findViewById(R.id.conversation_event_name);
        TextView date = (TextView)convertView.findViewById(R.id.most_recent_msg_date);
        if(currentUser.id == c.event.host.id){
            convUserName.setText(c.guest.username);
        } else{
            convUserName.setText(c.event.host.username);
        }
        convEventName.setText(c.event.eventName);

        ChatMessage m = c.messages.get(0);
        SimpleDateFormat df = new SimpleDateFormat("MMM dd',' yyyy");
        Date d = m.m_dateSent;
        String dateString = df.format(d);
        date.setText(dateString);

        return convertView;
    }
}

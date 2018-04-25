package com.eventfinder.www.eventfindermobile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ChatMessagesListAdapter extends BaseAdapter {
    private Context context;
    private List<ChatMessage> messages;

    public ChatMessagesListAdapter(Context context, List<ChatMessage> messages) {
        this.context = context;
        this.messages = messages;
    }

    @Override
    public int getCount() {
        if(messages == null) return 0;
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
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
        ChatMessage m = messages.get(position);

        // Identifying the message owner
        if (m.isCurrentUser()) {
            convertView = mInflater.inflate(R.layout.msg_list_item_right, null);
        } else {
            convertView = mInflater.inflate(R.layout.msg_list_item_left, null);
        }

        TextView msgFromLabel = (TextView) convertView.findViewById(R.id.fromMsgLabel);
        TextView msgText = (TextView) convertView.findViewById(R.id.msgText);

        msgFromLabel.setText(m.getSenderName());
        msgText.setText(m.getText());

        return convertView;
    }

}

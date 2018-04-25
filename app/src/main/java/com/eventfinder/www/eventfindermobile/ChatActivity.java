package com.eventfinder.www.eventfindermobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.LinkedList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ChatMessagesListAdapter adapter;
    private ListView messagesListView;
    private List<ChatMessage> messagesList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messagesListView = (ListView) findViewById(R.id.list_view_messages);
        adapter = new ChatMessagesListAdapter(this, messagesList);
        messagesListView.setAdapter(adapter);

        for(int i = 0; i < 10; i++) {
            String txt = "This is message # " + i;
            ChatMessage m;
            if(i%2 == 0) {
                m = new ChatMessage("Taylor", txt, true);
            } else {
                m = new ChatMessage("Nick", txt, false);
            }
            appendMessage(m);
        }

    }

    private void appendMessage(ChatMessage m) {
        messagesList.add(m);
        adapter.notifyDataSetChanged();
    }
}

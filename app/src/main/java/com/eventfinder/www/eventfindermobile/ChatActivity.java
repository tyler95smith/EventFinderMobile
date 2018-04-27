package com.eventfinder.www.eventfindermobile;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;
import com.eventfinder.www.eventfindermobile.api.DataParsing;
import com.eventfinder.www.eventfindermobile.api.Requests;
import com.eventfinder.www.eventfindermobile.api.VolleyHandler;
import com.eventfinder.www.eventfindermobile.api.VolleyResponseListener;

import org.json.JSONObject;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ChatMessagesListAdapter adapter;
    private ListView messagesListView;
    private List<ChatMessage> messagesList = new LinkedList<>();
    private Conversation conversation;
    private Button sendButton;
    private EditText newMessageText;
    private User user;
    private User otherUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        sendButton = (Button)findViewById(R.id.msgSubmit);
        newMessageText = (EditText)findViewById(R.id.msgInput);

        //get data from bundle
        final Bundle bundle = getIntent().getExtras();
        conversation = (Conversation)bundle.getSerializable("conversation");
        if(conversation.messages == null) {
            conversation.messages = new LinkedList<>();
        }

        messagesListView = (ListView) findViewById(R.id.list_view_messages);
        adapter = new ChatMessagesListAdapter(this, conversation.messages);
        messagesListView.setAdapter(adapter);

        user = (User)bundle.getSerializable("me");

        if(user.id == conversation.event.host.id){
            conversation.event.host.me = true;
            otherUser = conversation.guest;
        } else if(user.id == conversation.guest.id){
            conversation.guest.me = true;
            otherUser = conversation.event.host;
        }

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("wtf?");
                createNewMessage();
            }
        });


        for(int i = 0; i < 10; i++) {
            String txt = "This is message # " + i;
            ChatMessage m;
            if(i%2 == 0) {
                m = new ChatMessage(user.id,user.username, txt, true, new Date(0));
            } else {
                m = new ChatMessage(otherUser.id,otherUser.username, txt, false, new Date(0));
            }
            appendMessage(m);
        }

    }

    private void appendMessage(ChatMessage m) {
        conversation.messages.add(m);
        adapter.notifyDataSetChanged();
    }

    private void createNewMessage() {
        final Context context = getApplicationContext();
        String messageText = newMessageText.getText().toString();
        final int duration = Toast.LENGTH_SHORT;

        if(messageText == null || messageText == ""){
            return;
        }
        VolleyResponseListener listener = new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(context, message, duration).show();
            }

            @Override
            public void onResponse(Object response) {
                JSONObject messageJSON = (JSONObject)response;
                ChatMessage m = DataParsing.ChatMessageFromJSON(messageJSON);
                if(user.id == m.m_senderID){
                    m.m_isCurrentUser = true;
                }
                appendMessage(m);
            }
        };

        JsonObjectRequest req = Requests.createMessage(conversation.id, messageText, listener);
        VolleyHandler.getInstance(context).addToRequestQueue(req);
    }
}

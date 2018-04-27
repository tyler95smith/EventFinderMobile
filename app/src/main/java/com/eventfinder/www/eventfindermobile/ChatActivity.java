package com.eventfinder.www.eventfindermobile;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;
import com.eventfinder.www.eventfindermobile.api.DataParsing;
import com.eventfinder.www.eventfindermobile.api.Requests;
import com.eventfinder.www.eventfindermobile.api.VolleyHandler;
import com.eventfinder.www.eventfindermobile.api.VolleyResponseListener;

import org.json.JSONObject;
import org.w3c.dom.Text;

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
    private ConstraintLayout header;
    private TextView headerText;
    private User user;
    private User otherUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        sendButton = (Button)findViewById(R.id.msgSubmit);
        newMessageText = (EditText)findViewById(R.id.msgInput);
        headerText = (TextView)findViewById(R.id.event_name);


        //get data from bundle
        final Bundle bundle = getIntent().getExtras();
        user = (User)bundle.getSerializable("me");
        conversation = (Conversation)bundle.getSerializable("conversation");
        if(conversation.messages == null) {
            conversation.messages = new LinkedList<>();
        }else{
            for(ChatMessage message : conversation.messages){
                if(message.m_senderID == user.id){
                    message.m_isCurrentUser = true;
                }
            }
        }

        headerText.setText("Event: " + conversation.event.eventName);

        messagesListView = (ListView) findViewById(R.id.list_view_messages);
        adapter = new ChatMessagesListAdapter(this, conversation.messages);
        messagesListView.setAdapter(adapter);

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

        newMessageText.setEnabled(false);
        newMessageText.clearFocus();
        Toast.makeText(context, "Sending message...", duration).show();

        VolleyResponseListener listener = new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                newMessageText.setEnabled(true);
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
                Toast.makeText(context, "Message sent.", duration).show();
                newMessageText.setText("");
                newMessageText.setEnabled(true);
            }
        };

        JsonObjectRequest req = Requests.createMessage(conversation.id, messageText, listener);
        VolleyHandler.getInstance(context).addToRequestQueue(req);
    }
}

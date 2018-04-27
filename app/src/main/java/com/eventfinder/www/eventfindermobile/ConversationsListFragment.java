package com.eventfinder.www.eventfindermobile;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonArrayRequest;
import com.eventfinder.www.eventfindermobile.api.DataParsing;
import com.eventfinder.www.eventfindermobile.api.Requests;
import com.eventfinder.www.eventfindermobile.api.VolleyHandler;
import com.eventfinder.www.eventfindermobile.api.VolleyResponseListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ConversationsListFragment extends Fragment{
    private List<Conversation> conversations;
    private ListView conversationsListView;
    private ConversationListAdapter adapter;
    private User user;
    private Bundle bundle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_conversations_list, container, false);
        Intent intent = getActivity().getIntent();
        bundle = intent.getExtras();
        conversations = new ArrayList<>();
        loadConversations();
        user = (User)bundle.getSerializable("me");

        conversationsListView = (ListView) rootView.findViewById(R.id.list_view_conversations);
        adapter = new ConversationListAdapter(getContext(), conversations, user);
        conversationsListView.setAdapter(adapter);

        conversationsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Conversation c = (Conversation) conversations.get(position);
                bundle.putSerializable("conversation", c);
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return rootView;
    }

    private void addConversation(Conversation c){
        conversations.add(c);
        adapter.notifyDataSetChanged();
    }

    private void loadConversations(){
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
                    JSONArray convJSON = (JSONArray) response;
                    for (int i = 0; i < convJSON.length(); i++) {
                        JSONObject cData = convJSON.getJSONObject(i);
                        Conversation conversation = DataParsing.ConversationDataFromJSON(cData);
                        addConversation(conversation);
                    }
                } catch (Exception e) {
                    Toast.makeText(context, "Error loading conversations.", duration).show();
                }
            }
        };

        JsonArrayRequest req = Requests.getConversations(listener);
        if(req != null) {
            VolleyHandler.getInstance(context).addToRequestQueue(req);
        }
    }

    //---------------------------------------------------------------------
    // TO DO: implement Static factory method for creating new instances
    // of this fragment
    //      NOTE: need to understand this more before implementing.
    //            Search the web for "fragment factory android"
    //---------------------------------------------------------------------
}

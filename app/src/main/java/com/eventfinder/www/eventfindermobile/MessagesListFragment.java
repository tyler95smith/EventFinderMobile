package com.eventfinder.www.eventfindermobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

//==================================================================
//
//  IMPORTANT: This is just a temporary fragment as a placeholder
//  of sorts. This will actually need to be implemented as a
//  ListFragment.
//
//==================================================================
public class MessagesListFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_messages_list, container, false);

        Button btn=(Button)rootView.findViewById(R.id.m1);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtras(savedInstanceState);
                startActivity(intent);
            }
        });

        return rootView;
    }

    //---------------------------------------------------------------------
    // TO DO: implement Static factory method for creating new instances
    // of this fragment
    //      NOTE: need to understand this more before implementing.
    //            Search the web for "fragment factory android"
    //---------------------------------------------------------------------
}

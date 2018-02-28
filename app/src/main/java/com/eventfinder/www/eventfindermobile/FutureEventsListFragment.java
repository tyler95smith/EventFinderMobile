package com.eventfinder.www.eventfindermobile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//==================================================================
//
//  IMPORTANT: This is just a temporary fragment as a placeholder
//  of sorts. This will actually need to be implemented as a
//  ListFragment.
//
//==================================================================

public class FutureEventsListFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_messages_list, container, false);
    }

    //---------------------------------------------------------------------
    // Static factory method for creating new instances of this fragment.\
    //      NOTE: need to understand this more before implementing.
    //          Search the web for "fragment factory android"
    //---------------------------------------------------------------------
    /*
    public static NotificationsListFragment newInstance(int position) {
        NotificationsListFragment f = new NotificationsListFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        f.setArguments(args);
        return f;
    }
    */
}

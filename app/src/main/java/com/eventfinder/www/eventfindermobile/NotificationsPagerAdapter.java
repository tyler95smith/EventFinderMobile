package com.eventfinder.www.eventfindermobile;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

//===================================================================
//
//  FragmentPagerAdapter subclass for use with the viewpager
//  used to determine which Fragment to display based on tabbed
//  position (notifications fragment/messages fragment).
//
//===================================================================
public class NotificationsPagerAdapter extends FragmentPagerAdapter{
    private static final int TAB_COUNT = 2;
    private static final int NOTIFICATIONS_TAB_POS = 0;
    //Do not need MESSAGES_TAB_POS with current logic

    //---------------------------------------------------------------
    //  Constructor
    //---------------------------------------------------------------
    public NotificationsPagerAdapter(FragmentManager fm){
        super(fm);
    }

    //---------------------------------------------------------------
    //  Determines the corresponding fragment for each position(tab)
    //---------------------------------------------------------------
    @Override
    public Fragment getItem(int position) {
        if(position == NOTIFICATIONS_TAB_POS) {
            return new NotificationsListFragment();
        } else {
            return new MessagesListFragment();
        }
    }

    @Override
    public int getCount() {
        return(TAB_COUNT);
    }

}

package com.eventfinder.www.eventfindermobile;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

//===================================================================
//
//  FragmentPagerAdapter subclass for use with the viewpager
//  used to determine which Fragment to display based on tabbed
//  position (notifications fragment/messages fragment).
//
//===================================================================
public class FavoriteEventsPagerAdapter extends FragmentPagerAdapter{
    //private static final int TAB_COUNT = 3;
    //private static final int PAST_TAB_POS = 0;
    //private static final int FUTURE_TAB_POS=1;
    //private static final int MY_TAB_POS=2;
    //Do not need MESSAGES_TAB_POS with current logic

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    //---------------------------------------------------------------
    //  Constructor
    //---------------------------------------------------------------
    public FavoriteEventsPagerAdapter(FragmentManager fm){
        super(fm);
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
    @Override
    public Fragment getItem(int position) {
        /*
        if(position == PAST_TAB_POS) {
            return new PastEventsListFragment();
        } else if(position == FUTURE_TAB_POS) {
            return new FutureEventsListFragment();
        } else {
            return new MyEventsListFragment();
        }
        */
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

}

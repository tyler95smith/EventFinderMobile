package com.eventfinder.www.eventfindermobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;

public class InterestFragment extends DialogFragment {
    CharSequence[] ints;
    int numbOfInt = 72;
    ArrayList<String> selectInt;
    boolean[] checked = new boolean[numbOfInt];
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ints = new CharSequence[numbOfInt];
        ints[0] = "Acting";
        ints[1] = "Badmitton";
        ints[2] = "Band";
        ints[3] = "Baseball";
        ints[4] = "Basketball";
        ints[5] = "Bird Watching";
        ints[6] = "Billiards";
        ints[7] = "Board Games";
        ints[8] = "Boating";
        ints[9] = "Bowling";
        ints[10] = "Boxing";
        ints[11] = "Camping";
        ints[12] = "Chess";
        ints[13] = "Color Guard";
        ints[14] = "Cooking";
        ints[15] = "Dancing";
        ints[16] = "Darts";
        ints[17] = "Debate";
        ints[18] = "Drawing";
        ints[19] = "Fencing";
        ints[20] = "Fishing";
        ints[21] = "Football";
        ints[22] = "Gardening";
        ints[23] = "Ghost Hunting";
        ints[24] = "Golf";
        ints[25] = "Gymnastics";
        ints[26] = "Hiking";
        ints[27] = "Hockey";
        ints[28] = "Horseback Riding";
        ints[29] = "Hunting";
        ints[30] = "Ice Skating";
        ints[31] = "Kayaking";
        ints[32] = "Knitting";
        ints[33] = "LARPing";
        ints[34] = "Laser Tag";
        ints[35] = "Martial Arts";
        ints[36] = "Mountain Biking";
        ints[37] = "Orchestra";
        ints[38] = "Paintballing";
        ints[39] = "Painting";
        ints[40] = "Parkour";
        ints[41] = "Photography";
        ints[42] = "Poker";
        ints[43] = "Powerlifting";
        ints[44] = "Pottery";
        ints[45] = "Programming";
        ints[46] = "Rafting";
        ints[47] = "Rappelling";
        ints[48] = "Reading";
        ints[49] = "Rock Climbing";
        ints[50] = "Roller Skating";
        ints[51] = "Running";
        ints[52] = "Scuba Diving";
        ints[53] = "Sewing";
        ints[54] = "Shooting";
        ints[55] = "Shopping";
        ints[56] = "Singing";
        ints[57] = "Skateboarding";
        ints[58] = "Skiing";
        ints[59] = "Skydiving";
        ints[60] = "Snowboarding";
        ints[61] = "Soccer";
        ints[62] = "Surfing";
        ints[63] = "Swimming";
        ints[64] = "Traveling";
        ints[65] = "Video Games";
        ints[66] = "Volleyball";
        ints[67] = "Watching Movies";
        ints[68] = "Wood Carving";
        ints[69] = "Wrestling";
        ints[70] = "Writing";
        ints[71] = "Yoga";
        if(selectInt == null) {
            selectInt = new ArrayList<>();
            Arrays.fill(checked, false);
        }
            Intent intent = getActivity().getIntent();
            Bundle bundle = intent.getExtras();
            if(bundle.containsKey("event")) {
                Event event = (Event)bundle.getSerializable("event");
            } else {
                User user = (User) bundle.getSerializable("user");
                if(user.hasInterests == true) {
                    selectInt = user.interests;
                    for (int i = 0; i < numbOfInt; i++) {
                        if (selectInt.contains(ints[i])) {
                            checked[i] = true;
                        }
                    }
                }
            }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Interests:")
                .setMultiChoiceItems(ints, checked, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if(b) {
                            selectInt.add((String)ints[i]);
                        } else if(selectInt.contains((String)ints[i])) {
                            selectInt.remove((String)ints[i]);
                        }
                    }
                })
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(int j = 0; j < numbOfInt; j++) {
                            if(selectInt.contains((String)ints[j])) {
                                checked[j] = true;
                            }
                        }
                        mListener.onDialogPositiveClick(InterestFragment.this, selectInt);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(int j = 0; j < numbOfInt; j++) {
                            if(checked[j] == false) {
                                if(selectInt.contains((String)ints[j])) {
                                    selectInt.remove((String)ints[j]);
                                }
                            } else {
                                if(!selectInt.contains((String)ints[j])) {
                                    selectInt.add((String)ints[j]);
                                }
                            }
                        }
                    }
                });

        return builder.create();
    }

    public interface InterestListener {
        public void onDialogPositiveClick(DialogFragment dialog, ArrayList<String> ints);
    }

    InterestListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (InterestListener)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement InterestListener");
        }
    }
}

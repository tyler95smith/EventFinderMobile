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
    InterestArray intArray = new InterestArray();
    CharSequence[] ints = intArray.ints;
    int numbOfInt = 72;
    ArrayList<Integer> selectInt;
    boolean[] checked = new boolean[numbOfInt];
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if(selectInt == null) {
            selectInt = new ArrayList<>();
            Arrays.fill(checked, false);
        }
            Intent intent = getActivity().getIntent();
            Bundle bundle = intent.getExtras();
            if(!bundle.containsKey("sendEvent")) {
                User user = (User) bundle.getSerializable("me");
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
                            selectInt.add(i);
                        } else if(selectInt.contains(i)) {
                            selectInt.remove(i);
                        }
                    }
                })
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(int j = 0; j < numbOfInt; j++) {
                            if(selectInt.contains(j)) {
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
                                    selectInt.add(j);
                                }
                            }
                        }
                    }
                });

        return builder.create();
    }

    public interface InterestListener {
        public void onDialogPositiveClick(DialogFragment dialog, ArrayList<Integer> ints);
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

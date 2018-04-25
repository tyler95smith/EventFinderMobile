package com.eventfinder.www.eventfindermobile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ReportFragment extends DialogFragment {

    public ReportFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        boolean isEvent = false;
        Event event;
        User user;
        if(bundle.containsKey("reportEvent")) {
            isEvent = true;
            event = (Event)bundle.getSerializable("reportEvent");
            bundle.remove("reportEvent");
        } else {
            user = (User)bundle.getSerializable("reportUser");
            bundle.remove("reportUser");
        }

        builder.setView(inflater.inflate(R.layout.fragment_report, null))
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //send report
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //don't send report
                    }
                });

        return builder.create();
    }
}
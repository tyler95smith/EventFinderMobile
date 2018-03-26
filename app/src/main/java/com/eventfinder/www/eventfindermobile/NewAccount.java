package com.eventfinder.www.eventfindermobile;

import com.android.volley.toolbox.JsonObjectRequest;
import com.eventfinder.www.eventfindermobile.api.EventFinderAPI;
import com.eventfinder.www.eventfindermobile.api.VolleyHandler;
import com.eventfinder.www.eventfindermobile.api.VolleyResponseListener;
import com.eventfinder.www.eventfindermobile.api.Requests;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.RadioGroup;

import java.util.HashMap;

public class NewAccount extends AppCompatActivity {
    // global error message used in a toast
    CharSequence err_text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        final ConstraintLayout mainLayout = findViewById(R.id.main_layout);

        Button accountbtn = (Button)findViewById(R.id.account);

        accountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                // context and duration used in toast
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;

                if (hasValidInput(mainLayout)) {
                    createAccount();
                    startActivity(new Intent(NewAccount.this, Login.class));
                } else { // show error messages
                    Toast.makeText(context, err_text,duration).show();
                }
            }
        });
    }

    //
    // function that validates/checks if input is empty
    // currently hardcoded to being ConstraintLayout
    private boolean hasValidInput(ConstraintLayout mainLayout)
    {
        boolean validInput = true;
        err_text = "";

        // loop through all children
        for (int i = 0; i < mainLayout.getChildCount(); i++)
        {
            // if edittext object
            if (mainLayout.getChildAt(i) instanceof EditText)
            {
                EditText child = (EditText)mainLayout.getChildAt(i);
                // if empty add to error message
                if ("".equals(child.getText().toString()))
                {
                    validInput = false;
                    err_text = err_text + child.getHint().toString() + " cannot be blank!\n";
                }
            }
            // if male/female not really sure should it be a button or toggle group?

            if (mainLayout.getChildAt(i) instanceof  RadioGroup)
            {
                RadioGroup child = (RadioGroup)mainLayout.getChildAt(i);
                RadioGroup gender = (RadioGroup)findViewById(R.id.genderRadioGroup);

                // this is a temporary ignore of the male and female fields so that we can demo
                if (child != gender) {
                    // radioGroup is empty
                    if (child.getCheckedRadioButtonId() == -1) {
                        validInput = false;
                        err_text = err_text + "An account type must be selected.\n";
                    }
                }
            }
        }
        return validInput;
    }

    //User params (username, email, password)
    private HashMap<String, String> getUserParams()
    {
        HashMap<String, String> params = new HashMap<>();
        EditText username = (EditText)findViewById(R.id.username);
        EditText email = (EditText)findViewById(R.id.email);
        EditText password = (EditText)findViewById(R.id.password);
        params.put("username",username.getText().toString());
        params.put("email", email.getText().toString());
        params.put("password", password.getText().toString());
        return params;
    }

    //All other account params other than username, email and password
    private HashMap<String, String> getAcctParams()
    {
        HashMap<String, String> params = new HashMap<String, String>();
        EditText dob = (EditText)findViewById(R.id.date_of_birth);
        params.put("date_of_birth", dob.getText().toString());
        return params;
    }


    //
    // Make the API call to create a new account with the data entered by the user.
    // Also handles the response (listener).
    private void createAccount(){
        final Context context = getApplicationContext();

        // Create listener to determine how to handle the response from the request
        VolleyResponseListener listener = new VolleyResponseListener() {
            int duration = Toast.LENGTH_SHORT;
            @Override
            public void onError(String message) {
                Toast.makeText(context, "Oops. There was an error making the request.", duration).show();
            }

            @Override
            public void onResponse(Object response) {
                //Handle JSON response... for now just shows a simple message
                Toast.makeText(context, "The request to create account was made successfully.", duration).show();
            }
        };

        // Make API request to create a new account with entered data
        JsonObjectRequest req = Requests.createPersonalAccount(getAcctParams(), getUserParams(), listener);

        if(req != null) {
            VolleyHandler.getInstance(context).addToRequestQueue(req);
        }
    }
}
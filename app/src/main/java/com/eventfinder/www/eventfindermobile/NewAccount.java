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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup;

import java.util.HashMap;

public class NewAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        // this adds TextWatch objects to the various views which allows input validation after they finish typing
        addTextInputValidators();

        final ConstraintLayout mainLayout = findViewById(R.id.main_layout);

        Button accountbtn = (Button)findViewById(R.id.account);

        accountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                // context and duration used in toast
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;

                if (isInputValid(mainLayout)) {
                    createAccount();
                    startActivity(new Intent(NewAccount.this, Login.class));
                } else { // show error messages
                    Toast.makeText(context, "Please fix the errors above!",duration).show();
                }
            }
        });
    }

    //
    // function that validates/checks if input is empty
    // currently hardcoded to being ConstraintLayout
    private boolean isInputValid(ConstraintLayout mainLayout)
    {
        // loop through all children
        for (int i = 0; i < mainLayout.getChildCount(); i++)
        {
            // if child is a editText view
            if (mainLayout.getChildAt(i) instanceof EditText)
            {
                EditText child = (EditText)mainLayout.getChildAt(i);
                // if there is an error message
                if (!("".equals(child.getError())) && child.getError() != null)
                {
                    return false;
                }
            }
            // if male/female not really sure should it be a button or toggle group?
/*
            if (mainLayout.getChildAt(i) instanceof  RadioGroup)
            {
                RadioGroup child = (RadioGroup)mainLayout.getChildAt(i);
                RadioGroup gender = (RadioGroup)findViewById(R.id.genderRadioGroup);

                // this is a temporary ignore of the male and female fields so that we can demo
                if (child != gender) {
                    // radioGroup is empty
                    if (child.getCheckedRadioButtonId() == -1) {
                        inputValid = false;
                        err_text = err_text + "An account type must be selected.\n";
                    }
                }
            }
*/
        }
        return true;
    }

    //
    // This function will run code to 'validate' the text fields after they are changed
    //
    void addTextInputValidators()
    {
        EditText name = (EditText)findViewById(R.id.name);
        EditText username = (EditText)findViewById(R.id.username);
        EditText password = (EditText)findViewById(R.id.password);
        EditText duppassword = (EditText)findViewById(R.id.password2);
        EditText email = (EditText)findViewById(R.id.email);
        EditText dob = (EditText)findViewById(R.id.date_of_birth);

        name.addTextChangedListener(new TextValidator(name) {
            @Override public void validate(TextView textView, String text) {
                if (text == null || text.isEmpty())
                {
                    textView.setError("Name cannot be blank!");
                }
            }
        });

        username.addTextChangedListener(new TextValidator(username) {
            @Override public void validate(TextView textView, String text) {
                if (text == null || text.isEmpty())
                {
                    textView.setError("Username cannot be blank!");
                }
            }
        });

        password.addTextChangedListener(new TextValidator(password) {
            @Override public void validate(TextView textView, String text) {
                if (text == null || text.isEmpty())
                {
                    textView.setError("Password cannot be blank!");
                }
            }
        });

        duppassword.addTextChangedListener(new TextValidator(duppassword) {
            @Override public void validate(TextView textView, String text) {
                if (text == null || text.isEmpty())
                {
                    textView.setError("You must re-type the password!");
                }
            }
        });

        email.addTextChangedListener(new TextValidator(email) {
            @Override public void validate(TextView textView, String text) {
                if (text == null || text.isEmpty())
                {
                    textView.setError("Email cannot be blank!");
                }
            }
        });

        dob.addTextChangedListener(new TextValidator(dob) {
            @Override public void validate(TextView textView, String text) {
                if (text == null || text.isEmpty())
                {
                    textView.setError("Date of Birth cannot be blank!");
                }
            }
        });
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
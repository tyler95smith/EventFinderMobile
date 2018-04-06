package com.eventfinder.www.eventfindermobile;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;
import com.eventfinder.www.eventfindermobile.api.Requests;
import com.eventfinder.www.eventfindermobile.api.VolleyHandler;
import com.eventfinder.www.eventfindermobile.api.VolleyResponseListener;

import java.util.HashMap;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button submitbtn = (Button)findViewById(R.id.submit);
        Button newbtn = (Button)findViewById(R.id.newaccount);

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getToken();
            }
        });

        newbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, NewAccount.class));
            }
        });
    }

    //Get the username and password
    private HashMap<String, String> getCredentials()
    {
        HashMap<String, String> params = new HashMap<String, String>();
        EditText user = (EditText)findViewById(R.id.username);
        EditText pass = (EditText)findViewById(R.id.password);
        params.put("username",user.getText().toString());
        params.put("password",pass.getText().toString());
        return params;
    }


    private void getToken()
    {
        final Context context = getApplicationContext();

        // Create listener to determine how to handle the response from the request
        VolleyResponseListener listener = new VolleyResponseListener() {
            int duration = Toast.LENGTH_SHORT;
            @Override
            public void onError(String message) {
                Toast.makeText(context, "Invalid Username or Password", duration).show();
                System.out.print(message.toString());
            }

            @Override
            public void onResponse(Object response) {
                Toast.makeText(context, "Login Successful", duration).show();
                startActivity(new Intent(Login.this, HomeScreenActivity.class));
            }
        };

        // Make API request to get a new JWT token
        JsonObjectRequest req = Requests.login(getCredentials(), listener);

        if(req != null) {
            VolleyHandler.getInstance(context).addToRequestQueue(req);
        }
    }
}

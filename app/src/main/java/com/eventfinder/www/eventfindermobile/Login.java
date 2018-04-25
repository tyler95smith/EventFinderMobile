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
import com.eventfinder.www.eventfindermobile.api.EventFinderAPI;
import com.eventfinder.www.eventfindermobile.api.Requests;
import com.eventfinder.www.eventfindermobile.api.VolleyHandler;
import com.eventfinder.www.eventfindermobile.api.VolleyResponseListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

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
                requestToken();
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


    private void requestToken()
    {
        final Context context = getApplicationContext();

        // Create listener to determine how to handle the response from the request
        VolleyResponseListener listener = new VolleyResponseListener() {
            int duration = Toast.LENGTH_SHORT;
            @Override
            public void onError(String message) {
                Toast.makeText(context, "Invalid Username or Password", duration).show();
            }

            @Override
            public void onResponse(Object response) {
                try {
                    JSONObject obj = new JSONObject(response.toString());
                    String mytoken = obj.getString("token");
                    EventFinderAPI api = new EventFinderAPI();
                    api.setToken(mytoken);
                    System.out.println("Saved Token:" + api.getToken());
                    Toast.makeText(context, "Login Successful", duration).show();
                    getUserInfo();
                } catch (Exception E) {
                    System.out.println("Not valid JSON");
                }
            }
        };

        // Make API request to get a new JWT token
        JsonObjectRequest req = Requests.login(getCredentials(), listener);

        if(req != null) {
            VolleyHandler.getInstance(context).addToRequestQueue(req);
        }
    }

    void getUserInfo() {
        final Context context = getApplicationContext();
        final Bundle bundle = new Bundle();
        VolleyResponseListener listener = new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(context, "Error.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Object response) {
                try {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    User user = new User();
                    JSONObject temp = (JSONObject) response;
                    JSONObject userPart = temp.getJSONObject("user");
                    DateValidator valid = new DateValidator();
                    Date birth = valid.returnDate(temp.getString("date_of_birth"));
                    user.lastName = userPart.getString("last_name");
                    user.firstName = userPart.getString("first_name");
                    user.hideLocation = temp.getBoolean("hideLocation");
                    user.primaryLocation = temp.getString("primaryLocation");
                    user.dateOfBirth = birth;
                    user.email = userPart.getString("email");
                    user.username = userPart.getString("username");
                    user.bio = temp.getString("bio");
                    user.me = true;
                    user.Person_ID = temp.getInt("id");
                    user.isBanned = temp.getBoolean("isBanned");
                    bundle.putSerializable("me", user);
                    Intent intent = new Intent(Login.this, HomeScreenActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }catch (Exception e) {}
            }
        };
        JsonObjectRequest req = Requests.getMyInfo(listener);

        if(req != null) {
            VolleyHandler.getInstance(context).addToRequestQueue(req);
        }
    }
}

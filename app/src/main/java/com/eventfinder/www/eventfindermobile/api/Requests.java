package com.eventfinder.www.eventfindermobile.api;
import com.eventfinder.www.eventfindermobile.api.*;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Requests:
 *  Class for generating requests to be sent to the server.
 */

public class Requests {

    public static JsonObjectRequest createPersonalAccount(HashMap<String,String> acctParams, HashMap<String, String> userParams, final VolleyResponseListener listener) {
        String url = EventFinderAPI.API_URL + "createpersonaccount/";
        try {
            JSONObject userJSON = new JSONObject(userParams);
            JSONObject acctJSON = new JSONObject(acctParams);
            acctJSON.put("user", userJSON);

            JsonObjectRequest req = new JsonObjectRequest(url, acctJSON,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            listener.onResponse(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.onError(error.toString());
                }
            });
            return req;
        }
        catch (JSONException e) { return null;}
    }

}

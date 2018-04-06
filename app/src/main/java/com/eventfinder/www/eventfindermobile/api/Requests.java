package com.eventfinder.www.eventfindermobile.api;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.eventfinder.www.eventfindermobile.Event;
import com.eventfinder.www.eventfindermobile.api.*;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Vector;

/**
 * Requests:
 *  Class for generating requests to be sent to the server.
 */

public class Requests {

    public static JsonObjectRequest createPersonalAccount(HashMap<String, String> acctParams, HashMap<String, String> userParams, final VolleyResponseListener listener) {
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
        } catch (JSONException e) {
            return null;
        }
    }

    public static JsonObjectRequest updatePersonalAccount(HashMap<String, String> acctParams, HashMap<String, String> userParams, final VolleyResponseListener listener) {
        String url = EventFinderAPI.API_URL + "updatepersonaccount/";
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
        } catch (JSONException e) {
            return null;
        }
    }

    /*
        Volley Request to get Past Events
     */
    public static JsonArrayRequest getPastEvents(int userID, final VolleyResponseListener listener) {
        String url = EventFinderAPI.API_URL + "getpastevents/?user=" + userID;

        try {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            listener.onResponse(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            listener.onError(error.toString());
                        }

                    });
            return req;
        } catch (Exception e) {
            return null;
        }
    }

    /*
       Volley Request to get Future Events
    */
    public static JsonArrayRequest getFutureEvents(int userID, final VolleyResponseListener listener) {
        String url = EventFinderAPI.API_URL + "getfutureevents/?user=" + userID;

        try {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            listener.onResponse(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            listener.onError(error.toString());
                        }

                    });
            return req;
        } catch (Exception e) {
            return null;
        }
    }

    /*
        Volley Request to get a JWT (JSON Web Token)
     */
    public static JsonObjectRequest login(HashMap<String, String> credentials, final VolleyResponseListener listener) {
        String url = EventFinderAPI.API_URL + "token/login/";

            JSONObject credentialsJSON = new JSONObject(credentials);
            JsonObjectRequest req = new JsonObjectRequest(url, credentialsJSON,
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
}
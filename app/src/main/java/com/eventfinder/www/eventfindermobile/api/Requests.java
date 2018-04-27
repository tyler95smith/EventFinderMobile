package com.eventfinder.www.eventfindermobile.api;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 * Requests:
 *  Class for generating requests to be sent to the server.
 */

public class Requests {

    //---------------------------------------------------------------------------------
    //
    //  Creates JsonObjectRequest with option to include token in request header.
    //
    //---------------------------------------------------------------------------------
    private static JsonObjectRequest createJsonObjReq(int method, String url, JSONObject json, final VolleyResponseListener listener, boolean withToken){

        //
        //Include Token in request header
        if(withToken) {
            return new JsonObjectRequest(method, url, json,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    listener.onResponse(response);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    listener.onError(error.toString());
                                }
                            }
            )
            {
                @Override
                public HashMap<String, String> getHeaders() throws AuthFailureError {
                    EventFinderAPI api = new EventFinderAPI();
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", "JWT " + api.getToken());
                    return params;
                }
            };
        }

        //
        //Request with no token in header
        return new JsonObjectRequest(method, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error.toString());
                    }
                }
        );
    }

    //---------------------------------------------------------------------------------
    //
    //  Creates JsonArrayRequest with option to include token in request header.
    //
    //---------------------------------------------------------------------------------
    private static JsonArrayRequest createJsonArrReq(int method, String url, JSONArray json, final VolleyResponseListener listener, boolean withToken){

        //
        //Include Token in request header
        if(withToken) {
            return new JsonArrayRequest(method, url, json,
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
                    }
            )
            {
                @Override
                public HashMap<String, String> getHeaders() throws AuthFailureError {
                    EventFinderAPI api = new EventFinderAPI();
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", "JWT " + api.getToken());
                    return params;
                }
            };
        }

        //
        //Request with no token in header
        return new JsonArrayRequest(method, url, json,
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
                }
        );
    }

    public static JsonObjectRequest createPersonalAccount(HashMap<String,String> acctParams, HashMap<String, String> userParams, final VolleyResponseListener listener) {
        String url = EventFinderAPI.API_URL + "createpersonaccount/";
        try {
            JSONObject userJSON = new JSONObject(userParams);
            JSONObject acctJSON = new JSONObject(acctParams);
            acctJSON.put("user", userJSON);
            return createJsonObjReq(Request.Method.POST, url, acctJSON, listener, false);
        } catch (JSONException e) {
            return null;
        }
    }

    public static JsonObjectRequest updatePersonalAccount(HashMap<String, String> acctParams, HashMap<String, String> userParams, final VolleyResponseListener listener) {
        String url = EventFinderAPI.API_URL + "updatepersonaccount/";
        try {
            JSONObject userJSON = new JSONObject(userParams);
            userJSON.put("password", "thisismandatory");
            userJSON.put("username", "changeme2");
            JSONObject acctJSON = new JSONObject(acctParams);
            acctJSON.put("user", userJSON);

            return createJsonObjReq(Request.Method.PATCH, url, acctJSON, listener, true);

        } catch (JSONException e) {
            return null;
        }
    }

    public static JsonObjectRequest SendRsvpRequest(HashMap<String, String> params, final VolleyResponseListener listener) {
        String url = EventFinderAPI.API_URL + "creatersvp/";
        JSONObject rsvpJSON = new JSONObject(params);

        return createJsonObjReq(Request.Method.POST, url, rsvpJSON, listener, true);
    }

    public static JsonObjectRequest updateEvent(HashMap<String, String> params, final VolleyResponseListener listener) {
        String url = EventFinderAPI.API_URL + "updateevent/";
        JSONObject eventJSON = new JSONObject(params);

        return createJsonObjReq(Request.Method.PATCH, url, eventJSON, listener, true);
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
                    })
            {
                @Override
                public HashMap<String, String> getHeaders () throws AuthFailureError {
                    EventFinderAPI api = new EventFinderAPI();
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", "JWT " + api.getToken());
                    System.out.println("Headers " + params.toString());
                    return params;
                }
            };
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
                    })
            {
                @Override
                public HashMap<String, String> getHeaders () throws AuthFailureError {
                    EventFinderAPI api = new EventFinderAPI();
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", "JWT " + api.getToken());
                    System.out.println("Headers " + params.toString());
                    return params;
                }
            };
            return req;
        } catch (Exception e) {
            return null;
        }
    }

    public static JsonArrayRequest getMyEvents(int userID, final VolleyResponseListener listener) {
        String url = EventFinderAPI.API_URL + "getmyevents/?user=" + userID;

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
                    })
            {
                @Override
                public HashMap<String, String> getHeaders () throws AuthFailureError {
                    EventFinderAPI api = new EventFinderAPI();
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", "JWT " + api.getToken());
                    System.out.println("Headers " + params.toString());
                    return params;
                }
            };
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
        return createJsonObjReq(Request.Method.POST, url, credentialsJSON, listener, false);
    }

    //----------------------------------------------------------------------------
    //
    //  Get info for currently logged in user.
    //
    //----------------------------------------------------------------------------
    public static JsonObjectRequest getMyInfo(final VolleyResponseListener listener) {
        String url = EventFinderAPI.API_URL + "getmyinfo/";
        return createJsonObjReq(Request.Method.GET, url, null, listener, true);
    }

    //----------------------------------------------------------------------------
    //
    //  Create a new event with the current logged in user as the host and initial
    //  attendee.
    //
    //----------------------------------------------------------------------------
    public static JsonObjectRequest createNewEvent(HashMap<String,String> params, ArrayList<Integer> interestIDs, final VolleyResponseListener listener) {
        String url = EventFinderAPI.API_URL + "createevent/";
        try {
            JSONArray interests = new JSONArray();
            if(interestIDs != null) {
                interests = new JSONArray(interestIDs);
            }
            JSONObject eventJSON = new JSONObject(params);
            eventJSON.put("interests", interests);

            return createJsonObjReq(Request.Method.POST, url, eventJSON, listener, true);
        }
        catch (Exception e) { return null;}
    }

    public static JsonArrayRequest getRecentEvents(int n, final VolleyResponseListener listener){
        String url = EventFinderAPI.API_URL + "getrecentevents/" + n + "/";
        return createJsonArrReq(Request.Method.GET, url, null, listener, true);
    }

    //----------------------------------------------------------------------------------
    //
    //  Create new conversation based on logged in user, event ID and guest user ID.
    //      fields: "event": int, "guest": int
    //
    //  TOKEN REQUIRED
    //
    //----------------------------------------------------------------------------------
    public static JsonObjectRequest createConversation(int eventID, int guestUserID, final VolleyResponseListener listener){
        String url = EventFinderAPI.API_URL + "createconversation/";
        JSONObject json = new JSONObject();
        try {
            json.put("event", eventID);
            json.put("guest", guestUserID);
            System.out.println(json);
            return createJsonObjReq(Request.Method.POST, url, json, listener, true);
        } catch (Exception e) { return null;}
    }

    //----------------------------------------------------------------------------------
    //
    //  Create new message based on logged in user and conversation ID.
    //      fields: "conversation": int, "message": String
    //
    //  TOKEN REQUIRED
    //
    //----------------------------------------------------------------------------------
    public static JsonObjectRequest createMessage(int conversationID, String message, final VolleyResponseListener listener){
        String url = EventFinderAPI.API_URL + "createmessage/";
        JSONObject json = new JSONObject();
        try {
            json.put("conversation", conversationID);
            json.put("message", message);
        } catch (Exception e) { return null;}
        return createJsonObjReq(Request.Method.POST, url, json, listener, true);
    }


}
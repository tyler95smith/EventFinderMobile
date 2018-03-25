package com.eventfinder.www.eventfindermobile.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Singleton class to handle request queue and other core volley functionality
 * that should be persistent for the lifetime of the app.
 */

public class VolleyHandler {
    // mInstance: a single shared instance of VolleyHandler for all code which uses it.
    private static VolleyHandler mInstance;
    private Context mContext;
    private RequestQueue mRequestQueue;

    //
    // Constructor for VolleyHandler
    //-----------------------------------------------------------------------------
    private VolleyHandler(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    //
    // Returns the shared instance of VolleyHandler, or initializes the
    // VolleyHandler instance if it has not been created yet.
    //-----------------------------------------------------------------------------
    public static synchronized VolleyHandler getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleyHandler(context);
        }
        return mInstance;
    }

    //
    // Returns the Volley RequestQueue for the shared instance of VolleyHandler,
    // or starts the RequestQueue if it has not yet been created.
    //-----------------------------------------------------------------------------
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    //
    // Add a Volley Request (of any type) to the request queue.
    // This allows for one line of code to add a request to the request queue via
    // getInstance.addToRequestQueue(myRequest).
    //-----------------------------------------------------------------------------
    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }
}

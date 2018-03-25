package com.eventfinder.www.eventfindermobile.api;

/**
 * This class should be passed to API requests created from an activity.
 * The purpose of this class is that each activity can determine how to
 * handle the response when a successful request is made, and what
 * happens in the event that an error occurs making the request.
 */

public interface VolleyResponseListener {
    void onError(String message);

    void onResponse(Object response);
}

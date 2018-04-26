package com.eventfinder.www.eventfindermobile.api;

import com.eventfinder.www.eventfindermobile.Event;
import com.eventfinder.www.eventfindermobile.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

/**
 * Class to convert JSON objects received from API calls into their respective objects in the
 * app.
 */

public class DataParsing {

    //-------------------------------------------------------------------------------
    //
    //  Build event object from JSON.
    //
    //-------------------------------------------------------------------------------
    public static Event EventFromJSON(JSONObject eventData){
        System.out.println(eventData);
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSSSS'Z'");
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
        Event e = new Event();
        User host = new User();
        try {
            e.id = eventData.getInt("id");
            if (eventData.getString("date_created").contains(".")) {
                e.dateCreated = df1.parse(eventData.getString("date_created"));
            } else {
                e.dateCreated = df2.parse(eventData.getString("date_created"));
            }
            if(eventData.getString("event_date").contains(".")){
                e.eventDate = df1.parse(eventData.getString("event_date"));
            } else {
                e.eventDate = df2.parse(eventData.getString("event_date"));
            }
            e.eventName = eventData.getString("event_name");
            e.location = eventData.getString("location");
            e.description = eventData.getString("description");
            e.ageMin = eventData.getInt("age_min");
            e.ageMax = eventData.getInt("age_max");
            e.isHidden = eventData.getBoolean("is_hidden");
            JSONObject hostInfo = eventData.getJSONObject("host_info");
            JSONObject hostUser = hostInfo.getJSONObject("user");
            host.username = hostUser.getString("username");
            host.id = hostUser.getInt("id");
            e.host = host;

            // TODO: Need to figure out how interests will work and update app/api accordingly.
            // currently api returns id's of interests stored in the data base.
            //JSONArray interestArr = eventData.getJSONArray("interests");
            //for (int j = 0; j < interestArr.length(); j++) {
                //e.interests.add(interestArr.getString(j));
            //}


            // TODO: Need to create users for attendees and host.
            // TODO: This may mean including full user objects from Event Serializer.
            //JSONArray attendeeArr = eventData.getJSONArray("attendees");
            //for (int j = 0; j < attendeeArr.length(); j++) {
                //e.attendees.add(attendeeArr.getString(j));
            //}
            //e.host = eventData.getInt("host");
            return e;

        } catch (Exception exc){
            System.out.println(exc);
            return null;
        }
    }
}

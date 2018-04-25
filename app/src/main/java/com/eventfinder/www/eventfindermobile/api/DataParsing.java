package com.eventfinder.www.eventfindermobile.api;

import com.eventfinder.www.eventfindermobile.Event;
import com.eventfinder.www.eventfindermobile.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        Event e = new Event();
        try {
            e.id = eventData.getInt("id");
            e.dateCreated = DateFromString(eventData.getString("date_created"));
            e.eventDate = DateFromString(eventData.getString("event_date"));
            e.eventName = eventData.getString("event_name");
            e.location = eventData.getString("location");
            e.description = eventData.getString("description");
            e.ageMin = eventData.getInt("age_min");
            e.ageMax = eventData.getInt("age_max");
            e.isHidden = eventData.getBoolean("is_hidden");

            // TODO: Need to figure out how interests will work and update app/api accordingly.
            // currently api returns id's of interests stored in the data base.
            //JSONArray interestArr = eventData.getJSONArray("interests");
            //for (int j = 0; j < interestArr.length(); j++) {
                //e.interests.add(interestArr.getString(j));
            //}

            JSONArray attendeeArr = eventData.getJSONArray("attendees_info");
            for (int j = 0; j < attendeeArr.length(); j++) {
                e.attendees.add(UserFromPersonJSON(attendeeArr.getJSONObject(j)));
            }
            e.host = UserFromPersonJSON(eventData.getJSONObject("host_info"));
            return e;

        } catch (Exception exc){
            System.out.println(exc);
            return null;
        }
    }

    //-------------------------------------------------------------------------------
    //
    //  Build User object from Person JSON.
    //
    //-------------------------------------------------------------------------------
    public static User UserFromPersonJSON(JSONObject person) {
        User u = new User();
        try {
            u.person_ID = person.getInt("id");
            u.dateOfBirth = DateFromString(person.getString("date_of_birth"));
            u.bio = person.getString("bio");
            u.primaryLocation = person.getString("primaryLocation");
            u.hideLocation = person.getBoolean("hideLocation");
            u.isBanned = person.getBoolean("is_banned");
            JSONObject userPart = person.getJSONObject("user");
            u.id = userPart.getInt("id");
            u.username = userPart.getString("username");
            u.email = userPart.getString("email");
            u.firstName = userPart.getString("first_name");
            u.lastName = userPart.getString("last_name");
            //TODO: How to determine if this is "me"/current logged in user?
            //u.me = ????
        } catch (Exception E){
            return null;
        }
        return null;
    }

    //-------------------------------------------------------------------------------
    //
    //  Get Date object from string representation.
    //      Currently supported formats:
    //          df1: yyyy-MM-dd'T'hh:mm:ss.SSSSSS'Z'
    //          df2: yyyy-MM-dd'T'hh:mm:ss'Z'
    //          df3: yyyy-MM-dd
    //-------------------------------------------------------------------------------
    public static Date DateFromString(String dateString){

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSSSS'Z'");
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
        SimpleDateFormat df3 = new SimpleDateFormat("yyyy-MM-dd");

        try {
            //
            // Note: Would be better to use regular expressions to check if string matches a supported date string format.
            if (dateString.contains(".")) {
                return df1.parse(dateString);
            } else if(dateString.contains("Z")) {
                return df2.parse(dateString);
            } else {
                return df3.parse(dateString);
            }
        } catch (Exception E){
            return new Date(0);
        }
    }
}

package com.eventfinder.www.eventfindermobile;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by redre on 3/22/2018.
 */

public class Event implements Serializable {
    Date dateCreated = new Date();
    String eventName = "Test";
    String location = "Test";
    Date eventDate = new Date();
    String time = "12:00 am";
    String description = ".";
    int ageMin = 0;
    int ageMax = 100;
    ArrayList<String> interests;
    ArrayList<User> attendees = new ArrayList<>();
    User host;
    int maxAttendees;
    boolean isHidden = false;
}

package com.eventfinder.www.eventfindermobile;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by redre on 3/22/2018.
 */

public class Event implements Serializable {
    Date dateCreated;
    String eventName;
    String location;
    Date eventDate;
    String description;
    int ageMin;
    int ageMax;
    ArrayList<Interests> interests;
    ArrayList<User> attendees;
    User host;
    boolean isHidden = false;
}

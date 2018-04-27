package com.eventfinder.www.eventfindermobile;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by redre on 3/22/2018.
 */

public class Event implements Serializable {
    public int id = 1; //default 0
    public Date dateCreated = new Date();
    public String eventName = "Test";
    public String location = "Test";
    public Date eventDate = new Date();
    public String time = "12:00 am";
    public String description = ".";
    public int ageMin = 0;
    public int ageMax = 100;
    public ArrayList<Integer> interests = new ArrayList<>();
    public ArrayList<User> attendees = new ArrayList<>();
    public User host;
    public int maxAttendees;
    public boolean isHidden = false;
}

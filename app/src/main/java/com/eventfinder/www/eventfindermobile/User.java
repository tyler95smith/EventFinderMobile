package com.eventfinder.www.eventfindermobile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by redre on 3/22/2018.
 */

public class User implements Serializable {
    public String username = ".";
    public String firstName = ".";
    public String lastName = ".";
    public String email = ".";
    public Date dateOfBirth = new Date();
    public String bio = ".";
    public String gender = "Male";
    String primaryLocation;
    public ArrayList<String> interests = new ArrayList<>();
    public int id = 0;
    public int Person_ID = 0;
    boolean hideLocation = false;
    boolean isBanned = false;
    boolean hasInterests = false;
    public boolean me = true;
}

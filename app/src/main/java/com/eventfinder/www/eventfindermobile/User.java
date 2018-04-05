package com.eventfinder.www.eventfindermobile;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by redre on 3/22/2018.
 */

public class User implements Serializable {
    String username = ".";
    String firstName = ".";
    String lastName = ".";
    String email = ".";
    Date dateOfBirth = new Date();
    String bio = ".";
    String gender = "Male";
    String primaryLocation;
    ArrayList<String> interests = new ArrayList<>();
    int id = 0;
    boolean hideLocation = false;
    boolean isBanned = false;
    boolean hasInterests = false;
}

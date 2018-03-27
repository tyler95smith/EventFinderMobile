package com.eventfinder.www.eventfindermobile;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by redre on 3/22/2018.
 */

public class User implements Serializable {
    String username;
    String firstName;
    String lastName;
    String email;
    Date dateOfBirth;
    String bio;
    String gender;
    String primaryLocation;
    ArrayList<String> interests;
    int id;
    boolean hideLocation = false;
    boolean isBanned = false;
}

package com.eventfinder.www.eventfindermobile;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by redre on 3/22/2018.
 */

public class User implements Serializable {
    String username;
    String firstName;
    String lastName;
    String email;
    LocalDate dateOfBirth;
    String bio;
    String gender;
    String primaryLocation;
    boolean hideLocation = false;
    boolean isBanned = false;
}

package com.eventfinder.www.eventfindermobile;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by redre on 3/22/2018.
 */

public class User implements Serializable {
    Date dateOfBirth;
    String bio;
    String primaryLocation;
    boolean hideLocation = false;
    boolean isBanned = false;
}

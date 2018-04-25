package com.eventfinder.www.eventfindermobile;

import java.io.Serializable;

/**
 * Created by redre on 4/13/2018.
 */

public class Notification implements Serializable {
    Event event;
    User user;
    String message;
    boolean isInvite;
}

package com.eventfinder.www.eventfindermobile;

import java.io.Serializable;

/**
 * Created by redre on 4/13/2018.
 */

public class Notification implements Serializable {
    public Event event;
    public User user;
    public String message;
    public boolean isInvite;
}

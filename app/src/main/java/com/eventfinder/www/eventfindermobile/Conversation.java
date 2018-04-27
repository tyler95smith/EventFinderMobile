package com.eventfinder.www.eventfindermobile;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by Taylor on 4/26/2018.
 */

public class Conversation implements Serializable {
    public LinkedList<ChatMessage> messages = new LinkedList();
    public User guest;
    public int id;
    public Event event;
}

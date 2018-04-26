package com.eventfinder.www.eventfindermobile.api;


/**
 * Global variables for API.
 */
public class EventFinderAPI {
    public final static String API_URL = "http://claire.nthbox.com:8407/api/";
    private static String token = "";

    public String getToken(){return this.token;}
    public void setToken(String t){this.token = t;}
}

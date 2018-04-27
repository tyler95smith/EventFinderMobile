package com.eventfinder.www.eventfindermobile;


import java.io.Serializable;
import java.util.Date;

public class ChatMessage implements Serializable {
    public String m_senderName, m_txt;
    public boolean m_isCurrentUser = false;
    public int m_senderID;
    public Date m_dateSent;


    public ChatMessage(int senderID, String senderName, String txt, boolean isCurrentUser, Date dateSent) {
        this.m_senderName = senderName;
        this.m_txt = txt;
        this.m_isCurrentUser = isCurrentUser;
        this.m_senderID = senderID;
        this.m_dateSent = dateSent;
    }
}

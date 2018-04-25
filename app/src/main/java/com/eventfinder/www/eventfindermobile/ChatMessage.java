package com.eventfinder.www.eventfindermobile;


public class ChatMessage {
    private String m_senderName, m_txt;
    private boolean m_isCurrentUser;

    public ChatMessage(String senderName, String txt, boolean isCurrentUser) {
        this.m_senderName = senderName;
        this.m_txt = txt;
        this.m_isCurrentUser = isCurrentUser;
    }

    public String getSenderName() {
        return m_senderName;
    }
    public String getText() {
        return m_txt;
    }
    public boolean isCurrentUser()
    {
        return m_isCurrentUser;
    }
}

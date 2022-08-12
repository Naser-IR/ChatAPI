package com.example.whatsapp_android.api_entities;

public class Api_Invite {
    public String from;
    public String to;
    public String server;

    public Api_Invite(String from, String to, String server) {
        this.from = from;
        this.to = to;
        this.server = server;
    }
}

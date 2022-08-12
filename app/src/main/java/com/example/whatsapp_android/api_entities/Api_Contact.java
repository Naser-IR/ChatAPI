package com.example.whatsapp_android.api_entities;

public class Api_Contact {
    public String id;
    public String userId;
    public String name;
    public String server;
    public String last;
    public String lastdate;

    public Api_Contact(String id, String userId, String name, String server,String last, String lastdate) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.server = server;
        this.last = last;
        this.lastdate = lastdate;
    }

    public Api_Contact(String userId, String name, String server) {
        this.userId = userId;
        this.name = name;
        this.server = server;
    }
}

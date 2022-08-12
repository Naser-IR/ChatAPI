package com.example.whatsapp_android.api_entities;

public class Api_Messages {
    public String from;
    public String to;
    public String content;

    public Api_Messages(String from, String to, String content){
        this.from = from;
        this.to = to;
        this.content = content;
    }
}

package com.example.whatsapp_android.api_entities;

public class Api_User {
    public String username;
    public String name;
    public String password;

    public Api_User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Api_User(String username, String name, String password) {
        this.username = username;
        this.password = password;
        this.name = name;
    }
}

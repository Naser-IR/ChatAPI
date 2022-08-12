package com.example.whatsapp_android.Data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contact {
    @NonNull
    @PrimaryKey()
    public String contname;
    public String name;
    public String userId;
    public String server;
    public String last;
    public String lastdate;

    public Contact(@NonNull String contname, String name, String userId, String server, String last, String lastdate) {
        this.contname = contname;
        this.name = name;
        this.userId = userId;
        this.server = server;
        this.last = last;
        this.lastdate = lastdate;
    }

    @NonNull
    public String getContname() {
        return contname;
    }

    public void setContname(@NonNull String contname) {
        this.contname = contname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}

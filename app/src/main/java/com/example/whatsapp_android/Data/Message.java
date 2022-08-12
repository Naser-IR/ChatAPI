package com.example.whatsapp_android.Data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Message {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String content;
    public String created;
    public boolean sent;
    public String contactId;

    public Message(String content, String created, boolean sent, String contactId) {
        this.content = content;
        this.created = created;
        this.sent = sent;
        this.contactId = contactId;
    }

}

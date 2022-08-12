package com.example.whatsapp_android.Data;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithContacts {
    @Embedded
    public User user;
    @Relation(
            parentColumn = "userName",
            entityColumn = "userId"
    )
    public List<Contact> contacts;
}

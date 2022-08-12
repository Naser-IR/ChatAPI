package com.example.whatsapp_android.Data;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ContactWithMessages {
    @Embedded
    public Contact contact;
    @Relation(
            parentColumn = "contname",
            entityColumn = "contactId"
    )
    public List<Message> messages;
}

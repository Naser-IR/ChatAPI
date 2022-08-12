package com.example.whatsapp_android.Data;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Contact.class, Message.class}, version = 1)
public abstract class AppDB extends RoomDatabase {
    public abstract UserDao userDao();
}
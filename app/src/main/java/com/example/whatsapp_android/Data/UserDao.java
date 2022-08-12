package com.example.whatsapp_android.Data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM User WHERE userName = :username")
    User getUser(String username);

    @Transaction
    @Query("SELECT * FROM User WHERE userName = :username")
    UserWithContacts getContacts(String username);

    @Transaction
    @Query("SELECT * FROM Contact WHERE contname = :contName")
    ContactWithMessages getMessages(String contName);

    @Query("SELECT * FROM Contact WHERE contname = :contName")
    Contact getContact(String contName);

    @Insert
    void insertUser(User... User);

    @Insert
    void insertContact(Contact... contacts);

    @Update
    void updateContact(Contact... contacts);

    @Delete
    void deleteContact(Contact... contacts);

    @Insert
    void insertMessage(Message... messages);

    @Update
    void updateMessage(Message... messages);

    @Delete
    void deleteMessage(Message... messages);

}

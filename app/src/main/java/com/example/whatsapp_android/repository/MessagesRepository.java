package com.example.whatsapp_android.repository;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.whatsapp_android.Data.AppDB;
import com.example.whatsapp_android.Data.Contact;
import com.example.whatsapp_android.Data.ContactWithMessages;
import com.example.whatsapp_android.Data.Message;
import com.example.whatsapp_android.Data.UserDao;
import com.example.whatsapp_android.Data.UserWithContacts;
import com.example.whatsapp_android.MyApplication;
import com.example.whatsapp_android.User_Token;
import com.example.whatsapp_android.api.ContactsAPI;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MessagesRepository {
    private UserDao userDao;
    private String contact;
    private ContactsAPI api;
    private MessageListData messageListData;
    public static MessagesRepository mRepo;
    public MessagesRepository(String contactName){
        AppDB db = Room.databaseBuilder(MyApplication.context, AppDB.class, User_Token.username)
                .allowMainThreadQueries().build();
        userDao = db.userDao();
        api = new ContactsAPI();
        messageListData = new MessageListData(contactName);
        contact = contactName;
        mRepo = this;
    }

    public String getContact() {
        return contact;
    }

    class MessageListData extends MutableLiveData<List<Message>> {
        public MessageListData(String contactName){
            super();
            ContactWithMessages contactWithMessages = userDao.getMessages(contactName);
            if (contactWithMessages != null) {
                setValue(contactWithMessages.messages);
            }
            api.getMessages(contactName, this, User_Token.token);
        }
    }
    public MutableLiveData<List<Message>> getAll(){
        return messageListData;
    }

    public void addMessage(String msg){
        addToRoom(msg, true);
        api.addMessage(contact, msg, User_Token.token);
    }

    public void addToRoom(String msg, boolean sent){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd_HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        Message msg1 = new Message(msg, currentDateandTime,sent, contact);
        List<Message> messages = messageListData.getValue();
        messages.add(msg1);
        messageListData.postValue(messages);
        userDao.insertMessage(msg1);
    }
}
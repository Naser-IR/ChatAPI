package com.example.whatsapp_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.whatsapp_android.Data.*;
import com.example.whatsapp_android.api.ContactsAPI;
import com.example.whatsapp_android.databinding.ActivityLoginBinding;
import com.google.firebase.iid.FirebaseInstanceId;

public class LoginActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "1";
    private ActivityLoginBinding binding;
    private UserDao userDao;
    private AppDB db;
    private ContactsAPI api;
    MutableLiveData<String> token;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        api = new ContactsAPI();
        token = new MutableLiveData<String>();

        token.observe(this, token -> {
            if (token != null) {
                Intent intent = new Intent(this, ChatActivity.class);
                db = Room.databaseBuilder(getApplicationContext(), AppDB.class, username)
                        .allowMainThreadQueries().build();
                userDao = db.userDao();
                User u = userDao.getUser(username);
                if (u == null) {
                    userDao.insertUser(new User(username, username, binding.password.getText().toString(),null));
                }
                User_Token.setUsernameToken(binding.username.getText().toString(), "Bearer " + token);
                startActivity(intent);
            }
        });

        binding.loginbtn.setOnClickListener(v -> {
            username = binding.username.getText().toString();
            String password = binding.password.getText().toString();
            api.login(username, password, token);
        });

        binding.forgotpass.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}


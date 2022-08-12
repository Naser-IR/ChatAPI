package com.example.whatsapp_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.TextView;

import com.example.whatsapp_android.Data.AppDB;
import com.example.whatsapp_android.Data.Contact;
import com.example.whatsapp_android.Data.Message;
import com.example.whatsapp_android.Data.User;
import com.example.whatsapp_android.Data.UserDao;
import com.example.whatsapp_android.adapter.ContactsListAdapter;
import com.example.whatsapp_android.adapter.MessageAdapter;
import com.example.whatsapp_android.api.TransferApi;
import com.example.whatsapp_android.databinding.ActivityChatBinding;
import com.example.whatsapp_android.databinding.ActivityLoginBinding;
import com.example.whatsapp_android.databinding.ActivityPersonalChatBinding;
import com.example.whatsapp_android.repository.MessagesRepository;
import com.example.whatsapp_android.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class PersonalChat extends AppCompatActivity {

    private ActivityPersonalChatBinding binding;
    private UserDao userDao;
    private AppDB db;
    private MessagesRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.Theme_Dark);
        } else {
            setTheme(R.style.Theme_Light);
        }
        super.onCreate(savedInstanceState);
        binding = ActivityPersonalChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle bundle = getIntent().getExtras();
        String contname = bundle.getString("contname");
        repo = new MessagesRepository(contname);
        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, User_Token.username)
                .allowMainThreadQueries().build();
        userDao = db.userDao();

        Contact temp = userDao.getContact(contname);

        String[] preServer = temp.server.split(":");
        TransferApi transferApi = new TransferApi("http://10.0.2.2:" + preServer[1] + "/api/");
        RecyclerView lscontact = findViewById(R.id.chatRecycleView);
        final MessageAdapter adapter = new MessageAdapter(this);
        lscontact.setAdapter(adapter);
        lscontact.setLayoutManager(new LinearLayoutManager(this));
        repo.getAll().observe(this, messages ->{
            adapter.setMessages(messages);
        });
        binding.sendMessage.setOnClickListener(v -> {
            String content = binding.typeMessage.getText().toString();
            repo.addMessage(content);
            transferApi.transfer(User_Token.username, contname, content);
            binding.typeMessage.setText("");
        });
        binding.contactName.setText(temp.name);

        binding.charReturn.setOnClickListener(v -> {
            MessagesRepository.mRepo = null;
            finish();
        });
    }

    @Override
    protected void onPause() {
        MessagesRepository.mRepo = null;
        super.onPause();
    }

    @Override
    protected void onStop() {
        MessagesRepository.mRepo = null;
        super.onStop();
    }


}
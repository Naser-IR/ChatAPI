package com.example.whatsapp_android;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.whatsapp_android.Data.AppDB;
import com.example.whatsapp_android.Data.Contact;
import com.example.whatsapp_android.Data.User;
import com.example.whatsapp_android.Data.UserDao;
import com.example.whatsapp_android.adapter.ContactsListAdapter;
import com.example.whatsapp_android.adapter.MessageAdapter;
import com.example.whatsapp_android.api.TransferApi;
import com.example.whatsapp_android.databinding.ActivityChatBinding;
import com.example.whatsapp_android.repository.MessagesRepository;
import com.example.whatsapp_android.repository.UserRepository;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private UserDao userDao;
    private AppDB db;
    private UserRepository repo;
    private int orientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.Theme_Dark);
        } else {
            setTheme(R.style.Theme_Light);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        FloatingActionButton sittings = findViewById(R.id.sittings);
        sittings.setOnClickListener(v -> {
            Intent intent = new Intent(this, UpdateProfile.class);
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                intent.putExtra("dark", true);
            } else {
                intent.putExtra("dark", false);
            }

            startActivity(intent);
        });

        FloatingActionButton add = findViewById(R.id.addContactBtn);
        add.setOnClickListener(v -> {
            Intent intent = new Intent(this, InviteActivity.class);
            startActivity(intent);
        });

        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, User_Token.username)
                .allowMainThreadQueries().build();
        userDao = db.userDao();
        repo = UserRepository.getInstance();

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(ChatActivity.this, instanceIdResult -> {
            String newToken = instanceIdResult.getToken();
            repo.addAndroid(User_Token.username, newToken);
        });

        RecyclerView lscontact = findViewById(R.id.contact_list);
        final ContactsListAdapter adapter = new ContactsListAdapter(this);
        lscontact.setAdapter(adapter);
        lscontact.setLayoutManager(new LinearLayoutManager(this));
        repo.getAll().observe(this, contacts -> {
            adapter.setContacts(contacts);
        });
        TextView name = findViewById(R.id.NickName);
        ImageView image = findViewById(R.id.userImage);
        User temp = userDao.getUser(User_Token.username);
        name.setText(temp.name);
        if (temp.image != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(temp.image, 0, temp.image.length);
            image.setImageBitmap(bmp);
        }


        orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            RelativeLayout layout = findViewById(R.id.landscape_visible);
            layout.setVisibility(View.VISIBLE);
        } else {
            MessagesRepository.mRepo = null;
        }

    }

    public int getOrientation() {
        return orientation;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(repo != null) {
            repo.refresh();
        }
    }

    public void goToContact(String contact) {
        Intent intent = new Intent(this, PersonalChat.class);
        intent.putExtra("contname", contact);
        startActivity(intent);
    }

    public void openChatContact(String contact) {
        MessagesRepository repo2 = new MessagesRepository(contact);
        Contact temp = userDao.getContact(contact);

        String[] preServer = temp.server.split(":");
        TransferApi transferApi = new TransferApi("http://10.0.2.2:" + preServer[1] + "/api/");
        RecyclerView lscontact = findViewById(R.id.chatRecycleView_landscape);
        final MessageAdapter adapter = new MessageAdapter(this);
        lscontact.setAdapter(adapter);
        lscontact.setLayoutManager(new LinearLayoutManager(this));
        repo2.getAll().observe(this, messages ->{
            adapter.setMessages(messages);
        });
        ImageButton send = findViewById(R.id.sendMessageLandscape);
        send.setOnClickListener(v -> {
            EditText message = findViewById(R.id.typeMessageLandscape);
            String content = message.getText().toString();
            repo2.addMessage(content);
            transferApi.transfer(User_Token.username, contact, content);
            message.setText("");
        });
        TextView name = findViewById(R.id.contactNameLandscape);
        name.setText(temp.name);
    }
}
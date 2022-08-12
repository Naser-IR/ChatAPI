package com.example.whatsapp_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatDelegate;

public class UpdateProfile extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.Theme_Dark);
        } else {
            setTheme(R.style.Theme_Light);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateprofile);

        Switch darkbtn = findViewById(R.id.Dark_btn);
        Bundle bundle = getIntent().getExtras();
        boolean dark = bundle.getBoolean("dark");
        darkbtn.setChecked(dark);
        darkbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        ImageButton btn = findViewById(R.id.returnToChatupdate);
        btn.setOnClickListener(v -> {
            finish();
        });

        Button updatebtn = findViewById(R.id.updateprofilebutton);
        updatebtn.setOnClickListener(v -> {
            String username = findViewById(R.id.getnewusername).toString();
            finish();
            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtra("username", username);
            //   api.login(username, password, token);
//            db = Room.databaseBuilder(getApplicationContext(), AppDB.class, username)
//                    .allowMainThreadQueries().build();
//            userDao = db.userDao();
            startActivity(intent);

        });
//        darkbtn.setOnClickListener(v -> {
//            Intent intent = new Intent(this, PersonalChat.class);
//            intent.putExtra("Dark", 1);
//            startActivity(intent);
//        });
    }

}

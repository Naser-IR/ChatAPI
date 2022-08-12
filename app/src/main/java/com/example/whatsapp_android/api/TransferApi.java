package com.example.whatsapp_android.api;

import android.util.Log;

import com.example.whatsapp_android.MyApplication;
import com.example.whatsapp_android.R;
import com.example.whatsapp_android.api_entities.Api_Invite;
import com.example.whatsapp_android.api_entities.Api_Transfer;
import com.example.whatsapp_android.api_entities.Api_User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TransferApi {
    Retrofit retrofit;
    SendApi webServiceAPI;
    public TransferApi(String contServer) {
        retrofit = new Retrofit.Builder().baseUrl(contServer)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(SendApi.class);
    }

    public void transfer(String from, String to, String content) {
        Api_Transfer msg = new Api_Transfer(from, to, content);
        Call<String> call = webServiceAPI.transfer(msg);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String ret = response.body();
                Log.i("ContactsAPI", ret);
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    public void invite(String from, String to, String server) {
        Api_Invite inv = new Api_Invite(from, to, server);
        Call<String> call = webServiceAPI.invitations(inv);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String ret = response.body();
                Log.i("ContactsAPI", ret);
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }
}

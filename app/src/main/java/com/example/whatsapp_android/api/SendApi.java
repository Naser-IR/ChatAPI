package com.example.whatsapp_android.api;

import com.example.whatsapp_android.api_entities.*;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SendApi {
    @POST("contacts/transfer")
    Call<String> transfer(@Body Api_Transfer transfer);

    @POST("contacts/invitations")
    Call<String> invitations(@Body Api_Invite invite);
}

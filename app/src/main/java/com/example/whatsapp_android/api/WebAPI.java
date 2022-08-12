package com.example.whatsapp_android.api;



import retrofit2.Call;

import com.example.whatsapp_android.Data.*;
import com.example.whatsapp_android.api_entities.*;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface WebAPI {

    @POST("contacts2/login")
    Call<String> login(@Body Api_User temp);

    @POST("contacts2/register")
    Call<String> register(@Body Api_User temp);

    @GET("contacts2")
    Call<List<Contact>> getContacts(@Header("Authorization") String token);

    @POST("contacts2")
    Call<String> addContact(@Body Api_Contact cont, @Header("Authorization") String token);

    @GET("contacts2/{ContID}")
    Call<Contact> getContact(@Path("ContID") String ContID, @Header("Authorization") String token);

    @GET("contacts2/{ContID}/messages")
    Call<List<Message>> getMessages(@Path("ContID") String ContID, @Header("Authorization") String token);

    @POST("contacts2/{ContID}/messages")
    Call<String> addMessage(@Path("ContID") String ContID, @Body Temp Content, @Header("Authorization") String token);

    @POST("contacts2/map")
    Call<String> addAndroid(@Body Api_Device temp);
}

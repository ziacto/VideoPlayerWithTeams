package com.example.videoplayerwithteams.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ZeeZee on 11/16/2017.
 */

public class CallingWebServices
{
    Retrofit retrofit;
    WebServices webServices;
    Gson gson;

    public static final String BASE_URL = "https://maps.googleapis.com/maps/api/";

    private static CallingWebServices callingWebServices;

    public static CallingWebServices getInstance()
    {
        if (callingWebServices == null)
        {
            callingWebServices = new CallingWebServices();
        }
        return callingWebServices;
    }

    private CallingWebServices()
    {
        initRetrofit();
    }

    private void initRetrofit()
    {

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.readTimeout(60, TimeUnit.SECONDS);
        okHttpClient.connectTimeout(60, TimeUnit.SECONDS);

        gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).client(okHttpClient.build()).build();
        webServices = retrofit.create(WebServices.class);
    }


}

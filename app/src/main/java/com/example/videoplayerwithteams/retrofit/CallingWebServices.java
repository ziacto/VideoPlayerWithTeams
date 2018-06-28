package com.example.videoplayerwithteams.retrofit;

import com.example.videoplayerwithteams.interfaces.ServiceResponseChecker;
import com.example.videoplayerwithteams.models.TeamPlayerResponseModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
    
    public static final String BASE_URL = "https://devapi.dawriplus.com/";
    
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
    
    public void getTeamPlayers(final ServiceResponseChecker serviceResponseChecker)
    {
        Call<TeamPlayerResponseModel> call = webServices.getTeamPlayers();
        call.enqueue(new Callback<TeamPlayerResponseModel>()
        {
            @Override
            public void onResponse(Call<TeamPlayerResponseModel> call, Response<TeamPlayerResponseModel> response)
            {
                if (response.code() == 200)
                {
                    serviceResponseChecker.onSuccess(response.body());
                }
                else
                {
                    serviceResponseChecker.onError("Teams can't be fetched");
                }
            }
            
            @Override
            public void onFailure(Call<TeamPlayerResponseModel> call, Throwable t)
            {
                serviceResponseChecker.onError("Server can't be reached at the moment!");
            }
        });
    }
}

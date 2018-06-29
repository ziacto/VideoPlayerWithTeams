package com.example.videoplayerwithteams.retrofit;

import com.example.videoplayerwithteams.models.TeamPlayerResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by muhammadzia on 1/30/2018.
 */

public interface WebServices
{
    @GET("sample.json")
    Call<TeamPlayerResponseModel> getTeamPlayers();
}

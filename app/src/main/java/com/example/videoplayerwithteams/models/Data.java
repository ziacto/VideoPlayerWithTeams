
package com.example.videoplayerwithteams.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data
{
    
    @SerializedName("HomeTeam")
    @Expose
    private HomeTeam homeTeam;
    @SerializedName("AwayTeam")
    @Expose
    private AwayTeam awayTeam;
    
    public HomeTeam getHomeTeam()
    {
        return homeTeam;
    }
    
    public void setHomeTeam(HomeTeam homeTeam)
    {
        this.homeTeam = homeTeam;
    }
    
    public AwayTeam getAwayTeam()
    {
        return awayTeam;
    }
    
    public void setAwayTeam(AwayTeam awayTeam)
    {
        this.awayTeam = awayTeam;
    }
    
}

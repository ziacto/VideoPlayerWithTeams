
package com.example.videoplayerwithteams.models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AwayTeam
{
    
    @SerializedName("Players")
    @Expose
    private List<Player> players = null;
    
    public List<Player> getPlayers()
    {
        return players;
    }
    
    public void setPlayers(List<Player> players)
    {
        this.players = players;
    }
    
}

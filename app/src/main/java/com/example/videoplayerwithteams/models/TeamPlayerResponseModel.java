
package com.example.videoplayerwithteams.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeamPlayerResponseModel
{
    
    @SerializedName("Lineups")
    @Expose
    private Lineups lineups;
    
    public Lineups getLineups()
    {
        return lineups;
    }
    
    public void setLineups(Lineups lineups)
    {
        this.lineups = lineups;
    }
    
}

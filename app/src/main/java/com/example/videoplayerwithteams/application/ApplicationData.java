package com.example.videoplayerwithteams.application;

import com.example.videoplayerwithteams.models.TeamPlayerResponseModel;

/**
 * Created by ZeeZee on 6/28/2018.
 */

public class ApplicationData
{
    private static ApplicationData applicationData;
    private TeamPlayerResponseModel teamPlayerResponseModel;
    
    public static ApplicationData getInstance()
    {
        if (applicationData == null)
            applicationData = new ApplicationData();
        return applicationData;
    }
    
    private ApplicationData()
    {
        
    }
    
    public TeamPlayerResponseModel getTeamPlayerResponseModel()
    {
        return teamPlayerResponseModel;
    }
    
    public void setTeamPlayerResponseModel(TeamPlayerResponseModel teamPlayerResponseModel)
    {
        this.teamPlayerResponseModel = teamPlayerResponseModel;
    }
}

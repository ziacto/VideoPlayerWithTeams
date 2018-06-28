
package com.example.videoplayerwithteams.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lineups
{
    
    @SerializedName("Success")
    @Expose
    private boolean success;
    @SerializedName("Data")
    @Expose
    private Data data;
    
    public boolean isSuccess()
    {
        return success;
    }
    
    public void setSuccess(boolean success)
    {
        this.success = success;
    }
    
    public Data getData()
    {
        return data;
    }
    
    public void setData(Data data)
    {
        this.data = data;
    }
    
}

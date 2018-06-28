
package com.example.videoplayerwithteams.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Player {

    @SerializedName("Order")
    @Expose
    private int order;
    @SerializedName("StartInField")
    @Expose
    private boolean startInField;
    @SerializedName("Role")
    @Expose
    private String role;
    @SerializedName("IsCaptain")
    @Expose
    private boolean isCaptain;
    @SerializedName("JerseyNumber")
    @Expose
    private String jerseyNumber;
    @SerializedName("Id")
    @Expose
    private int id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("XCoordinate")
    @Expose
    private int xCoordinate;
    @SerializedName("YCoordinate")
    @Expose
    private int yCoordinate;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isStartInField() {
        return startInField;
    }

    public void setStartInField(boolean startInField) {
        this.startInField = startInField;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isIsCaptain() {
        return isCaptain;
    }

    public void setIsCaptain(boolean isCaptain) {
        this.isCaptain = isCaptain;
    }

    public String getJerseyNumber() {
        return jerseyNumber;
    }

    public void setJerseyNumber(String jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

}

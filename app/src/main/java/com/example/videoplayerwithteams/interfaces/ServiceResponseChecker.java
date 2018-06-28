package com.example.videoplayerwithteams.interfaces;

/**
 * Created by ZeeZee on 11/16/2017.
 */

public interface ServiceResponseChecker
{
    void onSuccess(Object object);
    void onFail(Object object);
    void onError(Object object);
}

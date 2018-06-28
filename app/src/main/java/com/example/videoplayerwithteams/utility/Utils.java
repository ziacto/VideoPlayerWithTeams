package com.example.videoplayerwithteams.utility;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by ZeeZee on 6/28/2018.
 */

public class Utils
{
    private static ProgressDialog progressDialog;
    
    public static void showProgressDialog(Context context)
    {
        hideProgressDialog();
        if (progressDialog == null)
        {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("please wait...");
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
        
    }
    
    public static void hideProgressDialog()
    {
        if (progressDialog != null)
            if (progressDialog.isShowing())
                progressDialog.dismiss();
    }
}

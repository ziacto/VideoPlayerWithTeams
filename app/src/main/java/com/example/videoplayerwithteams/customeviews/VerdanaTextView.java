package com.example.videoplayerwithteams.customeviews;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by muhammadzia on 5/22/2017.
 */

public class VerdanaTextView extends android.support.v7.widget.AppCompatTextView
{
    public VerdanaTextView(Context context)
    {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Verdana.ttf");
        this.setTypeface(face);
    }

    public VerdanaTextView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Verdana.ttf");
        this.setTypeface(face);
    }

    public VerdanaTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Verdana.ttf");
        this.setTypeface(face);
    }
}

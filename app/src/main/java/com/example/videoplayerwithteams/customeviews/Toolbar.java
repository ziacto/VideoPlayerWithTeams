package com.example.videoplayerwithteams.customeviews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.videoplayerwithteams.R;
import com.example.videoplayerwithteams.interfaces.MenuItemClickedListener;


/**
 * Created by muhammadzia on 10/20/2017.
 */

public class Toolbar extends RelativeLayout
{
    public Toolbar(Context context)
    {
        super(context);
        initViews(context);
    }

    public Toolbar(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initViews(context);
    }

    public Toolbar(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    private void initViews(Context context)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        try
        {
            inflater.inflate(R.layout.layout_toolbar, this);
        }
        catch (Exception e)
        {
            Log.e("view-error", e.toString());
        }

        bindViews();
    }

    public ImageView ivTogglePlayerItem, ivToggleFullScreen;
    TextView tvTitle;
    MenuItemClickedListener menuItemClickedListener;

    public void setOnMenuClickedListener(MenuItemClickedListener menuItemClickedListener)
    {
        this.menuItemClickedListener = menuItemClickedListener;
    }

    public void setTitle(String title)
    {
        tvTitle.setText(title);
    }

    private void bindViews()
    {
        ivTogglePlayerItem = (ImageView) findViewById(R.id.ivTogglePlayerItem);
        ivToggleFullScreen = (ImageView) findViewById(R.id.ivToggleFullScreen);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
    
        ivToggleFullScreen.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (menuItemClickedListener != null)
                    menuItemClickedListener.onToggleFullScreenClicked();
            }
        });
    
        ivTogglePlayerItem.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (menuItemClickedListener != null)
                    menuItemClickedListener.onTogglePlayerItems();
            }
        });
    }


}

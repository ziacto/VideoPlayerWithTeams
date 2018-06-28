package com.example.videoplayerwithteams.activities;

import android.app.Activity;
import android.net.Uri;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.easyvideoplayer.EasyVideoCallback;
import com.afollestad.easyvideoplayer.EasyVideoPlayer;
import com.example.videoplayerwithteams.R;
import com.example.videoplayerwithteams.adapters.TeamPlayerCarouselRecyclerViewAdapter;
import com.example.videoplayerwithteams.application.ApplicationData;
import com.example.videoplayerwithteams.constants.Constants;
import com.example.videoplayerwithteams.interfaces.ServiceResponseChecker;
import com.example.videoplayerwithteams.models.TeamPlayerResponseModel;
import com.example.videoplayerwithteams.retrofit.CallingWebServices;
import com.example.videoplayerwithteams.utility.Utils;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
{
    private String URL = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
    
    @BindView(R.id.easyVideoPlayer)
    EasyVideoPlayer easyVideoPlayer;
    
    @BindView(R.id.rvTeamPlayers)
    DiscreteScrollView rvTeamPlayers;
    
    @BindView(R.id.ivHome)
    ImageView ivHome;
    
    @BindView(R.id.ivAway)
    ImageView ivAway;
    
    @BindView(R.id.tvHome)
    TextView tvHome;
    
    @BindView(R.id.tvAway)
    TextView tvAway;
    
    @BindView(R.id.llHome)
    LinearLayout llHome;
    
    @BindView(R.id.llAway)
    LinearLayout llAway;
    
    @BindView(R.id.llCarousel)
    LinearLayout llCarousel;
    
    private int mediaPlayerSelectedPosition = 0;
    private boolean isHomeSelected = false;
    private boolean isAwaySelected = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mediaPlayerSelectedPosition = getIntent().getExtras().getInt(Constants.VIDEO_SEEK_TIME_KEY);
        initializeVideoPlayer();
        initializeCarouselImages();
        initializingButtons();
        llCarousel.startAnimation(inFromRightAnimation());
    }
    
    protected void selectView(LinearLayout layout, TextView text, ImageView icon)
    {
        layout.setBackground(getResources().getDrawable(R.drawable.rounded_rectangle_selected));
        text.setTextColor(getResources().getColor(R.color.selected_button_text_color));
        icon.setColorFilter(getResources().getColor(R.color.selected_button_text_color));
    }
    
    protected void unSelectView(LinearLayout layout, TextView text, ImageView icon)
    {
        layout.setBackground(getResources().getDrawable(R.drawable.rounded_rectangle_white));
        text.setTextColor(getResources().getColor(R.color.tab_unselected_color));
        icon.setColorFilter(getResources().getColor(R.color.tab_unselected_color));
    }
    
    @Override
    protected void onPause()
    {
        super.onPause();
        mediaPlayerSelectedPosition = easyVideoPlayer.getCurrentPosition();
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        easyVideoPlayer.seekTo(mediaPlayerSelectedPosition);
    }
    
    private Animation inFromRightAnimation()
    {
        
        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(1200);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
    }
    
    private void initializeVideoPlayer()
    {
        easyVideoPlayer = (EasyVideoPlayer) findViewById(R.id.easyVideoPlayer);
        easyVideoPlayer.setSource(Uri.parse(URL));
        easyVideoPlayer.start();
        easyVideoPlayer.setAutoPlay(true);
        easyVideoPlayer.setLoop(true);
        easyVideoPlayer.hideControls();
        easyVideoPlayer.disableControls();
        easyVideoPlayer.seekTo(mediaPlayerSelectedPosition);
        easyVideoPlayer.setCallback(new EasyVideoCallback()
        {
            @Override
            public void onStarted(EasyVideoPlayer player)
            {
                
            }
            
            @Override
            public void onPaused(EasyVideoPlayer player)
            {
                
            }
            
            @Override
            public void onPreparing(EasyVideoPlayer player)
            {
                
            }
            
            @Override
            public void onPrepared(EasyVideoPlayer player)
            {
                player.setVolume(0f, 0f);
            }
            
            @Override
            public void onBuffering(int percent)
            {
                
            }
            
            @Override
            public void onError(EasyVideoPlayer player, Exception e)
            {
                
            }
            
            @Override
            public void onCompletion(EasyVideoPlayer player)
            {
                
            }
            
            @Override
            public void onRetry(EasyVideoPlayer player, Uri source)
            {
                
            }
            
            @Override
            public void onSubmit(EasyVideoPlayer player, Uri source)
            {
                
            }
        });
    }
    
    private void initializeCarouselImages()
    {
        rvTeamPlayers = (DiscreteScrollView) findViewById(R.id.rvTeamPlayers);
        rvTeamPlayers.setOffscreenItems(3);
        rvTeamPlayers.setItemTransformer(new ScaleTransformer.Builder().setMinScale(0.7f).build());
        
        rvTeamPlayers.setHasFixedSize(true);
        rvTeamPlayers.setNestedScrollingEnabled(false);
        
        rvTeamPlayers.addOnItemTouchListener(new RecyclerView.OnItemTouchListener()
        {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e)
            {
                int action = e.getAction();
                switch (action)
                {
                    case MotionEvent.ACTION_MOVE:
                        rv.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                }
                return false;
            }
            
            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e)
            {
                
            }
            
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept)
            {
                
            }
        });
        
    }
    
    private void initializingButtons()
    {
        llHome.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (!isHomeSelected)
                {
                    isHomeSelected = true;
                    isAwaySelected = false;
                    selectView(llHome, tvHome, ivHome);
                    unSelectView(llAway, tvAway, ivAway);
                    getTeamPlayers();
                }
            }
        });
        
        llAway.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (!isAwaySelected)
                {
                    isAwaySelected = true;
                    isHomeSelected = false;
                    unSelectView(llHome, tvHome, ivHome);
                    selectView(llAway, tvAway, ivAway);
                    getTeamPlayers();
                }
            }
        });
    }
    
    private void getTeamPlayers()
    {
        if(ApplicationData.getInstance().getTeamPlayerResponseModel() == null)
        {
            Utils.showProgressDialog(MainActivity.this);
            CallingWebServices.getInstance().getTeamPlayers(new ServiceResponseChecker()
            {
                @Override
                public void onSuccess(Object object)
                {
                    Utils.hideProgressDialog();
                    TeamPlayerResponseModel teamPlayerResponseModel = (TeamPlayerResponseModel) object;
                    ApplicationData.getInstance().setTeamPlayerResponseModel(teamPlayerResponseModel);
                    setTeamsAccordingly();
                }
    
                @Override
                public void onFail(Object object)
                {
                    Utils.hideProgressDialog();
                }
    
                @Override
                public void onError(Object object)
                {
                    Utils.hideProgressDialog();
                }
            });
        }
        else
        {
            setTeamsAccordingly();
        }
    }
    
    private void setTeamsAccordingly()
    {
        if(isHomeSelected)
        {
            rvTeamPlayers.setAdapter(new TeamPlayerCarouselRecyclerViewAdapter(ApplicationData.getInstance().getTeamPlayerResponseModel().getLineups().getData().getHomeTeam().getPlayers(), MainActivity.this));
        }
        else if(isAwaySelected)
        {
            rvTeamPlayers.setAdapter(new TeamPlayerCarouselRecyclerViewAdapter(ApplicationData.getInstance().getTeamPlayerResponseModel().getLineups().getData().getAwayTeam().getPlayers(), MainActivity.this));
        }
    }
}

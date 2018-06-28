package com.example.videoplayerwithteams.activities;

import android.app.Activity;
import android.net.Uri;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.easyvideoplayer.EasyVideoCallback;
import com.afollestad.easyvideoplayer.EasyVideoPlayer;
import com.example.videoplayerwithteams.R;
import com.example.videoplayerwithteams.adapters.TeamPlayerCarouselRecyclerViewAdapter;
import com.example.videoplayerwithteams.constants.Constants;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
{
    private String URL = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
    ArrayList<String> imagesUrls = new ArrayList<>();
    
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
    
    private int mediaPlayerLeftPosition = 0;
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
        mediaPlayerLeftPosition = easyVideoPlayer.getCurrentPosition();
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        easyVideoPlayer.seekTo(mediaPlayerLeftPosition);
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
        
        for (int index = 0; index < 7; index++)
        {
            imagesUrls.add("https://picsum.photos/" + (new Random().nextInt(500) + 1) + "/" + (new Random().nextInt(500) + 1));
        }
        
        rvTeamPlayers.setAdapter(new TeamPlayerCarouselRecyclerViewAdapter(imagesUrls, MainActivity.this));
    }
    
    private void initializingButtons()
    {
        llHome.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!isHomeSelected)
                {
                    isHomeSelected = true;
                    selectView(llHome, tvHome, ivHome);
                    unSelectView(llAway, tvAway, ivAway);
                }
            }
        });
        
        llAway.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!isAwaySelected)
                {
                    isAwaySelected = true;
                    unSelectView(llHome, tvHome, ivHome);
                    selectView(llAway, tvAway, ivAway);
                }
            }
        });
    }
}

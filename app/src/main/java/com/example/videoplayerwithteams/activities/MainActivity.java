package com.example.videoplayerwithteams.activities;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.easyvideoplayer.EasyVideoCallback;
import com.afollestad.easyvideoplayer.EasyVideoPlayer;
import com.afollestad.easyvideoplayer.EasyVideoProgressCallback;
import com.example.videoplayerwithteams.R;
import com.example.videoplayerwithteams.adapters.TeamPlayerCarouselRecyclerViewAdapter;
import com.example.videoplayerwithteams.application.ApplicationData;
import com.example.videoplayerwithteams.constants.Constants;
import com.example.videoplayerwithteams.customeviews.Toolbar;
import com.example.videoplayerwithteams.interfaces.MenuItemClickedListener;
import com.example.videoplayerwithteams.interfaces.ServiceResponseChecker;
import com.example.videoplayerwithteams.models.TeamPlayerResponseModel;
import com.example.videoplayerwithteams.retrofit.CallingWebServices;
import com.example.videoplayerwithteams.utility.Utils;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
{
    private String URL = "http://intigralvod1-vh.akamaihd.net/i/3rdparty/Season2017_2018/10_12_2017_Hilal_fath/Highlights/high_,256,512,768,1200,.mp4.csmil/master.m3u8";
    
    @BindView(R.id.easyVideoPlayer)
    EasyVideoPlayer easyVideoPlayer;
    
    @BindView(R.id.rvTeamPlayers)
    DiscreteScrollView rvTeamPlayers;
    
    @BindView(R.id.rvTeamPlayersHome)
    DiscreteScrollView rvTeamPlayersHome;
    
    @BindView(R.id.rvTeamPlayersAway)
    DiscreteScrollView rvTeamPlayersAway;
    
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
    
    @BindView(R.id.llCarouselsForTab)
    LinearLayout llCarouselsForTab;
    
    @BindView(R.id.surfaceView)
    SurfaceView surfaceView;
    
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    
    private SurfaceHolder vidHolder;
    private MediaController mediaController;
    private Handler handler = new Handler();
    private DisplayMetrics displayMetrics;
    MediaPlayer mediaPlayer;
    
    private int mediaPlayerSelectedPosition = 0;
    private boolean isHomeSelected = false;
    private boolean isAwaySelected = false;
    private boolean isSeekStarted = false;
    private boolean isFullScreen = true;
    private boolean isPlayerItemsShown = false;
    private boolean isTablet = false;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
       
        llCarouselsForTab.setVisibility(View.GONE);
    
        //getting dimensions of screen to check wheather the screen is phone or tablet
        displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float yInches = displayMetrics.heightPixels / displayMetrics.ydpi;
        float xInches = displayMetrics.widthPixels / displayMetrics.xdpi;
        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);
        
        if (diagonalInches >= 7)
        {
            isTablet = true;
            initializeCarouselsForTablets();
        }
        else
        {
            isTablet = false;
            initializeCarouselForPhone();
            initializingCarouselButtonsForPhones();
            llCarousel.setVisibility(View.GONE);
        }
        mediaPlayerSelectedPosition = getIntent().getExtras().getInt(Constants.VIDEO_SEEK_TIME_KEY) * 1000;
        
        //initializing video player, with appropriate settings and given URL to buffer
        initializeVideoPlayer();
        
        //initializing toolbar with icons to toggle full screen and to toggle players
        initToolbar();
    }
    
    private void initToolbar()
    {
        toolbar.setTitle("Video Carousel");
        toolbar.setOnMenuClickedListener(new MenuItemClickedListener()
        {
            @Override
            public void onToggleFullScreenClicked()
            {
                if (isFullScreen)
                {
                    isFullScreen = false;
                    toolbar.ivToggleFullScreen.setImageResource(R.drawable.ic_open_full_screen);
                    int height = 400;
                    int width = 750;
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
                    params.addRule(RelativeLayout.CENTER_IN_PARENT);
                    easyVideoPlayer.setLayoutParams(params);
                }
                else
                {
                    isFullScreen = true;
                    toolbar.ivToggleFullScreen.setImageResource(R.drawable.ic_close_full_screen);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    params.addRule(RelativeLayout.BELOW, R.id.toolbar);
                    easyVideoPlayer.setLayoutParams(params);
                }
            }
            
            @Override
            public void onTogglePlayerItems()
            {
                if (isPlayerItemsShown)
                {
                    isPlayerItemsShown = false;
                    toolbar.ivTogglePlayerItem.setImageResource(R.drawable.ic_open_player_items);
                    if(isTablet)
                    {
                        llCarouselsForTab.startAnimation(outToLeftAnimation());
                    }
                    else
                    {
                        llCarousel.startAnimation(outToLeftAnimation());
                    }
                    
                }
                else
                {
                    isPlayerItemsShown = true;
                    toolbar.ivTogglePlayerItem.setImageResource(R.drawable.ic_close_player_items);
                    if(isTablet)
                    {
                        llCarouselsForTab.setVisibility(View.VISIBLE);
                        llCarouselsForTab.startAnimation(inFromRightAnimation());
                        getTeamPlayersForTablets();
                        
                    }
                    else
                    {
                        llCarousel.setVisibility(View.VISIBLE);
                        llCarousel.startAnimation(inFromRightAnimation());
                    }
                    
                }
            }
        });
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
        if (easyVideoPlayer.isPlaying())
            easyVideoPlayer.seekTo(mediaPlayerSelectedPosition);
    }
    
    private Animation inFromRightAnimation()
    {
        
        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setFillAfter(true);
        inFromRight.setDuration(1200);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
    }
    
    private Animation outToLeftAnimation()
    {
        Animation outtoLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoLeft.setFillAfter(true);
        outtoLeft.setDuration(1200);
        outtoLeft.setInterpolator(new AccelerateInterpolator());
        return outtoLeft;
    }
    
    private void initializeVideoPlayer()
    {
        easyVideoPlayer = (EasyVideoPlayer) findViewById(R.id.easyVideoPlayer);
        easyVideoPlayer.setSource(Uri.parse(URL));
        easyVideoPlayer.setAutoPlay(true);
        easyVideoPlayer.setLoop(true);
        easyVideoPlayer.hideControls();
        easyVideoPlayer.disableControls();
        easyVideoPlayer.setProgressCallback(new EasyVideoProgressCallback()
        {
            @Override
            public void onVideoProgressUpdate(int position, int duration)
            {
                
            }
        });
        easyVideoPlayer.setCallback(new EasyVideoCallback()
        {
            @Override
            public void onStarted(final EasyVideoPlayer player)
            {
                //seekStart();
                //player.start();
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        easyVideoPlayer.seekTo(mediaPlayerSelectedPosition);
                        isSeekStarted = true;
                    }
                }, 500);
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
            public void onPrepared(final EasyVideoPlayer player)
            {
                player.setVolume(0f, 0f);
                
                //player.start();
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
        //seekStart();
    }
    
    private void seekStart()
    {
        while (!isSeekStarted)
        {
            if (easyVideoPlayer.isPlaying())
            {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        easyVideoPlayer.seekTo(mediaPlayerSelectedPosition);
                        isSeekStarted = true;
                    }
                }, 500);
                
            }
        }
    }
    
    private void initializeCarouselForPhone()
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
    
    private void initializeCarouselsForTablets()
    {
        rvTeamPlayersAway.setOffscreenItems(3);
        rvTeamPlayersAway.setItemTransformer(new ScaleTransformer.Builder().setMinScale(0.7f).build());
    
        rvTeamPlayersAway.setHasFixedSize(false);
        rvTeamPlayersAway.setNestedScrollingEnabled(false);
        rvTeamPlayersAway.addOnItemTouchListener(new RecyclerView.OnItemTouchListener()
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
    
        rvTeamPlayersHome.setOffscreenItems(3);
        rvTeamPlayersHome.setItemTransformer(new ScaleTransformer.Builder().setMinScale(0.7f).build());
    
        rvTeamPlayersHome.setHasFixedSize(true);
        rvTeamPlayersHome.setNestedScrollingEnabled(false);
        rvTeamPlayersHome.addOnItemTouchListener(new RecyclerView.OnItemTouchListener()
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
    
    private void initializingCarouselButtonsForPhones()
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
        if (ApplicationData.getInstance().getTeamPlayerResponseModel() == null)
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
    
    private void getTeamPlayersForTablets()
    {
        if (ApplicationData.getInstance().getTeamPlayerResponseModel() == null)
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
                    setTeamsAccordinglyForTablets();
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
            setTeamsAccordinglyForTablets();
        }
    }
    
    private void setTeamsAccordingly()
    {
        if (isHomeSelected)
        {
            rvTeamPlayers.setAdapter(new TeamPlayerCarouselRecyclerViewAdapter(ApplicationData.getInstance().getTeamPlayerResponseModel().getLineups().getData().getHomeTeam().getPlayers(), MainActivity.this));
        }
        else if (isAwaySelected)
        {
            rvTeamPlayers.setAdapter(new TeamPlayerCarouselRecyclerViewAdapter(ApplicationData.getInstance().getTeamPlayerResponseModel().getLineups().getData().getAwayTeam().getPlayers(), MainActivity.this));
        }
    }
    
    private void setTeamsAccordinglyForTablets()
    {
        rvTeamPlayersHome.setAdapter(new TeamPlayerCarouselRecyclerViewAdapter(ApplicationData.getInstance().getTeamPlayerResponseModel().getLineups().getData().getHomeTeam().getPlayers(), MainActivity.this));
        rvTeamPlayersAway.setAdapter(new TeamPlayerCarouselRecyclerViewAdapter(ApplicationData.getInstance().getTeamPlayerResponseModel().getLineups().getData().getAwayTeam().getPlayers(), MainActivity.this));
        rvTeamPlayersHome.scrollToPosition(ApplicationData.getInstance().getTeamPlayerResponseModel().getLineups().getData().getAwayTeam().getPlayers().size() - 1);
    }
    
}

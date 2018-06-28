package com.example.videoplayerwithteams.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.videoplayerwithteams.R;
import com.example.videoplayerwithteams.constants.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LandingActivity extends AppCompatActivity
{
    @BindView(R.id.btnPlayVideo)
    Button btnPlayVideo;
    
    @BindView(R.id.seekBarPlayVideoFrom)
    SeekBar seekBarPlayVideoFrom;
    
    @BindView(R.id.tvMilliSeconds)
    TextView tvMilliSeconds;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        ButterKnife.bind(this);
        bindViews();
    }
    
    private void bindViews()
    {
        seekBarPlayVideoFrom.setMax(Constants.VIDEO_LENGTH_IN_SECONDS);
        seekBarPlayVideoFrom.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                tvMilliSeconds.setText((i) + "");
            }
            
            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
                
            }
            
            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                
            }
        });
        
        btnPlayVideo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intentToMainActivity = new Intent(LandingActivity.this, MainActivity.class);
                intentToMainActivity.putExtra(Constants.VIDEO_SEEK_TIME_KEY, Integer.parseInt(tvMilliSeconds.getText().toString()));
                startActivity(intentToMainActivity);
                finish();
            }
        });
        
    }
}

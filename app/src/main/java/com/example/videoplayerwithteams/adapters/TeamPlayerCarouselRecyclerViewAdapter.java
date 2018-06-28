package com.example.videoplayerwithteams.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.videoplayerwithteams.R;

import java.util.ArrayList;

/**
 * Created by ZeeZee on 11/4/2017.
 */

public class TeamPlayerCarouselRecyclerViewAdapter extends RecyclerView.Adapter<TeamPlayerCarouselRecyclerViewAdapter.FavouriteViewHolder>
{
    ArrayList<String> imagesUrls;
    Context context;
    
    public TeamPlayerCarouselRecyclerViewAdapter(ArrayList<String> imagesUrls, Context context)
    {
        this.imagesUrls = imagesUrls;
        this.context = context;
    }
    
    @Override
    public FavouriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_team_player_item, parent, false);
        return new FavouriteViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(FavouriteViewHolder holder, int position)
    {
        holder.bindView(position);
    }
    
    @Override
    public int getItemCount()
    {
        return imagesUrls.size();
    }
    
    public class FavouriteViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvPlayerName;
        
        public FavouriteViewHolder(View itemView)
        {
            super(itemView);
            tvPlayerName = (TextView) itemView.findViewById(R.id.tvPlayerName);
        }
        
        public void bindView(int position)
        {
            tvPlayerName.setText(imagesUrls.get(position));
        }
    }
}

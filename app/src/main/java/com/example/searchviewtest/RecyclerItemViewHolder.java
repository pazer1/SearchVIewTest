package com.example.searchviewtest;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView mItemTextView;
    private final ImageView mImageView;
    private final ImageView team1;
    private final ImageView team2;
    private final TextView team1_name;
    private final TextView team2_name;
    private final TextView leagueName;

    public RecyclerItemViewHolder(View itemView, TextView mItemTextView, ImageView mImageView, ImageView team1, ImageView team2,TextView team1name,TextView team2name,TextView leagueName) {
        super(itemView);
        this.mItemTextView = mItemTextView;
        this.mImageView = mImageView;
        this.team1 = team1;
        this.team2 = team2;
        this.team1_name = team1name;
        this.team2_name = team2name;
        this.leagueName = leagueName;
    }

    public static RecyclerItemViewHolder newInstance(View parent){
        TextView itemTextView = parent.findViewById(R.id.match_date);
        ImageView imageView = parent.findViewById(R.id.iv_twitch);
        ImageView team1 = parent.findViewById(R.id.team1);
        ImageView team2 = parent.findViewById(R.id.team2);
        TextView team1_name = parent.findViewById(R.id.team1_name);
        TextView team2_name = parent.findViewById(R.id.team2_name);
        TextView leagueText = parent.findViewById(R.id.league_name);
        return new RecyclerItemViewHolder(parent,itemTextView,imageView,team1,team2,team1_name,team2_name,leagueText);
    }

    public void setItemText(CharSequence text){
        mItemTextView.setText(text);
    }
    public void setmImageView(CharSequence t1, CharSequence t2){Glide.with(mItemTextView.getContext()).load(t1).into(team1); Glide.with(mImageView.getContext()).load(t2).into(team2);}
    public void setTeamName(String teamName1, String teamName2){team1_name.setText(teamName1); team2_name.setText(teamName2);}
    public void setLeaguename(String leaguename){leagueName.setText(leaguename);}
}

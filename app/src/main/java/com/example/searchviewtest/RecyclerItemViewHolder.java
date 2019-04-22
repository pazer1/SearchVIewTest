package com.example.searchviewtest;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView mItemTextView;
    private final ImageView mImageView;
    private final ImageView team1;
    private final ImageView team2;

    public RecyclerItemViewHolder(View itemView, TextView mItemTextView, ImageView mImageView, ImageView team1, ImageView team2) {
        super(itemView);
        this.mItemTextView = mItemTextView;
        this.mImageView = mImageView;
        this.team1 = team1;
        this.team2 = team2;
    }

    public static RecyclerItemViewHolder newInstance(View parent){
        TextView itemTextView = parent.findViewById(R.id.itemTextView);
        ImageView imageView = parent.findViewById(R.id.iv_twitch);
        ImageView team1 = parent.findViewById(R.id.team1);
        ImageView team2 = parent.findViewById(R.id.team2);
        return new RecyclerItemViewHolder(parent,itemTextView,imageView,team1,team2);
    }

    public void setItemText(CharSequence text){
        mItemTextView.setText(text);
    }
    public void setmImageView(CharSequence t1, CharSequence t2){Glide.with(mItemTextView.getContext()).load(t1).into(team1); Glide.with(mImageView.getContext()).load(t2).into(team2);}

}

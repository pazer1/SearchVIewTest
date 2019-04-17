package com.example.searchviewtest;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView mItemTextView;
    private final ImageView mImageView;

    public RecyclerItemViewHolder( View itemView, TextView mItemTextView, ImageView mImageView) {
        super(itemView);
        this.mItemTextView = mItemTextView;
        this.mImageView = mImageView;
    }

    public static RecyclerItemViewHolder newInstance(View parent){
        TextView itemTextView = parent.findViewById(R.id.itemTextView);
        ImageView imageView = parent.findViewById(R.id.iv_twitch);
        return new RecyclerItemViewHolder(parent,itemTextView,imageView);
    }

    public void setItemText(CharSequence text){
        mItemTextView.setText(text);
    }


}

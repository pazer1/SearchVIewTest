package com.example.searchviewtest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter {

    private List<String> mItemList;

    public RecyclerAdapter(List<String> mItemList) {
        this.mItemList = mItemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final Context context = viewGroup.getContext();
        final View view = LayoutInflater.from(context).inflate(R.layout.listview_items,viewGroup,false);
        view.findViewById(R.id.iv_twitch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((IsInstalled)(context.getApplicationContext())).isTwitch()){
                    Intent intent = context.getPackageManager().getLaunchIntentForPackage("tv.twitch.android.app");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else{
                    String url = "market://details?id=" + "tv.twitch.android.app";
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    context.startActivity(i);
                }
            }
        });
        return RecyclerItemViewHolder.newInstance(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        RecyclerItemViewHolder holder = (RecyclerItemViewHolder)viewHolder;
        String itemText = mItemList.get(i);
        holder.setItemText(itemText);

    }

    @Override
    public int getItemCount() {
        return mItemList==null?0:mItemList.size();
    }
}

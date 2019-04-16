package com.example.searchviewtest;

import android.content.Context;
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
        Context context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.listview_items,viewGroup,false);
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

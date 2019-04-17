package com.example.searchviewtest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class PartThreeFragment extends Fragment  {

    private final static String ITEMS_COUNT_KEY = "PartThreeFragment&ItmesCount";

    public static PartThreeFragment createInstance(int itemsCount){
        PartThreeFragment partThreeFragment = new PartThreeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY,itemsCount);
        partThreeFragment.setArguments(bundle);
        return partThreeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_part_three,container,false);
        setupRecyclerView(recyclerView);
        return recyclerView;
    }

    private void setupRecyclerView(RecyclerView recyclerView){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(createItemList());
        recyclerView.setAdapter(recyclerAdapter);
    }



    private List<String>createItemList(){
        List<String> itemList = new ArrayList<>();
        Bundle bundle = getArguments();
        if(bundle !=null){
            int itemsCount = bundle.getInt(ITEMS_COUNT_KEY);
            for (int i = 0; i < itemsCount; i++){
                itemList.add("Itme"+i);
            }
        }
        return itemList;
    }


}

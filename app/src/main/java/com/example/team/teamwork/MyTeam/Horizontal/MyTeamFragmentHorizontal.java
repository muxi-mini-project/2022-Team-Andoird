package com.example.team.teamwork.MyTeam.Horizontal;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team.R;
import com.example.team.home_page.home_pagefragment.view.TeamFragment;
import com.example.team.teamwork.MyTeam.Horizontal.HorizontalAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyTeamFragmentHorizontal extends Fragment {

    // 添加存储数据的列表
    //private List<String> mList = new ArrayList<>();
    private Context context;
    private RecyclerView recyclerView;
    private HorizontalAdapter horizontalAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.mt_recyclerview1, container, false);
        recyclerView = view.findViewById(R.id.mt_recyclerview1);
        initData();
        initView();

        return view;
    }

    private void initData() {
        //向list里添加数据
        //mList.add();
        //horizontalAdapter.updateData(mList);
    }

    private void initView() {

        context = this.getActivity();
        horizontalAdapter = new HorizontalAdapter();
        horizontalAdapter.setOnItemClickListener(new HorizontalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(),"click " + position + " item", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getContext(),"long click " + position + " item", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);


        recyclerView.setAdapter(horizontalAdapter);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

    }

}

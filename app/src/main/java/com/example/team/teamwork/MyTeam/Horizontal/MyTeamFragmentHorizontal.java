package com.example.team.teamwork.MyTeam.Horizontal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team.Api;
import com.example.team.R;
import com.example.team.home_page.home_pagefragment.view.TeamFragment;
import com.example.team.team.Bean.UserTeam;
import com.example.team.team.net.TeamAPI;
import com.example.team.teamwork.MyTeam.Horizontal.HorizontalAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyTeamFragmentHorizontal extends Fragment {

    // 添加存储数据的列表
    private List<HorizontalData.UserDTO> mList = new ArrayList<>();
    private RecyclerView recyclerView;
    private HorizontalAdapter horizontalAdapter;
    private Bundle bundle;
    private String team_id, token;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mt_recyclerview1, container, false);
        initRecyclerView();

        bundle = this.getArguments();
        team_id = bundle.getString("team_id");

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Token",0);
        token = sharedPreferences.getString("Token",null);

        initData();

        return view;
    }

    private void initData() {
        Retrofit retrofit = Api.getInstance().getApi();
        //web实例
        TeamAPI teamAPI = retrofit.create(TeamAPI.class);
        //call实例
        Call<HorizontalData> call = teamAPI.usersTeam2(token, team_id);
        call.enqueue(new retrofit2.Callback<HorizontalData>() {
            @Override
            public void onResponse(Call<HorizontalData> call, Response<HorizontalData> response) {
                //拉一个List下来
                HorizontalData data = response.body();
                mList.addAll(data.getUser());
                horizontalAdapter.refresh(mList);
            }
            @Override
            public void onFailure(Call<HorizontalData> call, Throwable t) {
            }
        });

    }

    private void initRecyclerView() {
        recyclerView = view.findViewById(R.id.mt_recyclerview1);
        horizontalAdapter = new HorizontalAdapter(getActivity(),mList);
        recyclerView.setAdapter(horizontalAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

    }

}

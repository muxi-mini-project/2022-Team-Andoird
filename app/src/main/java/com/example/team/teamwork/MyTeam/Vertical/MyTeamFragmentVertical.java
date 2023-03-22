package com.example.team.teamwork.MyTeam.Vertical;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team.Api;
import com.example.team.R;
import com.example.team.team.net.TeamAPI;
import com.example.team.teamwork.MyTeam.Horizontal.HorizontalAdapter;
import com.example.team.teamwork.MyTeam.Horizontal.HorizontalData;
import com.example.team.teamwork.Task.TaskActivity;
import com.example.team.teamwork.net.ProjectAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyTeamFragmentVertical extends Fragment {

    // 添加存储数据的列表
    private List<VerticalData.DataDTO> mList = new ArrayList<>();
    private RecyclerView recyclerView;
    private VerticalAdapter verticalAdapter;
    private Bundle bundle;
    private String team_id, token;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mt_recyclerview2, container, false);
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
        ProjectAPI projectAPI = retrofit.create(ProjectAPI.class);
        //call实例
        Call<VerticalData> call = projectAPI.allProject(token, team_id);
        call.enqueue(new retrofit2.Callback<VerticalData>() {
            @Override
            public void onResponse(Call<VerticalData> call, Response<VerticalData> response) {
                //拉一个List下来
                VerticalData data = response.body();
                mList.addAll(data.getData());
                verticalAdapter.refresh(mList);
            }
            @Override
            public void onFailure(Call<VerticalData> call, Throwable t) {
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = view.findViewById(R.id.mt_recyclerview2);
        verticalAdapter = new VerticalAdapter(getActivity(),mList);
        recyclerView.setAdapter(verticalAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

}







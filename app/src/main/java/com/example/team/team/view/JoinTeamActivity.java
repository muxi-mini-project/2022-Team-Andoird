package com.example.team.team.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.team.Api;
import com.example.team.R;
import com.example.team.StatusBar;
import com.example.team.home_page.home_pagefragment.Callback.Callback2;
import com.example.team.team.Bean.TeamData;
import com.example.team.team.net.TeamAPI;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class JoinTeamActivity extends StatusBar {
    private EditText mTeamCode;
    private String team_coding="1";
    private String token;
    private ImageButton mBackButton;
    private Button mButton;
    private static Callback2 mCallback2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StatusBar_to_transparent(this);
        //状态栏文字自适应
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_team);

        mBackButton = (ImageButton) findViewById(R.id.join_back);

        //返回
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //加入团队
        mTeamCode = (EditText) findViewById(R.id.join_code);
        //大坑！！！！！！！！！！！！！！1
        //team_coding=mTeamCode.getText().toString();

        mButton=findViewById(R.id.join_finish);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                team_coding=mTeamCode.getText().toString();
                WebRequest(team_coding);
            }
        });


        initWidgets();

    }
    private void initWidgets(){
        SharedPreferences sharedPreferences = getSharedPreferences("Token",0);
        token = sharedPreferences.getString("Token",null);
    }
    //网络请求---POST加入团队
    private void WebRequest(String team_coding) {
        //api实例
        Retrofit retrofit = Api.getInstance().getApi();
        //web实例
        TeamAPI mTeam = retrofit.create(TeamAPI.class);

        TeamData.DataDTO teamData=new TeamData.DataDTO(team_coding);
        //call实例
        Call<TeamData> call = mTeam.joinTeam(token,teamData);
        //异步网络请求
        call.enqueue(new retrofit2.Callback<TeamData>() {
            @Override
            public void onResponse(Call<TeamData> call, Response<TeamData> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(JoinTeamActivity.this, "加入团队成功", Toast.LENGTH_SHORT).show();
                    mCallback2.UpdateUI();
                    JoinTeamActivity.this.finish();
                }
                else
                    Toast.makeText(JoinTeamActivity.this, "已加入团队", Toast.LENGTH_SHORT).show();


            }
            @Override
            public void onFailure(Call<TeamData> call, Throwable t) {
                Toast.makeText(JoinTeamActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                //抛出异常
                t.printStackTrace();
                Log.e("tag", t.getMessage());
            }

        });

    }
    public static void setCallback2(Callback2 callback2) {
        mCallback2 = callback2;
    }


}

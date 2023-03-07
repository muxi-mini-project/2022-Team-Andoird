package com.example.team.user.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;

import com.example.team.Api;
import com.example.team.R;
import com.example.team.StatusBar;
import com.example.team.home_page.home_pagefragment.net.InitAPI;
import com.example.team.login.logining.LoginUser;
import com.example.team.user.view.UserActivity;

import retrofit2.Retrofit;

public class ChangeNickActivity extends StatusBar {
    private static final String Extra_Nickname = "nickname";

    private EditText et_nickName;
    private Button btn_return;
    private Button btn_finish;
    private ImageButton btn_clear;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // setCallback(HomePageActivity.this);
        //设置状态栏透明
        StatusBar_to_transparent(this);
        super.onCreate(savedInstanceState);
        //状态栏文字自适应
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.change_nick);
        initWidget();

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_finish.setOnClickListener(view -> {
            String nickName = et_nickName.getText().toString();
            Intent intent = getIntent();
            //携带昵称返回信息
            intent.putExtra(Extra_Nickname,nickName);
            setResult(RESULT_OK,intent);
            finish();
        });

        btn_clear.setOnClickListener(view -> {
            et_nickName.setText("");
        });
    }

    private void initWidget(){
        et_nickName = (EditText) findViewById(R.id.et_nickName);
        //初始化昵称
        String nickname = getIntent().getStringExtra(UserActivity.EXTRA_NICKNAME);
        et_nickName.setText(nickname);
        btn_return = findViewById(R.id.btn_return);
        btn_finish = findViewById(R.id.btn_finish);
        btn_clear = findViewById(R.id.btn_clear);
    }
}

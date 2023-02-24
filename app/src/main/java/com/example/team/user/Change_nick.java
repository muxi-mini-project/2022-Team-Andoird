package com.example.team.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.team.Api;
import com.example.team.R;
import com.example.team.StatusBar;
import com.example.team.home_page.home_pagefragment.net.InitAPI;
import com.example.team.login.logining.LoginUser;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Change_nick extends StatusBar {
    private static final String EXTRA_Nick = "com.example.team.home_page.user.nick2";
    private EditText mChange_nick;
    private ImageButton mImageButton;
    private TextView mTextView;
    private String mNick;
    private static CallBack mCallBack;
    private String token;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // setCallback(HomePageActivity.this);
        //设置状态栏透明
        StatusBar_to_transparent(this);
        super.onCreate(savedInstanceState);
        //状态栏文字自适应
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        setContentView(R.layout.change_nick);
        //拿出token
        initWidgets();

        mNick=getIntent().getStringExtra(EXTRA_Nick);
        mImageButton=(ImageButton) findViewById(R.id.change1);
        mImageButton.setBackgroundResource(R.mipmap.fan_hui);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mChange_nick=(EditText) findViewById(R.id.change2);
        mChange_nick.setText(mNick.toCharArray(),0,mNick.length());

        mTextView=(TextView)findViewById(R.id.change_ok);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNick=mChange_nick.getText().toString();
                WebRequest1(mNick);
                mCallBack.change_Nick(mNick);
                //可结束该Activity
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public static Intent newIntent(Context context, String nick) {
        Intent intent = new Intent(context, Change_nick.class);
        intent.putExtra(EXTRA_Nick,nick);
        return intent;
    }
    //为什么变为静态的可以使用
    public static void setCallBack(CallBack callback){
        mCallBack= callback;
    }

    private void initWidgets() {
        //fragment中得getActivity
        SharedPreferences sharedPreferences2 = Change_nick.this.getSharedPreferences("Token", 0);
        token = sharedPreferences2.getString("Token", null);
    }

    private void WebRequest1(String nickname) {
        //api实例
        Retrofit retrofit = Api.getInstance().getApi();
        //对象，用body
        LoginUser.DataDTO mLoginUser = new LoginUser.DataDTO(nickname);
        //web实例
        InitAPI mInit = retrofit.create(InitAPI.class);
        //call实例
        Call<LoginUser> call = mInit.initNick(token, mLoginUser);
        //异步网络请求
        call.enqueue(new retrofit2.Callback<LoginUser>() {
            @Override
            public void onResponse(Call<LoginUser> call, Response<LoginUser> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Change_nick.this, "信息初始化成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Change_nick.this, "信息初始化失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginUser> call, Throwable t) {
                Toast.makeText(Change_nick.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                //抛出异常
                t.printStackTrace();
                Log.e("tag", t.getMessage());
            }

        });

    }
}

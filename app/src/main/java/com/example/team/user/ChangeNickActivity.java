package com.example.team.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;

import com.example.team.Api;
import com.example.team.R;
import com.example.team.StatusBar;
import com.example.team.home_page.home_pagefragment.net.InitAPI;
import com.example.team.login.logining.LoginUser;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChangeNickActivity extends StatusBar {
    private static final String Extra_Nickname = "nickname";

    private EditText et_nickName;
    private Button btn_return;
    private Button btn_finish;

    private String mNick;

    private String token;

//    public static void actionStart(Context context,String nickName){
//        Intent intent = new Intent(context,ChangeNickActivity.class);
//        intent.putExtra(Extra_Nickname,nickName);
//        context.startActivityForResult()
//    }

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

        //String nickName = getIntent().getStringExtra(EXTRA_NICKNAME);

        btn_return = findViewById(R.id.btn_return);
        btn_return.setBackgroundResource(R.mipmap.fan_hui);
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        et_nickName = (EditText) findViewById(R.id.et_nickName);
        //et_nickName.setText(mNick.toCharArray(),0,mNick.length());

        btn_finish = findViewById(R.id.btn_finish);
        btn_finish.setOnClickListener(view -> {
            String nickName = et_nickName.getText().toString();
            changeNickRequest(nickName);
            Intent intent = getIntent();
            intent.putExtra(Extra_Nickname,nickName);
            setResult(RESULT_OK,intent);
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initWidgets() {
        //fragment中得getActivity
        SharedPreferences sharedPreferences2 = getSharedPreferences("Token", 0);
        token = sharedPreferences2.getString("Token", null);
    }

    private void changeNickRequest(String nickname) {
        //api实例
        Retrofit retrofit = Api.getInstance().getApi();
        //对象，用body
        LoginUser.Data mLoginUser = new LoginUser.Data(nickname);
        //web实例
        InitAPI mInit = retrofit.create(InitAPI.class);
        //call实例
        Call<LoginUser> call = mInit.initNick(token, mLoginUser);
        //异步网络请求
        call.enqueue(new retrofit2.Callback<LoginUser>() {
            @Override
            public void onResponse(Call<LoginUser> call, Response<LoginUser> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ChangeNickActivity.this, "信息初始化成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChangeNickActivity.this, "信息初始化失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginUser> call, Throwable t) {
                Toast.makeText(ChangeNickActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                //抛出异常
                t.printStackTrace();
                Log.e("tag", t.getMessage());
            }
        });
    }
}

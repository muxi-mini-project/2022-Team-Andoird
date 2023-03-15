package com.example.team.begin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.fragment.app.Fragment;

import com.example.team.R;
import com.example.team.StatusBar;
import com.example.team.home_page.HomePageActivity;
import com.example.team.login.logining.LoginActivity;
import com.example.team.team.Bean.UserTeam;


import java.util.ArrayList;
import java.util.List;

public class FirstActivity extends StatusBar{
    private ViewPager mViewPager;
    private List<Fragment> fragmentList;
    private List<UserTeam.Team> teams;
    private int currentIndex = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //设置状态栏透明
        StatusBar_to_transparent(this);
        //状态栏文字自适应
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);

        //如果是初次登录，直接进入主界面
        isFirstLogin();

        mViewPager = (ViewPager) findViewById(R.id.first_vp);
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new Begin1());
        fragmentList.add(new Begin2());
        fragmentList.add(new Begin3());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentPagerAdapter adapter = new MyPagerAdapter(fragmentManager,fragmentList);
        mViewPager.setAdapter(adapter);
    }

    /*
    判断是否为初次登陆
     */
    private void isFirstLogin(){
        SharedPreferences sp = getSharedPreferences(LoginActivity.KEY, Context.MODE_PRIVATE);
        String token = sp.getString(LoginActivity.TOKEN,null);
        Log.d("FirstActivity",token);
        if(token != null){
            HomePageActivity.actionStart(FirstActivity.this);
            finish();
        }
    }

    /*
    切换到下一个fragment
     */
    public void switchNext(){
        currentIndex++;
        mViewPager.setCurrentItem(currentIndex);
    }
}
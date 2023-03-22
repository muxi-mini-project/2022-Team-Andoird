package com.example.team.teamwork.MyTeam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.team.R;
import com.example.team.StatusBar;
import com.example.team.home_page.home_pagefragment.view.TeamFragment;
import com.example.team.teamwork.MyTeam.Horizontal.MyTeamFragmentHorizontal;
import com.example.team.teamwork.MyTeam.Vertical.MyTeamFragmentVertical;
import com.example.team.teamwork.Task.TaskActivity;

public class MyTeamActivity extends StatusBar {
    private ImageButton mt_ib1;
    private ImageButton mt_ib2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //设置状态栏透明
        StatusBar_to_transparent(this);
        //状态栏文字自适应
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_team);

        FragmentManager mt_fm1 = getSupportFragmentManager();
        Fragment mt_fragment1 = mt_fm1.findFragmentById(R.id.mt_fragmentContainerView1);
        if (mt_fragment1 == null) {
            mt_fragment1 = new MyTeamFragmentHorizontal();
            mt_fm1.beginTransaction()
                    .add(R.id.mt_fragmentContainerView1, mt_fragment1)
                    .commit();
        }

        FragmentManager mt_fm2 = getSupportFragmentManager();
        Fragment mt_fragment2 = mt_fm2.findFragmentById(R.id.mt_fragmentContainerView2);
        if (mt_fragment2 == null) {
            mt_fragment2 = new MyTeamFragmentVertical();
            mt_fm2.beginTransaction()
                    .add(R.id.mt_fragmentContainerView2, mt_fragment2)
                    .commit();
        }
        InitView();
    }

    /*初始化控件*/
    private void InitView() {
        mt_ib1 = (ImageButton) findViewById(R.id.mt_ib1);
        mt_ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTeamActivity.this.finish();
            }
        });

        mt_ib2 = (ImageButton) findViewById(R.id.mt_ib2);
        mt_ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MyTeamActivity.this,NewItem.class);
                //需要传入 团队id

                startActivity(intent);
            }
        });
    }

}

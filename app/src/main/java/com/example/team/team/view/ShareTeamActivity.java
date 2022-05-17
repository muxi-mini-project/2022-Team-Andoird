package com.example.team.team.view;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.team.R;
import com.example.team.StatusBar;

public class ShareTeamActivity extends StatusBar {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StatusBar_to_transparent(this);
        super.onCreate(savedInstanceState);
        //状态栏文字自适应
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.create_team2);
    }
}

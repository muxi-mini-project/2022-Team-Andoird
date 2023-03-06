package com.example.team.user.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.team.R;
import com.example.team.StatusBar;

public class AboutActivity extends StatusBar {

    private Button btn_return;

    public static void actionStart(Context context){
        Intent intent = new Intent(context,AboutActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
        //设置状态栏透明
        StatusBar_to_transparent(this);
        //状态栏文字自适应
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        btn_return  = findViewById(R.id.btn_return);
        btn_return.setOnClickListener(view -> {
            finish();
        });
    }
}

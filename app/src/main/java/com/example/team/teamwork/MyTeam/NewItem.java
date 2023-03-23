package com.example.team.teamwork.MyTeam;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.team.R;
import com.example.team.StatusBar;
import com.example.team.teamwork.Bean.CreatPData;
import com.example.team.teamwork.Bean.CreateProject_ok;
import com.example.team.teamwork.net.ProjectAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewItem extends StatusBar {

    private Button mReturn_Button;
    private Button mFinish_Button;
    private EditText mItem_Name;
    private EditText mItem_Introduction;
    private static final String TAG = "NewItem";

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        StatusBar_to_transparent(this);
        //状态栏文字自适应
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_item);

        token = getSharedPreferences("token",0).getString("token",null);

        mReturn_Button = (Button) findViewById(R.id.back_new_item);
        mReturn_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mItem_Name = (EditText) findViewById(R.id.et_item_name_new_item);
        mItem_Introduction = (EditText) findViewById(R.id.et_project_new_item);


        mFinish_Button = (Button) findViewById(R.id.bt_finish_new_item);
        mFinish_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //创建body
                CreatPData body = new CreatPData();
                body.setProject_name(mItem_Name.toString());
                body.setDeadline("ddl");
                body.setStart_time("start_time");
                body.setRemark(mItem_Introduction.toString());
                body.setStep(null);

                //网络请求
                Retrofit retrofit = new Retrofit.Builder().baseUrl("`http://47.96.23.198/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ProjectAPI projectAPI= retrofit.create(ProjectAPI.class);
                Call<CreateProject_ok> call = projectAPI.createProject(token,1,body);
                call.enqueue(new Callback<CreateProject_ok>() {
                    @Override
                    public void onResponse(Call<CreateProject_ok> call, Response<CreateProject_ok> response) {
                        Toast.makeText(NewItem.this,"创建成功",Toast.LENGTH_SHORT);
                        //注销界面
                        NewItem.this.finish();
                    }

                    @Override
                    public void onFailure(Call<CreateProject_ok> call, Throwable t) {
                        Toast.makeText(NewItem.this,"创建失败",Toast.LENGTH_SHORT);
                    }
                });

            }
        });

    }


}

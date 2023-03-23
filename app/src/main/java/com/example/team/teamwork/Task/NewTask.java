package com.example.team.teamwork.Task;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.team.R;
import com.example.team.StatusBar;
import com.example.team.teamwork.Bean.CreateTaskData;
import com.example.team.teamwork.Bean.LookTaskData;
import com.example.team.teamwork.net.ProjectAPI;
import com.example.team.teamwork.net.TaskAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewTask extends StatusBar {

    private Button mReturn;
    private EditText mName;
    private TextView mStartTime;
    private TextView mEndTime;
    private EditText mMember;
    private EditText mRemark;
    private Button mFinish;

    private String token;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        token = getSharedPreferences("token",0).getString("token",null);

        mReturn = findViewById(R.id.back_new_task);
        mName = findViewById(R.id.edit_project_name);
        mStartTime = null;
        mEndTime = null;
        mMember = null;
        mRemark = findViewById(R.id.et_Remark_task);

        mFinish = findViewById(R.id.bt_finish_task);
        mFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //创建body
                CreateTaskData body = new CreateTaskData();
                body.setName(mName.toString());
                body.setStart_time(mStartTime.toString());
                body.setDeadline(mEndTime.toString());
                ArrayList<String> members = new ArrayList<>();
                body.setMember(members);

                //网络请求
                Retrofit retrofit = new Retrofit.Builder().baseUrl("`http://47.96.23.198/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                TaskAPI taskAPI = retrofit.create(TaskAPI.class);
                Call<LookTaskData> call = taskAPI.createTask(token,1,body);


            }
        });
    }
}

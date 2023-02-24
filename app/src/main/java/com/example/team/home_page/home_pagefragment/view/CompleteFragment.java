package com.example.team.home_page.home_pagefragment.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team.Api;
import com.example.team.R;
import com.example.team.home_page.home_pagefragment.Bean.TaskData;
import com.example.team.home_page.home_pagefragment.Callback.Callback4;
import com.example.team.team.Bean.UserTeam;
import com.example.team.teamwork.Bean.LookTaskData;
import com.example.team.teamwork.net.TaskAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CompleteFragment extends Fragment implements Callback4 {
    private List<LookTaskData.TData> task;
    private UserTeam.Team team;
    private String token;
    private RecyclerView mRecyclerView;
    private TaskAdapter mTaskAdapter;
    private static Callback4 mCallback4;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_recycleview1, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview1);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        InCompleteFragment.setCallback(CompleteFragment.this);

        initWidgets();

        WebRequest1(token);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //更新视图层
        //UpdateUI();
    }

    private void UpdateUI() {
        if (mTaskAdapter == null) {
            mTaskAdapter = new TaskAdapter();
            //很重要
            mRecyclerView.setAdapter(mTaskAdapter);
        } else {
            mTaskAdapter.notifyDataSetChanged();
        }
    }

    private class TaskHolder extends RecyclerView.ViewHolder {
        private LookTaskData.TData mData;
        private TextView mTaskName;
        private TextView mTeam;
        private TextView mProject;
        private TextView mDate;
        private ImageButton mComplete;
        private int task_id;

        public TaskHolder(LayoutInflater inflater, ViewGroup container) {
            super(inflater.inflate(R.layout.task2_page, container, false));
            mTaskName = (TextView) itemView.findViewById(R.id.task_page1);
            mTeam = (TextView) itemView.findViewById(R.id.task_page2);
            mProject = (TextView) itemView.findViewById(R.id.task_page3);
            mDate = (TextView) itemView.findViewById(R.id.task_page4);
            mComplete = (ImageButton) itemView.findViewById(R.id.task_page5);
            mComplete.setBackgroundResource(R.mipmap.task2_page);
            mComplete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WebRequest2(token, task_id);
                }
            });
        }

        public void bind(LookTaskData.TData tData) {
            this.mData = tData;
            task_id = mData.getTask_id();
            mTaskName.setText(mData.getName());
            mTeam.setText(mData.getCreatetime());
            mProject.setText(mData.getProject());
            mDate.setText(mData.getDeadline());

        }
    }

    private class TaskAdapter extends RecyclerView.Adapter {
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TaskHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof TaskHolder) {
                LookTaskData.TData tData=task.get(position);
                ((TaskHolder) holder).bind(tData);
            }
        }

        @Override
        public int getItemCount() {
            return task.size();
        }
    }

    private void initWidgets() {
        //fragment中得getActivity
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Token", 0);
        token = sharedPreferences.getString("Token", null);
    }

    //网络请求Get---查看已完成的任务
    private void WebRequest1(String token) {
        //api实例
        Retrofit retrofit = Api.getInstance().getApi();
        //web实例
        TaskAPI taskAPI = retrofit.create(TaskAPI.class);
        //call实例
        Call<TaskData> call = taskAPI.doneList(token);
        //异步网络请求
        call.enqueue(new retrofit2.Callback<TaskData>() {
            @Override
            public void onResponse(Call<TaskData> call, Response<TaskData> response) {
                if (response.isSuccessful()) {
                    //拉一个List下来
                    task = response.body().getTask();
                    UpdateUI();
                } else {
                    showCode(response.body().getCode());
                    Toast.makeText(getActivity(), "搞错了,再来", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<TaskData> call, Throwable t) {
                Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
                //抛出异常
                t.printStackTrace();
                Log.e("tag", t.getMessage());
            }

        });
    }

    //网络请求PUT---完成任务
    private void WebRequest2(String token, int task_id) {
        //api实例
        Retrofit retrofit = Api.getInstance().getApi();
        //web实例
        TaskAPI taskAPI = retrofit.create(TaskAPI.class);
        //call实例
        Call<TaskData> call = taskAPI.cancelTask(token, task_id);
        //异步网络请求
        call.enqueue(new retrofit2.Callback<TaskData>() {
            @Override
            public void onResponse(Call<TaskData> call, Response<TaskData> response) {
                if (response.isSuccessful()) {
                    //拉一个List下来
                    showMsg(response.body().getMessage());
                    WebRequest1(token);
                    mCallback4.UPdate();

                } else {
                    showCode(response.body().getCode());
                    Toast.makeText(getActivity(), "搞错了,再来", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<TaskData> call, Throwable t) {
                Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
                //抛出异常
                t.printStackTrace();
                Log.e("tag", t.getMessage());
            }

        });
    }

    private void showMsg(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    private void showCode(int code) {
        Toast.makeText(getActivity(), code, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void UPdate() {
        WebRequest1(token);
    }
    public static void setCallback(Callback4 callback4) {
        mCallback4 = callback4;
    }

}

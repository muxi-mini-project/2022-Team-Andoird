package com.example.team.home_page.home_pagefragment.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team.R;
import com.example.team.home_page.home_pagefragment.Bean.TaskData;
import com.example.team.home_page.home_pagefragment.Bean.TaskDataLab;

import java.util.List;

public class CompleteFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private TaskAdapter mTaskAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.home_recycleview1,container,false);
        mRecyclerView=(RecyclerView) view.findViewById(R.id.recyclerview1);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        UpdateUI();
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        //更新视图层
        UpdateUI();
    }

    private void UpdateUI(){
        TaskDataLab taskDataLab=TaskDataLab.get(getActivity());
        List<TaskData> taskData=taskDataLab.getTaskData();
        if(mTaskAdapter==null){
            mTaskAdapter=new TaskAdapter(taskData);
            //很重要
            mRecyclerView.setAdapter(mTaskAdapter);
        }else{
            mTaskAdapter.notifyDataSetChanged();
        }
    }

    private class TaskHolder extends RecyclerView.ViewHolder{
            //implements View.OnClickListener{
        private TextView mTaskName;
        private TextView mTeam;
        private TextView mProject;
        private TextView mDate;
        private ImageButton mComplete;

        public TaskHolder(LayoutInflater inflater,ViewGroup container){
        super(inflater.inflate(R.layout.task_page,container,false));

        mTaskName=(TextView) itemView.findViewById(R.id.task_page1);
        mTeam=(TextView) itemView.findViewById(R.id.task_page2);
        mProject=(TextView) itemView.findViewById(R.id.task_page3);
        mDate=(TextView) itemView.findViewById(R.id.task_page4);
        mComplete=(ImageButton) itemView.findViewById(R.id.task_page5);
        mComplete.setBackgroundResource(R.mipmap.home_over);
        }

        //@Override
        //public void onClick(View v) {}
        public void bind(TaskData taskData){

        }
    }
    private class TaskAdapter extends RecyclerView.Adapter{
        private List<TaskData> mTaskData;
        public TaskAdapter(List<TaskData> taskData){
            mTaskData=taskData;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
            return new TaskHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof TaskHolder){
                TaskData taskData=mTaskData.get(position);
                ((TaskHolder)holder).bind(taskData);
            }
        }

        @Override
        public int getItemCount() {
            return mTaskData.size();
        }
    }

}

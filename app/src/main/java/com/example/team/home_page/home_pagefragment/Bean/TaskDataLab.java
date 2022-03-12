package com.example.team.home_page.home_pagefragment.Bean;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskDataLab {
    private static TaskDataLab sTaskDataLab;//为啥是s开头
    private List<TaskData> mTaskData;//泛型类，但不局限于泛型数组（ArrayList）

    //构造器方法
    private TaskDataLab(Context context) {//私有的构造方法？？？
        mTaskData = new ArrayList<>();//泛型类
        //满载100个crime数据的模型层
        for (int i = 0; i < 10; i++) {
        TaskData taskData= new TaskData();
        taskData.setTask("dddd");
        taskData.setProject("sdd");
        mTaskData.add(taskData);//添加
         }
    }

    //getCrimeLab方法
    public static TaskDataLab get(Context context) {//静态方法
        if(sTaskDataLab == null) {
            sTaskDataLab = new TaskDataLab(context);
        }
        return sTaskDataLab;
    }

    //get,返回数组列表
    public List<TaskData> getTaskData() {
        return mTaskData;
    }

    //返回带指定ID的Crime对象
    public TaskData getTaskData(UUID id) {
        for (TaskData taskData : mTaskData) {

            if (taskData.getId().equals(id)) {
                return taskData;
            }
        }
        return null;
    }

}


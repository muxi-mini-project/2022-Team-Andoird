package com.example.team.teamwork.net;

import com.example.team.home_page.home_pagefragment.Bean.TaskData;
import com.example.team.teamwork.Bean.CreateTaskData;
import com.example.team.teamwork.Bean.LookAllTaskData;
import com.example.team.teamwork.Bean.LookTaskData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TaskAPI {
    /**
     * 任务相关
     */
    //查看任务
    @GET("team/task/{task_id}")
    Call<LookTaskData> lookTask(@Header("token") String token, @Path("task_id") int task_id);

    //创建任务
    @POST("team/task/{team_id}")
    Call<LookTaskData> createTask(@Header("token") String token, @Path("team_id") int team_id, @Body CreateTaskData cTask);

    //查看所有项目
    @GET("team/alltask/{team_id}")
    Call<LookAllTaskData> lookAllTask(@Header("token") String token, @Path("team_id") int team_id);


    //查看任务代办
    @GET("mytask/info/todolist")
    Call<TaskData> todoList(@Header("token") String token);

    //查看已完成的任务
    @GET("mytask/info/donelist")
    Call<TaskData> doneList(@Header("token") String token);

    //完成任务
    @PUT("mytask/info/donetask/{task_id}")
    Call<TaskData> doneTask(@Header("token")String token,@Path("task_id") int task_id);

    //取消任务的完成
    @PUT("mytask/info/cancel_done/{task_id}")
    Call<TaskData> cancelTask(@Header("token")String token,@Path("task_id") int task_id);

}

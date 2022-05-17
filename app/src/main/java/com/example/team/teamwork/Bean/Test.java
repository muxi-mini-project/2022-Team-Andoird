package com.example.team.teamwork.Bean;

import android.util.Log;
import android.widget.Toast;

import com.example.team.Api;
import com.example.team.home_page.home_pagefragment.view.TeamFragment;
import com.example.team.home_page.home_pagefragment.view.UserTeamFragment;
import com.example.team.team.Bean.UserTeam;
import com.example.team.team.Bean.UsersTeam;
import com.example.team.team.net.TeamAPI;
import com.example.team.teamwork.net.ProjectAPI;
import com.example.team.teamwork.net.TaskAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Test {
    //ok
    /*
    //网络请求Get---查看任务
    private void WebRequest(int task_id, String token) {
        //api实例
        Retrofit retrofit = Api.getInstance().getApi();
        //web实例
        TaskAPI taskAPI = retrofit.create(TaskAPI.class);
        //call实例
        Call<LookTaskData> call = taskAPI.lookTask(token, task_id);
        //异步网络请求
        call.enqueue(new retrofit2.Callback<LookTaskData>() {
            @Override
            public void onResponse(Call<LookTaskData> call, Response<LookTaskData> response) {
                if (response.isSuccessful()) {
                    //拉一个List下来
                    String mm = response.body().getData().getRemark();
                    String ss = response.body().getData().getStep();

                } else {
                    showMsg(response.body().getMessage());
                    showCode(response.body().getCode());
                    Toast.makeText(getActivity(), "搞错了,再来", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<LookTaskData> call, Throwable t) {
                Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
                //抛出异常
                t.printStackTrace();
                Log.e("tag", t.getMessage());
            }

        });
    }
    */
    //ok
    /*
    //7个变量参数
    //必须得new一个对象出来才可以set
    private CreateTaskData mCTask;
    mCTask=new CreateTaskData();
    mCTask.setDeadline("2022-11-31 19:08:06");
    mCTask.setRemark("产品组展示产品");
    mCTask.setStep("报名");
    ArrayList<String> ss = new ArrayList<>(1);
    ss.add("宁先生");
    mCTask.setMember(ss);
    mCTask.setStart_time("2018-11-30 19:08:06");
    mCTask.setProject("项目10号");
    mCTask.setName("展示木犀产品发布");

    //网络请求POST---创建任务
    private void WebRequest2(int team_id, String token, CreateTaskData cTask) {
        //api实例
        Retrofit retrofit = Api.getInstance().getApi();
        //web实例
        TaskAPI taskAPI = retrofit.create(TaskAPI.class);
        //call实例
        Call<LookTaskData> call = taskAPI.createTask(token, team_id, cTask);
        //异步网络请求
        call.enqueue(new retrofit2.Callback<LookTaskData>() {
            @Override
            public void onResponse(Call<LookTaskData> call, Response<LookTaskData> response) {
                if (response.isSuccessful()) {
                    //拉一个List下来
                    int mm = response.body().getTask_id();
                    String ss = response.body().getMessage();

                } else {
                    Toast.makeText(getActivity(), "搞错了,再来", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<LookTaskData> call, Throwable t) {
                Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
                //抛出异常
                t.printStackTrace();
                Log.e("tag", t.getMessage());
            }

        });
    }*/
    //ok
    /*
    //网络请求Get---查看所有项目
    private void WebRequest(String token, int team_id) {
        //api实例
        Retrofit retrofit = Api.getInstance().getApi();
        //web实例
        ProjectAPI projectAPI = retrofit.create(ProjectAPI.class);
        //call实例
        Call<AllProject> call = projectAPI.lookAllProject(token, team_id);
        //异步网络请求
        call.enqueue(new retrofit2.Callback<AllProject>() {
            @Override
            public void onResponse(Call<AllProject> call, Response<AllProject> response) {
                if (response.isSuccessful()) {
                    //拉一个List下来
                    List<AllProject.PData> data = response.body().getData();
                    int code = response.body().getCode();


                } else {
                    showMsg(response.body().getMessage());
                    showCode(response.body().getCode());
                    Toast.makeText(getActivity(), "搞错了,再来", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<AllProject> call, Throwable t) {
                Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
                //抛出异常
                t.printStackTrace();
                Log.e("tag", t.getMessage());
            }

        });
    }*/
    //ok
    /*
    //查看项目
    private void WebRequest2(String token, int project_id) {
        //api实例
        Retrofit retrofit = Api.getInstance().getApi();
        //web实例
        ProjectAPI projectAPI = retrofit.create(ProjectAPI.class);
        //call实例
        Call<LookProjectData> call = projectAPI.lookProject(token,project_id);
        //异步网络请求
        call.enqueue(new retrofit2.Callback<LookProjectData>() {
            @Override
            public void onResponse(Call<LookProjectData> call, Response<LookProjectData> response) {
                if (response.isSuccessful()) {
                    //拉一个List下来
                    AllProject.PData data  = response.body().getData();
                    int code = response.body().getCode();


                } else {
                    showMsg(response.body().getMessage());
                    showCode(response.body().getCode());
                    Toast.makeText(getActivity(), "搞错了,再来", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<LookProjectData> call, Throwable t) {
                Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
                //抛出异常
                t.printStackTrace();
                Log.e("tag", t.getMessage());
            }

        });
    }*/
    /*
    CreatPData pData=new CreatPData();
    pData.setRemark("参加比赛");
    pData.setProject_name("木犀安卓的app")
    pData.setDeadline("2092-04-05 03:27:23");
    pData.setStart_time("1992-04-05 03:27:23");
    ArrayList<String> sss = new ArrayList<>(1);
    sss.add("写app");
    sss.add("摆烂");
    pData.setStep(sss);
    WebRequest2(token,2,pData);
    //网络请求POST---创建项目
    private void WebRequest2(String token,int team_id,  CreatPData mProject) {
        //api实例
        Retrofit retrofit = Api.getInstance().getApi();
        //web实例
        ProjectAPI projectAPI = retrofit.create(ProjectAPI.class);
        //call实例
        Call<CreateProject_ok> call = projectAPI.createProject(token, team_id, mProject);
        //异步网络请求
        call.enqueue(new retrofit2.Callback<CreateProject_ok>() {
            @Override
            public void onResponse(Call<CreateProject_ok> call, Response<CreateProject_ok> response) {
                if(response.isSuccessful()) {
                    //拉一个List下来
                    int mm = response.body().getCode();
                    String ss = response.body().getMessage();
                    int xx=response.body().getProject_id();

                }
                else {
                    Toast.makeText(getActivity(), "搞错了,再来", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<CreateProject_ok> call, Throwable t) {
                Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
                //抛出异常
                t.printStackTrace();
                Log.e("tag", t.getMessage());
            }

        });
    }*/
}

package com.example.team.teamwork.net;

import com.example.team.teamwork.Bean.AllProject;
import com.example.team.teamwork.Bean.CreatPData;
import com.example.team.teamwork.Bean.CreateProject_ok;
import com.example.team.teamwork.Bean.CreateTaskData;
import com.example.team.teamwork.Bean.LookProjectData;
import com.example.team.teamwork.Bean.LookTaskData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProjectAPI {
    /**
     * 项目
     */
    //查看所有的项目
    @GET("team/allproject/{team_id}")
    Call<AllProject> lookAllProject(@Header("token") String token, @Path("team_id") int team_id);

    //查看项目
    @GET("team/project/{project_id}")
    Call<LookProjectData> lookProject(@Header("token") String token, @Path("project_id") int project_id);

    //创建项目
    @POST("team/project/{team_id}")
    Call<CreateProject_ok> createProject(@Header("token")String token, @Path("team_id") int team_id, @Body CreatPData cProject);

}

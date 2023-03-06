package com.example.team.network;

import com.example.team.login.logining.LoginResponse;
import com.example.team.login.logining.LoginUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Api {
    @POST("login")  //登陆
    Call<LoginResponse> postLogin(@Body LoginUser.Data loginUser);

    @GET("/user/info")
    Call<LoginUser> getUserInfo(@Header("token") String token);
}

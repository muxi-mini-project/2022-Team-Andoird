package com.example.team.home_page.home_pagefragment.net;

import android.graphics.Bitmap;

import com.example.team.login.logining.LoginUser;

import java.io.File;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface InitAPI {
    /**
     * 初始化用户信息
     * nickname
     * 注解
     */
    @POST("user/pupup")
    Call<LoginUser> initNick(@Header("token")String token, @Body LoginUser.Data loginUser);

    //得到用户信息
    @GET("user/info")
    Call<LoginUser> getNick(@Header("token")String token);

    //传入头像
    @Multipart
    @POST("user/pupup/avatar")
    Call<LoginUser> initAvatar(@Header("token") String token, @Part MultipartBody.Part file);
}

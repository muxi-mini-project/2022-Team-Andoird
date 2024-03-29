package com.example.team.network;

import com.example.team.login.logining.LoginResponse;
import com.example.team.login.logining.LoginUser;
import com.example.team.user.bean.UploadBody;
import com.example.team.user.bean.UploadResponse;

import java.io.File;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface Api {
    @POST("login")  //登陆
    Call<LoginResponse> postLogin(@Body LoginUser.Data loginUser);

    @GET("user/info")  //查询用户信息
    Call<LoginUser> getUserInfo();

    @PUT("user/info") //修改昵称
    Call<UploadResponse> changeNickname(@Body LoginUser.Data user);

    @Multipart
    @POST("user/pupup/avatar")
    Call<UploadResponse> changeAvatar(@Part MultipartBody.Part image);
}

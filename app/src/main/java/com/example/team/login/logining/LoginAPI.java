package com.example.team.login.logining;



import retrofit2.Call;//不加这个可能LoginResponse会出错
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginAPI {
    /**
     * 获取用户信息
     * 登录
     * 注解
     */

    @POST("login")
    Call<LoginResponse> postLogin(@Body LoginUser loginUser);
}

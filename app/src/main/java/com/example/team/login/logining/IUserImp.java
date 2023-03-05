package com.example.team.login.logining;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.team.network.Api;
import com.example.team.home_page.HomePageActivity;
import com.example.team.network.ServiceCreator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class IUserImp implements IUserModel{
    @Override
    public void login(String student_id, String password, OnLoginListener listener) {
        //Retrofit retrofit = ServiceCreator.create(Api.class);
        //LoginAPI loginService = retrofit.create(LoginAPI.class);
        com.example.team.network.Api loginApi = ServiceCreator.create(Api.class);
        LoginUser.Data student = new LoginUser.Data(student_id,password);
        Call<LoginResponse> call = loginApi.postLogin(student);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }
}

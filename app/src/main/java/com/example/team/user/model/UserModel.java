package com.example.team.user.model;

import android.util.Log;

import com.example.team.login.logining.LoginUser;
import com.example.team.network.Api;
import com.example.team.network.ServiceCreator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserModel {
    private LoginUser user;
    //private String token;

    public void getUserinfo(String token,requestListener listener){
        Api userInfoApi = ServiceCreator.create(Api.class);
        Call<LoginUser> call = userInfoApi.getUserInfo(token);
        call.enqueue(new Callback<LoginUser>() {
            @Override
            public void onResponse(Call<LoginUser> call, Response<LoginUser> response) {
                Log.d("UserModel","load success");
                user = response.body();
                Log.d("UserModel",""+(user==null));
                listener.onSuccess();
            }

            @Override
            public void onFailure(Call<LoginUser> call, Throwable t) {
                Log.d("UserModel","load failed");
                t.printStackTrace();
                listener.onFailed();
            }
        });
    }

    public LoginUser getUser(){
        return user;
    }

}

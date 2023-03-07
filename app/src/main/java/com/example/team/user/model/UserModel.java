package com.example.team.user.model;

import android.util.Log;

import com.example.team.login.logining.LoginUser;
import com.example.team.network.Api;
import com.example.team.network.ServiceCreator;
import com.example.team.user.bean.UploadBody;
import com.example.team.user.bean.UploadResponse;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserModel {
    private LoginUser user;


    public void getUserinfo(requestListener listener){
        Api userInfoApi = ServiceCreator.create(Api.class);
        Call<LoginUser> call = userInfoApi.getUserInfo();
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

    public void setNickRequest(String nickName){
        Api setNickApi = ServiceCreator.create(Api.class);
        LoginUser.Data user = new LoginUser.Data(nickName);
        Call<UploadResponse> call = setNickApi.changeNickname(user);
        call.enqueue(new Callback<UploadResponse>(){
            @Override
            public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                Log.d("UserModel","nickName upload success");
            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {
                Log.d("UserModel","nickName upload failed");
                t.printStackTrace();
            }
        });
    }

    public void setAvatar(File imageFile){
        Api setAvatarApi = ServiceCreator.create(Api.class);

        Call<UploadResponse> call = setAvatarApi.changeAvatar(new UploadBody(imageFile));
        call.enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                Log.d("UserModel","avatar upload success");
            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {
                Log.d("UserModel","avatar upload failed");
                t.printStackTrace();
            }
        });
    }
}

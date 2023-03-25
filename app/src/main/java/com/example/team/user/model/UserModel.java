package com.example.team.user.model;

import android.net.Uri;
import android.util.Log;

import com.example.team.R;
import com.example.team.login.logining.LoginUser;
import com.example.team.network.Api;
import com.example.team.network.ServiceCreator;
import com.example.team.user.bean.UploadBody;
import com.example.team.user.bean.UploadResponse;

import java.io.File;
import java.net.URI;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
                //File file = new File("");
                //Log.d("UserModel",""+(user==null));
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

    /*
    上传昵称请求
     */
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

    /*
    上传头像请求
     */
    public void setAvatarRequest(File file){
        Api setAvatarApi = ServiceCreator.create(Api.class);
        //File file = new File("test.txt");
        //File file =
        //Log.d("UserModel",imageUri.toString());

        //file = new File("reg_bg_min.jpg");

        //Log.d("UserModel","file"+file.toString());
        //file = new File();

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);

        //file = new File("/reg_bg_min.jpg");
        //RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"),file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file",file.getName(),requestBody);
        Call<UploadResponse> call = setAvatarApi.changeAvatar(part);
        //Log.d("UserModel",System.getProperty("user.dir"));

        call.enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                //Log.d("UserModel",imageFile.toString());
                Log.d("UserModel","avatar upload success");
                Log.d("UserModel",response.toString());
                UploadResponse res = response.body();
                Log.d("UserModel",res.getUrl());
            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {
                Log.d("UserModel","avatar upload failed");
                t.printStackTrace();
                Log.d("UserModel",t.toString());
            }
        });
    }
}

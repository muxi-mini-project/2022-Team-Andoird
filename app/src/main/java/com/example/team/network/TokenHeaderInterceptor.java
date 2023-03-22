package com.example.team.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.team.MyApplication;
import com.example.team.login.logining.LoginActivity;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TokenHeaderInterceptor implements Interceptor{

    @Override
    public Response intercept(Chain chain) throws IOException {
        Context context = MyApplication.context;
        SharedPreferences sp = context.getSharedPreferences("team",Context.MODE_PRIVATE);
        String token = sp.getString("token",null);
        Log.d("TokenHeaderInterceptor","token:"+token);
        //String token = new LoginActivity().restoreData("token");
        if(token == null){
            Request request = chain.request();
            return chain.proceed(request);
        }else{
            Request request = chain.request();
            Request newRequest = request.newBuilder().addHeader("token",token).build();
            return chain.proceed(newRequest);
        }
    }
}

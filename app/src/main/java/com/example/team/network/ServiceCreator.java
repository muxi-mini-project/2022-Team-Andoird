package com.example.team.network;

import android.content.SharedPreferences;

import com.example.team.login.logining.LoginActivity;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceCreator {
    private static final String BASE_URL = "http://47.96.23.198:8463/";
    //Retrofit对象，只存在一个实例
    private static Retrofit retrofit;

    //获取Retrofit实例
    private static Retrofit getRetrofit(){
        if(retrofit != null){
            return retrofit;
        }else{
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            return retrofit;
        }
    }

    //通过传入接口获取该接口的动态代理对象
    public static <T> T create(Class<T> serviceClass){
        return getRetrofit().create(serviceClass);
    }

    public static OkHttpClient getClient(){
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(15, TimeUnit.SECONDS);
        httpClientBuilder.addInterceptor(new TokenHeaderInterceptor());
        return httpClientBuilder.build();
    }
}

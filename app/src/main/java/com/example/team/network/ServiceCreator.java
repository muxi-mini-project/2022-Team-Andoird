package com.example.team.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceCreator {
    private static final String BASE_URL = "http://47.96.23.198:8463";
    private static Retrofit retrofit;

    private static Retrofit getRetrofit(){
        if(retrofit != null){
            return retrofit;
        }else{
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            return retrofit;
        }
    }

    public static <T> T create(Class<T> serviceClass){
        return getRetrofit().create(serviceClass);
    }
}

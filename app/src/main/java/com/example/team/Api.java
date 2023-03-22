package com.example.team;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    private Retrofit api;
    private static Api INSTANCE = new Api();

    private Api() {
        api = new Retrofit.Builder()
                .baseUrl("http://47.96.23.198:8463")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

//    private static class NetUtilHolder {
//        private static Api INSTANCE = new Api();
//    }

    public static Api getInstance() {
        return INSTANCE;
    }

    public Retrofit getApi() {
        return api;
    }
}

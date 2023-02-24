package com.example.team;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    private Retrofit api;

    private Api() {
        api = new Retrofit.Builder()
                .baseUrl("http://119.3.2.168:8463/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static class NetUtilHolder {
        private static Api INSTANCE = new Api();
    }

    public static Api getInstance() {
        return NetUtilHolder.INSTANCE;
    }

    public Retrofit getApi() {
        return api;
    }
}

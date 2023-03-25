package com.example.team;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
    public static Context context;
    public static List<AppCompatActivity> activityList;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        activityList = new ArrayList<>();
    }

    public static void addActivity(AppCompatActivity activity){
        activityList.add(activity);
    }

    public void destroyAllActivity(){
        for(AppCompatActivity a:activityList){
            if(a!=null){
                a.finish();
            }
        }
    }

    public static String restoreData(String key){
        SharedPreferences sp = context.getSharedPreferences("team",Context.MODE_PRIVATE);
        String token = sp.getString("token",null);
        return token;
    }
}

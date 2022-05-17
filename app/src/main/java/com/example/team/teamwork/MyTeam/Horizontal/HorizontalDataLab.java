package com.example.team.teamwork.MyTeam.Horizontal;

import android.content.Context;

import com.example.team.home_page.home_pagefragment.Bean.TaskData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class HorizontalDataLab {
    private static HorizontalDataLab sHorizontalDataLab;
    private List<HorizontalData> mHorizontalData;//泛型类，但不局限于泛型数组（ArrayList）

    //构造器方法
    private HorizontalDataLab(Context context) {//私有的构造方法
        mHorizontalData = new ArrayList<>();//泛型类

        //满载100个crime数据的模型层
        for (int i = 0; i < 10; i++) {
            HorizontalData horizontalData= new HorizontalData();
            mHorizontalData.add(horizontalData);//添加
        }
    }

    //getCrimeLab方法
    public static HorizontalDataLab get(Context context) {//静态方法
        if( sHorizontalDataLab == null) {
            sHorizontalDataLab = new HorizontalDataLab(context);
        }
        return sHorizontalDataLab;
    }

    //get,返回数组列表
    public List<HorizontalData> getHorizontalData() {
        return mHorizontalData;
    }

    //返回带指定ID的Crime对象
    public HorizontalData getHorizontalData(UUID id) {
        for (HorizontalData horizontalData : mHorizontalData) {
            if (horizontalData.getId().equals(id)) {
                return horizontalData;
            }
        }
        return null;
    }

}


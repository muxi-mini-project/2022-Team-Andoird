package com.example.team.teamwork.MyTeam.Vertical;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class VerticalDataLab {
    private static VerticalDataLab sVerticalDataLab;
    private List<VerticalData> mVerticalData;//泛型类，但不局限于泛型数组（ArrayList）

    //构造器方法
    private VerticalDataLab(Context context) {//私有的构造方法
        mVerticalData = new ArrayList<>();//泛型类
        //满载100个crime数据的模型层
        for (int i = 0; i < 10; i++) {
            VerticalData verticalData= new VerticalData();
            verticalData.setTeam("皮卡丘");
            verticalData.setProject("魔力球");
            verticalData.setProjectIntroduction("这是一个没有灵魂的项目介绍");
            mVerticalData.add(verticalData);//添加单例（？）
        }
    }

    //getCrimeLab方法
    public static VerticalDataLab get(Context context) {//静态方法
        if(sVerticalDataLab == null) {
            sVerticalDataLab = new VerticalDataLab(context);
        }
        return sVerticalDataLab;
    }

    //get,返回数组列表
    public List<VerticalData> getVerticalData() {
        return mVerticalData;
    }

    //返回带指定ID的Crime对象
    public VerticalData getTaskData(UUID id) {
        for (VerticalData verticalData : mVerticalData) {

            if (verticalData.getId().equals(id)) {
                return verticalData;
            }
        }
        return null;
    }

}


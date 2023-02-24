package com.example.team.teamwork.Task;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class TaskFragmentPagerAdapter extends FragmentPagerAdapter{

    private List<Fragment> mList;

    public TaskFragmentPagerAdapter(FragmentManager fm,List<Fragment> list){
        super(fm);
        this.mList = list;
    }

    @Override
    public Fragment getItem(int arg0){
        return mList.get(arg0); //显示第几个页面
    }

    @Override
    public int getCount(){
        return mList.size();    //有几个页面
    }

}

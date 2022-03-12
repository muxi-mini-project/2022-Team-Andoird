package com.example.team.home_page;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.team.R;
import com.example.team.home_page.home_pagefragment.view.CompleteFragment;
import com.example.team.home_page.home_pagefragment.view.InCompleteFragment;
import com.example.team.home_page.home_pagefragment.view.TeamFragment;
import com.example.team.home_page.home_pagefragment.view.WholeFragment;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity implements View.OnClickListener{
    private ViewPager mViewPager;
    private ShapeableImageView mHead;
    private TextView mNickname;
    //private TextView mTeamName;
    private ImageView mAdd;

    private ImageButton mwancheng;
    private ImageButton mweiwancheng;
    private ImageButton mquanbu;
    private List<Fragment> mList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_1);

        //HeadPart
        mHead=(ShapeableImageView)findViewById(R.id.home_page1);
        mNickname=(TextView)findViewById(R.id.home_page2);
        //mTeamName=(TextView)findViewById(R.id.home_page3);
        mAdd=(ImageButton)findViewById(R.id.home_page3);
        mAdd.setBackgroundResource(R.mipmap.jia_hao);


        //TeamFragment
        FragmentManager fm=getSupportFragmentManager();
        Fragment fragment2=fm.findFragmentById(R.id.fragment_container);
        if (fragment2 == null) {
            fragment2 = new TeamFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment2)
                    .commit();
        }

        //ViewPager
        mweiwancheng=(ImageButton) findViewById(R.id.home_page4);
        mweiwancheng.setBackgroundResource(R.mipmap.weiwancheng1);
        mweiwancheng.setOnClickListener(this);

        mwancheng=(ImageButton) findViewById(R.id.home_page5);
        mwancheng.setBackgroundResource(R.mipmap.wancheng2);
        mwancheng.setOnClickListener(this);

        mquanbu=(ImageButton) findViewById(R.id.home_page6);
        mquanbu.setBackgroundResource(R.mipmap.quabu2);
        mquanbu.setOnClickListener(this);

        mViewPager = (ViewPager) findViewById(R.id.home_page7);
        mList = new ArrayList<>();
        mList.add(new InCompleteFragment());
        mList.add(new CompleteFragment());
        mList.add(new WholeFragment());


        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                //返回的是一个实例（fragment实例或者.......）
                //可以拿一个泛型数组来实现
                return mList.get(position);
            }

            @Override
            public int getCount() {
                return mList.size();
            }
            //删除container中指定下标的视图
            //?????是否需要
           /** @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object){
                Fragment fragment = mList.get(position);
                fragmentManager.beginTransaction().hide(fragment).commit();
            }*/
        });
        //设置当前页面
        mViewPager.setCurrentItem(0);
        //设置最多跳转
        mViewPager.setOffscreenPageLimit(2);
        //点击
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        mweiwancheng.setBackgroundResource(R.mipmap.weiwancheng1);
                        mwancheng.setBackgroundResource(R.mipmap.wancheng2);
                        mquanbu.setBackgroundResource(R.mipmap.quabu2);
                        break;
                    case 1:
                        mweiwancheng.setBackgroundResource(R.mipmap.weiwancheng2);
                        mwancheng.setBackgroundResource(R.mipmap.wancheng1);
                        mquanbu.setBackgroundResource(R.mipmap.quabu2);

                        break;
                    case 2:
                        mweiwancheng.setBackgroundResource(R.mipmap.weiwancheng2);
                        mwancheng.setBackgroundResource(R.mipmap.wancheng2);
                        mquanbu.setBackgroundResource(R.mipmap.quanbu1);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    //要有implements View.OnClickListener
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_page4:
                //点击"健康"时切换到第一页
                mViewPager.setCurrentItem(0);
                mweiwancheng.setBackgroundResource(R.mipmap.weiwancheng1);
                mwancheng.setBackgroundResource(R.mipmap.wancheng2);
                mquanbu.setBackgroundResource(R.mipmap.quabu2);
                break;
            case R.id.home_page5:
                //点击“运动”时切换到第二页
                mViewPager.setCurrentItem(1);
                mweiwancheng.setBackgroundResource(R.mipmap.weiwancheng2);
                mwancheng.setBackgroundResource(R.mipmap.wancheng1);
                mquanbu.setBackgroundResource(R.mipmap.quabu2);
                break;
            case R.id.home_page6:
                //点击“学习”时切换到第三页
                mViewPager.setCurrentItem(2);
                mweiwancheng.setBackgroundResource(R.mipmap.weiwancheng2);
                mwancheng.setBackgroundResource(R.mipmap.wancheng2);
                mquanbu.setBackgroundResource(R.mipmap.quanbu1);
                break;
        }
    }

}

package com.example.team;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.fragment.app.Fragment;

import com.example.team.begin.Begin1;
import com.example.team.begin.Begin2;
import com.example.team.begin.Begin3;
import com.example.team.begin.Current;
import com.example.team.home_page.HomePageActivity;
import com.example.team.team.Bean.UserTeam;


import java.util.ArrayList;
import java.util.List;

public class FirstActivity extends StatusBar implements Current {
    private ViewPager mViewPager;
    private List<Fragment> mList;
    private int mCurrent;
    private String token;
    private List<UserTeam.Team> teams;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //设置状态栏透明
        StatusBar_to_transparent(this);
        //状态栏文字自适应
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);

        //获得SharedPreferences，并创建文件名为saved
        SharedPreferences sp = getSharedPreferences("Token", 0);

        //判断是否为初次登录
        token = sp.getString("Token", null);
        if (token != null) {
            Intent intent = new Intent(FirstActivity.this, HomePageActivity.class);
            //intent.putExtra("EXTRA_UserTeam", (Serializable) teams);
            startActivity(intent);
            FirstActivity.this.finish();
        }

        mViewPager = (ViewPager) findViewById(R.id.first_vp);
        mList = new ArrayList<>();
        Begin1.setCurrent1(FirstActivity.this);
        Begin2.setCurrent2(FirstActivity.this);
        mList.add(new Begin1());
        mList.add(new Begin2());
        mList.add(new Begin3());

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return mList.get(position);
            }

            @Override
            public int getCount() {
                return mList.size();
            }
            //删除container中指定下标的视图
            //?????是否需要
            /** @Override public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object){
            Fragment fragment = mList.get(position);
            fragmentManager.beginTransaction().hide(fragment).commit();
            }*/
        });
        //设置当前页面
        mCurrent = 0;
        mViewPager.setCurrentItem(mCurrent);
        //设置最多跳转
        mViewPager.setOffscreenPageLimit(2);
        //点击
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //当前值
                mCurrent = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void add_current() {
        mCurrent++;
        mViewPager.setCurrentItem(mCurrent);

    }
}

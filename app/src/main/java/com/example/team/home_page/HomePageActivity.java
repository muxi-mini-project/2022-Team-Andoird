package com.example.team.home_page;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.team.Api;
import com.example.team.R;
import com.example.team.StatusBar;
import com.example.team.home_page.home_pagefragment.Callback.Callback;
import com.example.team.home_page.home_pagefragment.Callback.Callback3;
import com.example.team.home_page.home_pagefragment.view.CompleteFragment;
import com.example.team.home_page.home_pagefragment.view.InCompleteFragment;
import com.example.team.home_page.home_pagefragment.net.InitAPI;
import com.example.team.home_page.home_pagefragment.view.InitFragment;
import com.example.team.home_page.home_pagefragment.view.TeamFragment;
import com.example.team.home_page.home_pagefragment.view.UserTeamFragment;
import com.example.team.home_page.home_pagefragment.view.WholeFragment;
import com.example.team.login.login_ok.utils.BitmapUtils;
import com.example.team.login.login_ok.utils.CameraUtils;
import com.example.team.login.logining.LoginUser;
import com.example.team.team.Bean.UserTeam;
import com.example.team.team.view.CreateActivity;
import com.example.team.team.view.CreateTeamActivity;
import com.example.team.team.view.JoinTeamActivity;
import com.example.team.user.view.UserActivity;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomePageActivity extends StatusBar implements View.OnClickListener, Callback, Callback3 {
    private static final String EXTRA_UserTeam = "com.example.team.home_page.first_activity.team";
    //保存着Data;
    private String token;
    private ViewPager mViewPager;
    //头像
    private ShapeableImageView mShapeableImageView;

    //通过回调来获取信息
    private String image;
    //昵称
    private TextView mNickname;
    private String mNick;
    private String name;
    private String avatar;
    //PopupWindow
    private ImageView mAdd;

    private TextView mCreate;
    private TextView mAdd2;


    private ImageButton mwancheng;
    private ImageButton mweiwancheng;
    private ImageButton mquanbu;
    private List<Fragment> mList;
    private InitFragment mInitFragment;
    private UserTeamFragment mUserTeamFragment;
    private static final String INIT_DIALOG = "Init_dialog";
    private List<UserTeam.Team> mTeams;
    //Base64
    private String base64Pic;
    //拍照和相册获取图片的Bitmap
    private Bitmap orc_bitmap;
    //Glide请求图片选项配置
    private RequestOptions requestOptions = RequestOptions.circleCropTransform()
            .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
            .skipMemoryCache(true);//不做内存缓存

    public static void actionStart(Context context){
        Intent intent = new Intent(context,HomePageActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        //设置状态栏透明
        StatusBar_to_transparent(this);
        //状态栏文字自适应
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_1);

        //HeadPart
        mShapeableImageView = (ShapeableImageView) findViewById(R.id.home_page1);
        mShapeableImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this,UserActivity.class);
                //Intent intent = UserActivity.newIntent(HomePageActivity.this, image, mNick);
                //变成静态的
                //UserActivity.setCallback(HomePageActivity.this);
                startActivity(intent);
            }
        });
        mNickname = (TextView) findViewById(R.id.home_page2);
        //mTeamName=(TextView)findViewById(R.id.home_page3);
        mAdd = (ImageButton) findViewById(R.id.home_page3);
        mAdd.setBackgroundResource(R.mipmap.jia_hao);
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopWindow(v);
            }
        });


        //TeamFragment
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment2 = fm.findFragmentById(R.id.fragment_container);
        if (fragment2 == null) {
            //把teams给fragment
            fragment2 = TeamFragment.newInstance(mTeams);
            //Callback,来调用HomePager的方法
            TeamFragment.setCallback(HomePageActivity.this);
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment2)
                    .commit();
        }

        //ViewPager
        mweiwancheng = (ImageButton) findViewById(R.id.home_page4);
        mweiwancheng.setBackgroundResource(R.mipmap.weiwancheng1);
        mweiwancheng.setOnClickListener(this);

        mwancheng = (ImageButton) findViewById(R.id.home_page5);
        mwancheng.setBackgroundResource(R.mipmap.wancheng2);
        mwancheng.setOnClickListener(this);

        mquanbu = (ImageButton) findViewById(R.id.home_page6);
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
            /** @Override public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object){
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
                switch (position) {
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

        initWidgets();
        WebRequest();

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

    private void init_dialog() {
        FragmentManager fm = getSupportFragmentManager();
        mInitFragment = InitFragment.newInstance();
        //不需要目标fragment,因为可以判断activity是哪个
        mInitFragment.setCallback(HomePageActivity.this);
        mInitFragment.show(fm, INIT_DIALOG);
    }

    //显示头像的方法
    private void displayImage2(String imagePath) {
        if (!TextUtils.isEmpty(imagePath)) {

            //显示图片
            Glide.with(this).load(imagePath).apply(requestOptions).into(mShapeableImageView);


            //压缩图片
            orc_bitmap = CameraUtils.compression(BitmapFactory.decodeFile(imagePath));

            //转Base64
            base64Pic = BitmapUtils.bitmapToBase64(orc_bitmap);

        } else {
            Toast.makeText(this, "图片获取失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void initPopWindow(View v) {
        View view = LayoutInflater.from(HomePageActivity.this).
                inflate(R.layout.creat_team_popupwindow, null, false);

        //1.构造一个PopupWindow，参数依次是加载的View，宽高
        final PopupWindow popWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

         mCreate = (TextView) view.findViewById(R.id.popupwindow2);
         mAdd2 = (TextView) view.findViewById(R.id.popupwindow4);


        popWindow.setAnimationStyle(R.anim.anim_pop);  //设置加载动画

        //这些为了点击非PopupWindow区域，PopupWindow会消失的，如果没有下面的
        //代码的话，你会发现，当你把PopupWindow显示出来了，无论你按多少次后退键
        //PopupWindow并不会关闭，而且退不出程序，加上下述代码可以解决这个问题
        popWindow.setTouchable(true);
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));    //要为popWindow设置一个背景才有效


        //设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量
        //设置0，0为左下角
        //x负为左，y正为右
        popWindow.showAsDropDown(v, -260, 0);

        //设置popupWindow里的按钮的事件
        mCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, CreateActivity.class);
                //设置回调接口
                startActivity(intent);
                popWindow.dismiss();
            }
        });
        mAdd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, JoinTeamActivity.class);
                startActivity(intent);
                popWindow.dismiss();
            }
        });
    }

    private void initWidgets(){
        SharedPreferences sharedPreferences = getSharedPreferences("Token",0);
        token = sharedPreferences.getString("Token",null);
    }
    //网络请求
    private void WebRequest() {
        //api实例
        Retrofit retrofit = Api.getInstance().getApi();
        //web实例
        InitAPI mInit = retrofit.create(InitAPI.class);
        //call实例
        Call<LoginUser> call = mInit.getNick(token);
        //异步网络请求
        call.enqueue(new retrofit2.Callback<LoginUser>() {
            @Override
            public void onResponse(Call<LoginUser> call, Response<LoginUser> response) {
                if (response.isSuccessful()) {
                    //数据一定以这种形式
                    name = response.body().getData().getNickname();
                    avatar=response.body().getData().getAvatar();
                    //空指针问题
                    if (" ".equals(name)) {
                        init_dialog();
                    }
                    else {
                        mNick = name;
                        mNickname.setText(mNick);
                        //网络url显示图片
                        Glide.with(HomePageActivity.this).load(avatar).apply(requestOptions)
                                .into(mShapeableImageView);

                    }
                }

            }
            @Override
            public void onFailure(Call<LoginUser> call, Throwable t) {
                Toast.makeText(HomePageActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                //抛出异常
                t.printStackTrace();
                Log.e("tag", t.getMessage());
            }

        });
    }



    //回调方法
    @Override
    public void dismiss() {
        mInitFragment.dismiss();
    }

    @Override
    public void SetName(String string) {
        mNick = string;
        mNickname.setText(mNick);
    }

    @Override
    public void SetHead(String string) {
        //通过回调，得到图片的信息
        image = string;
        displayImage2(string);
    }
    @Override
    public void Users_Team(int team_id) {
        FragmentManager fm = getSupportFragmentManager();
        mUserTeamFragment= UserTeamFragment.newInstance();
        Bundle bundle=new Bundle();
        bundle.putInt("team_id",team_id);
        mUserTeamFragment.setArguments(bundle);
        //不需要目标fragment,因为可以判断activity是哪个
        mUserTeamFragment.show(fm, "UserTeamFragment");

    }
    public static Intent newIntent(Context context, List<UserTeam.Team> teams) {
        Intent intent = new Intent(context, HomePageActivity.class);
        intent.putExtra(EXTRA_UserTeam, (Serializable) teams);
        return intent;
    }
}




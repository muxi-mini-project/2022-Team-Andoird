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
import com.example.team.team.view.CreateTeamActivity;
import com.example.team.team.view.JoinTeamActivity;
import com.example.team.user.UserActivity;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomePageActivity extends StatusBar implements View.OnClickListener, Callback, Callback3 {
    private static final String EXTRA_UserTeam = "com.example.team.home_page.first_activity.team";
    //?????????Data;
    private String token;
    private ViewPager mViewPager;
    //??????
    private ShapeableImageView mShapeableImageView;

    //???????????????????????????
    private String image;
    //??????
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
    //??????????????????????????????Bitmap
    private Bitmap orc_bitmap;
    //Glide????????????????????????
    private RequestOptions requestOptions = RequestOptions.circleCropTransform()
            .diskCacheStrategy(DiskCacheStrategy.NONE)//??????????????????
            .skipMemoryCache(true);//??????????????????


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        //?????????????????????
        StatusBar_to_transparent(this);
        //????????????????????????
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_1);


        //HeadPart
        mShapeableImageView = (ShapeableImageView) findViewById(R.id.home_page1);
        mShapeableImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = UserActivity.newIntent(HomePageActivity.this, image, mNick);
                //???????????????
                UserActivity.setCallback(HomePageActivity.this);
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
            //???teams???fragment
            fragment2 = TeamFragment.newInstance(mTeams);
            //Callback,?????????HomePager?????????
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
                //???????????????????????????fragment????????????.......???
                //????????????????????????????????????
                return mList.get(position);
            }

            @Override
            public int getCount() {
                return mList.size();
            }
            //??????container????????????????????????
            //?????????????????
            /** @Override public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object){
            Fragment fragment = mList.get(position);
            fragmentManager.beginTransaction().hide(fragment).commit();
            }*/
        });
        //??????????????????
        mViewPager.setCurrentItem(0);
        //??????????????????
        mViewPager.setOffscreenPageLimit(2);
        //??????
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
    //??????implements View.OnClickListener
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_page4:
                //??????"??????"?????????????????????
                mViewPager.setCurrentItem(0);
                mweiwancheng.setBackgroundResource(R.mipmap.weiwancheng1);
                mwancheng.setBackgroundResource(R.mipmap.wancheng2);
                mquanbu.setBackgroundResource(R.mipmap.quabu2);
                break;
            case R.id.home_page5:
                //???????????????????????????????????????
                mViewPager.setCurrentItem(1);
                mweiwancheng.setBackgroundResource(R.mipmap.weiwancheng2);
                mwancheng.setBackgroundResource(R.mipmap.wancheng1);
                mquanbu.setBackgroundResource(R.mipmap.quabu2);
                break;
            case R.id.home_page6:
                //???????????????????????????????????????
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
        //???????????????fragment,??????????????????activity?????????
        mInitFragment.setCallback(HomePageActivity.this);
        mInitFragment.show(fm, INIT_DIALOG);
    }

    //?????????????????????
    private void displayImage2(String imagePath) {
        if (!TextUtils.isEmpty(imagePath)) {

            //????????????
            Glide.with(this).load(imagePath).apply(requestOptions).into(mShapeableImageView);


            //????????????
            orc_bitmap = CameraUtils.compression(BitmapFactory.decodeFile(imagePath));

            //???Base64
            base64Pic = BitmapUtils.bitmapToBase64(orc_bitmap);

        } else {
            Toast.makeText(this, "??????????????????", Toast.LENGTH_SHORT).show();
        }
    }

    private void initPopWindow(View v) {
        View view = LayoutInflater.from(HomePageActivity.this).
                inflate(R.layout.creat_team_popupwindow, null, false);

        //1.????????????PopupWindow???????????????????????????View?????????
        final PopupWindow popWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

         mCreate = (TextView) view.findViewById(R.id.popupwindow2);
         mAdd2 = (TextView) view.findViewById(R.id.popupwindow4);


        popWindow.setAnimationStyle(R.anim.anim_pop);  //??????????????????

        //?????????????????????PopupWindow?????????PopupWindow????????????????????????????????????
        //???????????????????????????????????????PopupWindow????????????????????????????????????????????????
        //PopupWindow????????????????????????????????????????????????????????????????????????????????????
        popWindow.setTouchable(true);
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // ??????????????????true?????????touch??????????????????
                // ????????? PopupWindow???onTouchEvent?????????????????????????????????????????????dismiss
            }
        });
        popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));    //??????popWindow???????????????????????????


        //??????popupWindow???????????????????????????????????????View???x??????????????????y???????????????
        //??????0???0????????????
        //x????????????y?????????
        popWindow.showAsDropDown(v, -260, 0);

        //??????popupWindow?????????????????????
        mCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, CreateTeamActivity.class);
                //??????????????????
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
    //????????????
    private void WebRequest() {
        //api??????
        Retrofit retrofit = Api.getInstance().getApi();
        //web??????
        InitAPI mInit = retrofit.create(InitAPI.class);
        //call??????
        Call<LoginUser> call = mInit.getNick(token);
        //??????????????????
        call.enqueue(new retrofit2.Callback<LoginUser>() {
            @Override
            public void onResponse(Call<LoginUser> call, Response<LoginUser> response) {
                if (response.isSuccessful()) {
                    //???????????????????????????
                    name = response.body().getData().getNickname();
                    avatar=response.body().getData().getAvatar();
                    //???????????????
                    if (" ".equals(name)) {
                        init_dialog();
                    }
                    else {
                        mNick = name;
                        mNickname.setText(mNick);
                        //??????url????????????
                        Glide.with(HomePageActivity.this).load(avatar).apply(requestOptions)
                                .into(mShapeableImageView);

                    }
                }

            }
            @Override
            public void onFailure(Call<LoginUser> call, Throwable t) {
                Toast.makeText(HomePageActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                //????????????
                t.printStackTrace();
                Log.e("tag", t.getMessage());
            }

        });
    }



    //????????????
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
        //????????????????????????????????????
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
        //???????????????fragment,??????????????????activity?????????
        mUserTeamFragment.show(fm, "UserTeamFragment");

    }
    public static Intent newIntent(Context context, List<UserTeam.Team> teams) {
        Intent intent = new Intent(context, HomePageActivity.class);
        intent.putExtra(EXTRA_UserTeam, (Serializable) teams);
        return intent;
    }
}




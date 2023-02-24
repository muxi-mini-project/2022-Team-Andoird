package com.example.team.home_page.home_pagefragment.view;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.team.Api;
import com.example.team.R;
import com.example.team.team.Bean.UsersTeam;
import com.example.team.team.net.TeamAPI;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserTeamFragment extends DialogFragment {
    private ImageButton mguanbi;
    private TextView mTextView;
    private RecyclerView mTeamRecyclerView;
    private UserTeamAdapter mUserTeamAdapter;
    private String token;
    private int team_id;
    private String team_coding;
    private List<UsersTeam.Users> mUsers;
    //private CreateTaskData mCTask;
    //Glide请求图片选项配置
    private RequestOptions requestOptions = RequestOptions.circleCropTransform()
            .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
            .skipMemoryCache(true);//不做内存缓存


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //去掉dialog的标题，需要在setContentView()之前
        this.getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = this.getDialog().getWindow();
        //去掉dialog默认的padding
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置dialog的位置在底部
        lp.gravity = Gravity.BOTTOM;
        //设置dialog的动画
        lp.windowAnimations = R.style.BottomDialogAnimation;
        window.setAttributes(lp);
        window.setBackgroundDrawable(new ColorDrawable());

        //setContentView()
        View view = inflater.inflate(R.layout.user_team, container, false);
        mTextView=(TextView) view.findViewById(R.id.user_team3);
        mguanbi = (ImageButton) view.findViewById(R.id.user_team1);
        mguanbi.setBackgroundResource(R.mipmap.guanbi);
        mguanbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mTeamRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mTeamRecyclerView.setLayoutManager(linearLayoutManager);

        Bundle bundle=getArguments();
        if (bundle != null) {
            team_id = bundle.getInt("team_id");
        }
        initWidgets();

        // WebRequest2(x,token);
        // WebRequest2(2,token,mCTask);
        UPdateUI();
        return view;
    }

    private void UPdateUI() {
        WebRequest(token);
    }

    private class UserTeamHolder extends RecyclerView.ViewHolder {

        private ShapeableImageView mShapeableImageView;
        private final TextView mTeamUserName;


        public UserTeamHolder(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent) {
            super(inflater.inflate(R.layout.team_users, parent, false));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //
                }
            });//???，（点击整个都可响应）

            mShapeableImageView = (ShapeableImageView) itemView.findViewById(R.id.team_users1);
            mTeamUserName = (TextView) itemView.findViewById(R.id.team_users2);
        }

        private void bind(UsersTeam.Users users) {
            Glide.with(getActivity()).load(users.getAvatar())
                    .placeholder(R.drawable.title)//图片加载出来前，显示的图片
                    .error(R.drawable.title)//图片加载失败后，显示的图片
                    .apply(requestOptions)
                    .into(mShapeableImageView);
            mTeamUserName.setText(users.getNickname());
        }

    }

    private class UserTeamAdapter extends RecyclerView.Adapter {
        //使用这个更具有一般性RecyclerView.ViewHolder
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new UserTeamHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            UsersTeam.Users user = mUsers.get(position);
            ((UserTeamHolder) holder).bind(user);
        }

        @Override
        public int getItemCount() {
            return mUsers.size();
        }
    }

    private void initWidgets() {
        //fragment中得getActivity
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Token", 0);
        token = sharedPreferences.getString("Token", null);
    }

    //网络请求Get---查看团队
    private void WebRequest(String token) {
        //api实例
        Retrofit retrofit = Api.getInstance().getApi();
        //web实例
        TeamAPI teamAPI = retrofit.create(TeamAPI.class);
        //call实例
        Call<UsersTeam> call = teamAPI.usersTeam(token,team_id);
        //异步网络请求
        call.enqueue(new retrofit2.Callback<UsersTeam>() {
            @Override
            public void onResponse(Call<UsersTeam> call, Response<UsersTeam> response) {
                if (response.isSuccessful()) {
                    //拉一个List下来
                    mUsers = response.body().getUser();
                    team_coding=response.body().getTeam().getTeam_coding();
                    mTextView.setText(team_coding);
                    if (mUserTeamAdapter == null) {
                        mUserTeamAdapter = new UserTeamAdapter();
                        mTeamRecyclerView.setAdapter(mUserTeamAdapter);
                    } else {
                        mUserTeamAdapter.notifyDataSetChanged();
                    }


                } else {
                    showMsg(response.body().getMessage());
                    showCode(response.body().getCode());
                    Toast.makeText(getActivity(), "搞错了,再来", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<UsersTeam> call, Throwable t) {
                Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
                //抛出异常
                t.printStackTrace();
                Log.e("tag", t.getMessage());
            }

        });
    }

    private void showMsg(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
    private void showCode(int code){
        Toast.makeText(getActivity(), code, Toast.LENGTH_SHORT).show();
    }
    public static UserTeamFragment newInstance() {
        UserTeamFragment fragment = new UserTeamFragment();
        return fragment;
    }
}

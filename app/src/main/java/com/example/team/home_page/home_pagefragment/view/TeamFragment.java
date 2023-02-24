package com.example.team.home_page.home_pagefragment.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.team.Api;
import com.example.team.R;
import com.example.team.home_page.home_pagefragment.Callback.Callback2;
import com.example.team.home_page.home_pagefragment.Callback.Callback3;
import com.example.team.team.Bean.UserTeam;
import com.example.team.team.net.TeamAPI;
import com.example.team.team.view.CreateTeamActivity;
import com.example.team.team.view.JoinTeamActivity;
import com.example.team.teamwork.MyTeam.MyTeamActivity;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TeamFragment extends Fragment implements Callback2 {
    private static final String ARG_LIST_TEAM = "List_team";
    private RecyclerView mTeamRecyclerView;
    private TeamAdapter mTeamAdapter;
    private String token;
    private List<UserTeam.Team> teams;
    private static Callback3 mCallback3;
    //Glide请求图片选项配置
    private RequestOptions requestOptions = RequestOptions.circleCropTransform()
            .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
            .skipMemoryCache(true);//不做内存缓存

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initWidgets();

        //回调来更新UI！！！！！！
        CreateTeamActivity.setCallback2(TeamFragment.this);
        JoinTeamActivity.setCallback2(TeamFragment.this);

        View view=inflater.inflate(R.layout.home_recycleview1,container,false);
        mTeamRecyclerView=(RecyclerView) view.findViewById(R.id.recyclerview1);
        //设置横向的RecycleView
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mTeamRecyclerView.setLayoutManager(linearLayoutManager);
        UPdateUI();
        //WebRequest2(token,2);
        //WebRequest2(token,13);

        return view;
    }
    private void UPdateUI(){
        WebRequest(token);
    }
    private class TeamHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private final TextView mTeamName;
        private UserTeam.Team team;
        private TextView mProject;
        private ImageView mImageView1;
        private ImageView mImageView2;
        private ImageButton mImageButton;

        public TeamHolder(@NonNull LayoutInflater inflater,@Nullable ViewGroup parent ){
            super(inflater.inflate(R.layout.team_page, parent, false));
            itemView.setOnClickListener(this);//???，（点击整个都可响应）

            mTeamName=(TextView) itemView.findViewById(R.id.team_page1);
            mProject=(TextView) itemView.findViewById(R.id.team_page2);
            mImageView1=(ImageView) itemView.findViewById(R.id.team_page3);
            mImageView2=(ImageView) itemView.findViewById(R.id.team_page4);
            mImageButton=(ImageButton) itemView.findViewById(R.id.team_page5);
            mImageButton.setBackgroundResource(R.mipmap.yuan2);
            mImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback3.Users_Team(team.getTeam_id());
                }
            });
        }
        private void bind(UserTeam.Team team){
            this.team=team;
            mTeamName.setText(team.getTeamname());
            Glide.with(getActivity()).load(team.getAvatar()).apply(requestOptions).into(mImageView1);

        }

        @Override
        public void onClick(View v) {
            Intent intent= new Intent(getActivity(), MyTeamActivity.class);
            intent.putExtra("team_id",team.getTeam_id());
            startActivity(intent);
        }

    }
    private class TeamAdapter extends RecyclerView.Adapter{
        /*
        private List<UserTeam.Team> teams;
        public TeamAdapter(List<UserTeam.Team> teams){
            this.teams=teams;
        }*/
        //使用这个更具有一般性RecyclerView.ViewHolder
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
            return new TeamHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            UserTeam.Team team=teams.get(position);
            ((TeamHolder)holder).bind(team);
        }

        @Override
        public int getItemCount() {
                return teams.size();
        }
    }

    private void initWidgets(){
        //fragment中得getActivity
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Token",0);
        token = sharedPreferences.getString("Token",null);
    }

    //网络请求Get---查看用户加入的团队
    private void WebRequest(String token) {
        //api实例
        Retrofit retrofit = Api.getInstance().getApi();
        //web实例
        TeamAPI teamAPI = retrofit.create(TeamAPI.class);
        //call实例
        Call<UserTeam> call = teamAPI.userTeam(token);
        //异步网络请求
        call.enqueue(new retrofit2.Callback<UserTeam>() {
            @Override
            public void onResponse(Call<UserTeam> call, Response<UserTeam> response) {
                if (response.isSuccessful()) {
                    //拉一个List下来
                    teams=response.body().getTeam();
                    if(mTeamAdapter==null){
                        mTeamAdapter=new TeamAdapter();
                        mTeamRecyclerView.setAdapter(mTeamAdapter);
                    }
                    else{
                        mTeamAdapter.notifyDataSetChanged();
                    }
                    //WebRequest1(response.body().getData().getTeam_coding());


                }
                else{
                    showMsg(response.body().getMessage());
                    showCode(response.body().getCode());
                    Toast.makeText(getActivity(),"搞错了,再来",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<UserTeam> call, Throwable t) {
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

    public static TeamFragment newInstance(List<UserTeam.Team> mTeam) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_LIST_TEAM, (Serializable) mTeam);
        TeamFragment fragment = new TeamFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void UpdateUI() {
        UPdateUI();
    }
    //接口实例
    public static void setCallback(Callback3 callback3) {
        mCallback3 = callback3;
    }
}

package com.example.team.home_page.home_pagefragment.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team.R;

public class TeamFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private TeamAdapter mTeamAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.home_recycleview1,container,false);
        mRecyclerView=(RecyclerView) view.findViewById(R.id.recyclerview1);
        //设置横向的RecycleView
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        UpdateUI();
        return view;
    }
    private void UpdateUI(){
        if(mTeamAdapter==null){
            mTeamAdapter=new TeamAdapter();
            mRecyclerView.setAdapter(mTeamAdapter);
        }
        else{
            mTeamAdapter.notifyDataSetChanged();
        }
    }
    private class TeamHolder extends RecyclerView.ViewHolder{

        private TextView mTeam;
        private TextView mProject;
        private ImageView mImageView1;
        private ImageView mImageView2;
        private ImageButton mImageButton;

        public TeamHolder(@NonNull LayoutInflater inflater,@Nullable ViewGroup parent ){
            super(inflater.inflate(R.layout.team_page, parent, false));
            mTeam=(TextView) itemView.findViewById(R.id.team_page1);
            mProject=(TextView) itemView.findViewById(R.id.team_page2);
            mImageView1=(ImageView) itemView.findViewById(R.id.team_page3);
            mImageView2=(ImageView) itemView.findViewById(R.id.team_page4);
            mImageButton=(ImageButton) itemView.findViewById(R.id.team_page5);
            mImageButton.setBackgroundResource(R.mipmap.yuan2);
        }
        private void bind(){
            //
        }
    }
    private class TeamAdapter extends RecyclerView.Adapter{
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
            return new TeamHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((TeamHolder)holder).bind();
        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

}

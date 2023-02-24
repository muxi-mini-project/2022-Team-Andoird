package com.example.team.teamwork.MyTeam.Vertical;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team.R;
import com.example.team.teamwork.Task.TaskActivity;

import java.util.ArrayList;
import java.util.List;

public class MyTeamFragmentVertical extends Fragment {

    // 添加存储数据的列表
    private List<String> mList = new ArrayList<>();
    private Context context;
    private RecyclerView recyclerView;
    private com.example.team.teamwork.MyTeam.Vertical.VerticalAdapter verticalAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.mt_recyclerview2, container, false);
        recyclerView = view.findViewById(R.id.mt_recyclerview2);
        initData();
        initView();

        return view;
    }

    private void initData() {
        //向list里添加数据
        mList.add("亚特兰大老鹰");
        mList.add("夏洛特黄蜂");
        mList.add("小猪佩奇");
    }

    private void initView() {

        context = this.getActivity();
        verticalAdapter =new com.example.team.teamwork.MyTeam.Vertical.VerticalAdapter(mList);
        verticalAdapter.setOnItemClickListener(new VerticalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(),"click " + position + " item", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(),TaskActivity.class);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getContext(),"long click " + position + " item", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setAdapter(verticalAdapter);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
    }

}







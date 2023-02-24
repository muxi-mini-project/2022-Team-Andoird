package com.example.team.teamwork.Task;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team.R;

import java.util.ArrayList;
import java.util.List;

public class TaskFragment extends Fragment {

    // 添加存储数据的列表
    private List<String> mList;
    private Context context;
    private RecyclerView recyclerView;
    private VerticalAdapter verticalAdapter;
    private boolean isCreated = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.task_recyclerview, container, false);
        recyclerView = view.findViewById(R.id.task_recyclerview);
        initData();
        initView();

        return view;
    }

    private void initData() {
        //向list里添加数据
        mList = new ArrayList<>();
        mList.add("亚特兰大老鹰");
        mList.add("夏洛特黄蜂");
        mList.add("米奇妙妙屋");
        mList.add("海贼王");
        mList.add("小猪佩奇");
        mList.add("喜羊羊与灰太狼");
        mList.add("神兵天将");
        mList.add("果宝特攻");
        mList.add("蜡笔小新");

    }

    private void initView() {

        context = this.getActivity();
        verticalAdapter = new VerticalAdapter(mList);
        verticalAdapter.setOnItemClickListener(new VerticalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(),"click " + position + " item", Toast.LENGTH_SHORT).show();
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

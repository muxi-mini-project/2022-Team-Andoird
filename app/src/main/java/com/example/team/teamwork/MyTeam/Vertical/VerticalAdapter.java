package com.example.team.teamwork.MyTeam.Vertical;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.team.R;
import com.example.team.teamwork.MyTeam.Horizontal.HorizontalAdapter;
import com.example.team.teamwork.MyTeam.Horizontal.HorizontalData;
import com.example.team.teamwork.MyTeam.MyTeamActivity;
import com.example.team.teamwork.Task.TaskActivity;

import java.util.ArrayList;
import java.util.List;

//① 创建Adapter
public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.VH> {
    private Context context;
    private List<VerticalData.DataDTO> data = new ArrayList<>();

    //创建构造函数
    public VerticalAdapter(Context context, List<VerticalData.DataDTO> data) {

        //将传递过来的数据，赋值给本地变量
        this.context = context; //上下文
        this.data = data; //实体类数据ArrayList
    }


    //② 在Adapter中实现3个方法
    @Override   //加载 RecyclerView 子项的布局，然后返回一个 ViewHolder 对象。   ViewHolder是继承自 RecyclerView 的一个静态内部类
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法,实例化展示的view
        View itemView= LayoutInflater.from(context).inflate(R.layout.my_team_item2,parent,false);

        return new VH(itemView);
    }

    @Override   //为子项绑定数据
    public void onBindViewHolder(VH holder, int position) {
        //绑定数据
        holder.projectName.setText(data.get(position).getProjectName());
        holder.projectDeadline.setText(data.get(position).getDeadline());
        int id = data.get(position).getProjectId();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TaskActivity.class);
                intent.putExtra("project_id", id);
                context.startActivity(intent);
            }
        });

    }

    @Override   //用于获取 RecyclerView 一共有多少子项
    public int getItemCount() {return data.size();}//

    public void refresh(List<VerticalData.DataDTO> list){
        //这个方法是我们自己手写的，主要是对适配器的一个刷新
        this.data.addAll(list);
        notifyDataSetChanged();
    }

    //③ 创建ViewHolder
    public static class VH extends RecyclerView.ViewHolder {

        public TextView projectName;
        public TextView projectDeadline;
        public VH(View itemView) {
            super(itemView);
            projectName = itemView.findViewById(R.id.project_name);
            projectDeadline = itemView.findViewById(R.id.project_deadline);
        }

    }

}


package com.example.team.teamwork.MyTeam.Horizontal;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.team.R;

import java.util.ArrayList;
import java.util.List;

//① 创建Adapter
public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.VH2> {
    private Context context;
    private List<HorizontalData.UserDTO> data = new ArrayList<>();

    //创建构造函数
    public HorizontalAdapter(Context context, List<HorizontalData.UserDTO> data) {

        //将传递过来的数据，赋值给本地变量
        this.context = context; //上下文
        this.data = data; //实体类数据ArrayList
    }


    //② 在Adapter中实现3个方法
    @Override   //加载 RecyclerView 子项的布局，然后返回一个 ViewHolder 对象。   ViewHolder是继承自 RecyclerView 的一个静态内部类
    public VH2 onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法,实例化展示的view
        View itemView= LayoutInflater.from(context).inflate(R.layout.my_team_item1,parent,false);
        return new VH2(itemView);
    }

    @Override   //为子项绑定数据
    public void onBindViewHolder(VH2 holder, int position) {

        Glide.with(context)
                .load(data.get(position).getAvatar())
                .into(holder.avatar);

    }

    @Override   //用于获取 RecyclerView 一共有多少子项
    public int getItemCount() {return data.size();}//list.size()

    public void refresh(List<HorizontalData.UserDTO> list){
        //这个方法是我们自己手写的，主要是对适配器的一个刷新
        this.data.addAll(list);
        notifyDataSetChanged();
    }


    //③ 创建ViewHolder
    public static class VH2 extends RecyclerView.ViewHolder{

        ImageView avatar;
        public VH2(View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.mti_iv1);
        }
    }

}


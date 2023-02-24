package com.example.team.teamwork.MyTeam.Horizontal;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.team.R;

import java.util.List;

//① 创建Adapter
public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.VH2> {


    //展示数据
    //private List<String>list;
    //事件回调监听
    private OnItemClickListener onItemClickListener;
    /*public HorizontalAdapter(List<ImageView> list) {
        this.list= list;
    }
    public void updateData(List<ImageView> list){
        this.list = list;
        notifyDataSetChanged();
    }*/

    //定义回调接口
    public interface OnItemClickListener {
        void onItemClick(View view ,int position);
        void onItemLongClick(View view ,int position);
    }

    //定义一个设置监听器的方法
    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }


    //② 在Adapter中实现3个方法
    @Override   //加载 RecyclerView 子项的布局，然后返回一个 ViewHolder 对象。   ViewHolder是继承自 RecyclerView 的一个静态内部类
    public VH2 onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法,实例化展示的view
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_team_item1,parent,false);
        //实例化view holder
        VH2 viewHolder = new VH2(view);
        return viewHolder;
    }

    @Override   //为子项绑定数据
    public void onBindViewHolder(VH2 holder, int position) {
        //绑定数据
        //holder.tvContent.setText(list.get(position));
        //对recyclerview的每一个itemView设置点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView,pos);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemLongClick(holder.itemView, pos);
                }
                //表示此事件已经消费，不会触发单击事件
                return true;
            }
        });
    }

    @Override   //用于获取 RecyclerView 一共有多少子项
    public int getItemCount() {return 10;}//list.size()

    //③ 创建ViewHolder
    public static class VH2 extends RecyclerView.ViewHolder{

        public final ImageView tvContent;
        public VH2(View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.mti_iv1);
        }
    }

}


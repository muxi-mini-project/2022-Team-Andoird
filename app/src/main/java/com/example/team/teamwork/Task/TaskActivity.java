package com.example.team.teamwork.Task;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.team.Api;
import com.example.team.R;
import com.example.team.StatusBar;
import com.example.team.teamwork.Bean.LookAllTaskData;
import com.example.team.teamwork.Bean.LookTaskData;
import com.example.team.teamwork.net.TaskAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TaskActivity extends StatusBar implements View.OnClickListener {

    private TextView tm_one;
    private TextView tm_two;
    private TextView tm_three;
    private ImageButton tm_return;
    private ImageButton task_add;
    private TaskDialog dialog;
    private ViewPager myViewPager;
    private List<Fragment> list;
    private TaskFragmentPagerAdapter adapter;
    private List<LookTaskData.TData> mData;
    private int team_id;
    private String project_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBar_to_transparent(this);
        //状态栏文字自适应
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_main);
        InitView();


        //设置菜单栏的点击事件
        tm_one.setOnClickListener(this);
        tm_two.setOnClickListener(this);
        tm_three.setOnClickListener(this);
        myViewPager.setOnPageChangeListener(new MyPagerChangeListener());

        //把Fragment添加到List集合里面
        list = new ArrayList<>();
        list.add(new TaskFragment());
        list.add(new TaskFragment());
        list.add(new TaskFragment());
        adapter = new TaskFragmentPagerAdapter(getSupportFragmentManager(), list);
        myViewPager.setAdapter(adapter);
        myViewPager.setCurrentItem(0);  //初始化显示第一个页面
        tm_one.setBackgroundColor(0xff9191ff);//被选中就为蓝色
    }

    /*初始化控件*/
    private void InitView() {
        tm_one = (TextView) findViewById(R.id.tm_one);
        tm_two = (TextView) findViewById(R.id.tm_two);
        tm_three = (TextView) findViewById(R.id.tm_three);
        myViewPager = (ViewPager) findViewById(R.id.tm_viewpager);

        tm_return = (ImageButton) findViewById(R.id.tm_return);
        tm_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskActivity.this.finish();
            }
        });

        task_add = (ImageButton) findViewById(R.id.task_add);
        task_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task_dialog();
            }
        });

    }

    /*点击事件*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tm_one:
                myViewPager.setCurrentItem(0);
                tm_one.setBackgroundColor(0xff9191ff);
                tm_two.setBackgroundColor(Color.WHITE);
                tm_three.setBackgroundColor(Color.WHITE);
                break;
            case R.id.tm_two:
                myViewPager.setCurrentItem(1);
                tm_one.setBackgroundColor(Color.WHITE);
                tm_two.setBackgroundColor(0xff9191ff);
                tm_three.setBackgroundColor(Color.WHITE);
                break;
            case R.id.tm_three:
                myViewPager.setCurrentItem(2);
                tm_one.setBackgroundColor(Color.WHITE);
                tm_two.setBackgroundColor(Color.WHITE);
                tm_three.setBackgroundColor(0xff9191ff);
                break;
        }
    }


    /* 设置一个ViewPager的侦听事件，当左右滑动ViewPager时菜单栏被选中状态跟着改变*/
    public class MyPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
                case 0:
                    tm_one.setBackgroundColor(0xff9191ff);
                    tm_two.setBackgroundColor(Color.WHITE);
                    tm_three.setBackgroundColor(Color.WHITE);
                    break;
                case 1:
                    tm_one.setBackgroundColor(Color.WHITE);
                    tm_two.setBackgroundColor(0xff9191ff);
                    tm_three.setBackgroundColor(Color.WHITE);
                    break;
                case 2:
                    tm_one.setBackgroundColor(Color.WHITE);
                    tm_two.setBackgroundColor(Color.WHITE);
                    tm_three.setBackgroundColor(0xff9191ff);
                    break;
            }
        }
    }

    //网络请求Get---所有查看任务
    private void WebRequest(int team_id, String token) {
        //api实例
        Retrofit retrofit = Api.getInstance().getApi();
        //web实例
        TaskAPI taskAPI = retrofit.create(TaskAPI.class);
        //call实例
        Call<LookAllTaskData> call = taskAPI.lookAllTask(token, team_id);
        //异步网络请求
        call.enqueue(new retrofit2.Callback<LookAllTaskData>() {
            @Override
            public void onResponse(Call<LookAllTaskData> call, Response<LookAllTaskData> response) {
                if (response.isSuccessful()) {
                    //拉一个List下来
                    mData=response.body().getData();


                } else {

                    Toast.makeText(TaskActivity.this, "搞错了,再来", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<LookAllTaskData> call, Throwable t) {
                Toast.makeText(TaskActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                //抛出异常
                t.printStackTrace();
                Log.e("tag", t.getMessage());
            }

        });
    }

    private void task_dialog(){
        FragmentManager fm_dialog = getSupportFragmentManager();
        dialog = TaskDialog.newInstance();
        Bundle bundle=new Bundle();
        bundle.putInt("team_id",2);
        bundle.putString("project_name","项目10号");
        dialog.setArguments(bundle);
        dialog.show(fm_dialog,"my_dialog");
    }

}


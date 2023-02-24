package com.example.team.teamwork.Task;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.team.Api;
import com.example.team.R;
import com.example.team.home_page.home_pagefragment.Callback.Callback4;
import com.example.team.teamwork.Bean.CreateTaskData;
import com.example.team.teamwork.Bean.LookTaskData;
import com.example.team.teamwork.net.TaskAPI;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TaskDialog extends DialogFragment {

    private EditText remark;
    private EditText name;
    private EditText step;
    private Button ddl;
    private Button sl;
    private EditText member;
    private Button True;
    private Button False;
    private Calendar calendar;
    private Calendar calendar2;
    private String token;
    private int team_id;
    private String project_name;
    private CreateTaskData mCTask=new CreateTaskData();
    private static Callback4 mCallback4;
    private static Callback4 mCallback42;


    @Nullable//表明该参数可能为null,通常用于消除空指针异常
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_dialog,container,false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        initWidgets();

        //拿到team_id
        Bundle bundle=getArguments();
        if (bundle != null) {
            team_id = bundle.getInt("team_id");
            project_name=bundle.getString("project_name");
        }

        remark=(EditText)view.findViewById(R.id.td_remark);
        step=(EditText)view.findViewById(R.id.td_step);
        name = (EditText) view.findViewById(R.id.td_name);
        ddl = (Button) view.findViewById(R.id.td_ddl);
        sl = (Button)view.findViewById(R.id.td_sl);
        False = (Button) view.findViewById(R.id.td_false);
        True = (Button) view.findViewById(R.id.td_true);
        member = (EditText) view.findViewById(R.id.td_member);
        calendar = Calendar.getInstance();
        calendar2 = Calendar.getInstance();

        False.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });
        True.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCTask.setName(name.getText().toString());
                mCTask.setStep(step.getText().toString());
                mCTask.setRemark(remark.getText().toString());
                mCTask.setProject(project_name);
                ArrayList<String> ss=new ArrayList<>(1);
                ss.add(member.getText().toString());
                mCTask.setMember(ss);
                WebRequest(team_id,token,mCTask);
            }
        });
        sl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate1();
            }
        });

        ddl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate2();
            }
        });



        return view;
    }

    public void showDate2(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.task_dialog_date, null);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener(){
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String time = year + "年" + (month + 1) + " 月" + dayOfMonth +"日";
                mCTask.setDeadline(time);
                ddl.setText("截止时间：" + year + "年" + month + "月" + dayOfMonth + "日");
            }
        };

        final DatePicker Time = (DatePicker) view.findViewById(R.id.task_date);
        //startTime.updateDate(startTime.getYear(), startTime.getMonth(), 01);

        DatePickerDialog.Builder builder = new DatePickerDialog.Builder(getContext());
        builder.setTitle("选择时间");
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                int month1 = Time.getMonth() + 1;
                String et = "" + Time.getYear() + month1 + Time.getDayOfMonth();
            }
        });
        builder.setNegativeButton("取消", null);

        DatePickerDialog dialog = new DatePickerDialog(getDialog().getContext(),onDateSetListener,year,month,day);
        dialog.show();
        //自动弹出键盘问题解决
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    public void showDate1(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.task_dialog_date, null);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener(){
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String time =  year + "年" + month + "月" + dayOfMonth + "日";
                mCTask.setStart_time(time);
                sl.setText("开始时间：" + year + "年" + month + "月" + dayOfMonth + "日");
            }
        };

        //final DatePicker startTime = (DatePicker) view.findViewById(R.id.st);
        final DatePicker Time = (DatePicker) view.findViewById(R.id.task_date);
        DatePickerDialog.Builder builder = new DatePickerDialog.Builder(getContext());
        builder.setTitle("选择时间");
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //int month = startTime.getMonth() + 1;
                //String st = "" + startTime.getYear() + month + startTime.getDayOfMonth();
                int month1 = Time.getMonth() + 1;
                String et = "" + Time.getYear() + month1 + Time.getDayOfMonth();
            }
        });
        builder.setNegativeButton("取消", null);

        DatePickerDialog dialog = new DatePickerDialog(getDialog().getContext(),onDateSetListener,year,month,day);
        dialog.show();
        //自动弹出键盘问题解决
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    public void showMember(){

    }
    private void initWidgets(){
        //fragment中得getActivity
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Token",0);
        token = sharedPreferences.getString("Token",null);
    }

    //网络请求POST---创建任务
    private void WebRequest(int team_id, String token, CreateTaskData cTask) {
        //api实例
        Retrofit retrofit = Api.getInstance().getApi();
        //web实例
        TaskAPI taskAPI = retrofit.create(TaskAPI.class);
        //call实例
        Call<LookTaskData> call = taskAPI.createTask(token, team_id, cTask);
        //异步网络请求
        call.enqueue(new retrofit2.Callback<LookTaskData>() {
            @Override
            public void onResponse(Call<LookTaskData> call, Response<LookTaskData> response) {
                if (response.isSuccessful()) {
                    //拉一个List下来
                    int mm = response.body().getTask_id();
                    String ss = response.body().getMessage();
                    dismiss();
                    Toast.makeText(getContext(), "任务创建成功", Toast.LENGTH_SHORT).show();
                    mCallback4.UPdate();
                    mCallback42.UPdate();

                } else {
                    Toast.makeText(getActivity(), "搞错了,再来", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<LookTaskData> call, Throwable t) {
                Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
                //抛出异常
                t.printStackTrace();
                Log.e("tag", t.getMessage());
            }

        });
    }


    //实例化
    public static TaskDialog  newInstance() {
        TaskDialog dialog = new TaskDialog();
        return dialog;
    }
    public static void setCallback(Callback4 callback4) {
        mCallback4 = callback4;
    }
    public static void setCallback2(Callback4 callback4) {
        mCallback42 = callback4;
    }
}
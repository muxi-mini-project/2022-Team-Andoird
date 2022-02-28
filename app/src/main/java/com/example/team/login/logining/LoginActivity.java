package com.example.team.login.logining;

import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;不要这个，改为下面的
import androidx.appcompat.app.AppCompatActivity;
//import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.team.R;
import com.example.team.login.login_ok.Login_over;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
/**
    private EditText mStudent_Id;
    private EditText mPassword;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String student_id;
        String password;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStudent_Id=(EditText) findViewById(R.id.);
        mPassword=(EditText)findViewById(R.id.);

        mLoginButton=(Button) findViewById(R.id.);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                student_id=mStudent_Id.getText().toString();
                password=mPassword.getText().toString();
                WebRequest(student_id,password);
            }
        });


    }
    private void WebRequest(String student_id,String password) {
        //api实例
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://127.0.0.1:4523/mock/603295/login")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //对象，用body
        LoginUser mLoginUser = new LoginUser(student_id, password);
        //web实例
        LoginAPI mLogin = retrofit.create(LoginAPI.class);
        //call实例
        Call<LoginResponse> call = mLogin.postLogin(mLoginUser);
        //异步网络请求
        call.enqueue(new retrofit2.Callback<LoginResponse>() {
            //web成功
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                    Intent intent1=new Intent(LoginActivity.this, Login_over.class);
                    startActivity(intent1);
                }
//                if (response != null && response.body() != null){
//
//                    //判断是否初入
//                    if(true){
//                        Intent intent1=new Intent(LoginActivity.this, Login_over.class);
//                        startActivity(intent1);
//                    }
//                    else{
//                        Intent intent2=new Intent(LoginActivity.this,Login_over.class);
//                        startActivity(intent2);
//                    }
//
//                }
//                else{
//                    Toast.makeText(LoginActivity.this,"账号或密码错误",Toast.LENGTH_SHORT).show();
//                }
            }
            //web失败
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"网络连接失败", Toast.LENGTH_SHORT).show();
                //抛出异常
                t.printStackTrace();
                Log.e("tag",t.getMessage());
            }
        });
    }*/




}

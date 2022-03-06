package com.example.team.login.logining;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;不要这个，改为下面的
import androidx.appcompat.app.AppCompatActivity;
//import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.team.FirstActivity;
import com.example.team.R;
import com.example.team.home_page.HomePageActivity;
import com.example.team.login.login_ok.Login_over;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText mStudent_Id;
    private EditText mPassword;
    private Button mLoginButton;
    private CheckBox mRemember;

    private String student_id;
    private String password;

    public static String saved;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);//

        //获得SharedPreferences，并创建文件名为saved
        SharedPreferences sp = getSharedPreferences("saved", 0);
        //获得Editor对象，用于储存用户信息
        SharedPreferences.Editor editor = sp.edit();
        //判断是否为初次登录
        saved=sp.getString("saved",null);
        if (saved!= null){
            Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);//
            startActivity(intent);
            finish();
        }

        //重新登录,如果之前记住过密码，直接先导入。
        mStudent_Id=(EditText) findViewById(R.id.et5);
        mPassword=(EditText)findViewById(R.id.et6);
        if(sp.getString("account",student_id) !=null
                && sp.getString("password",password) !=null){
            mStudent_Id.setText(sp.getString("account",student_id));
            mPassword.setText(sp.getString("password",password));
        }
       //
        mRemember=(CheckBox)findViewById(R.id.key_remember);
        mLoginButton=(Button) findViewById(R.id.enter);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                student_id=mStudent_Id.getText().toString();
                password=mPassword.getText().toString();
                //保存密码
                if(mRemember.isChecked()){
                    editor.putString("account",student_id);
                    editor.putString("password",password);
                    editor.commit();
                    editor.apply();
                }
                else{
                    //啥也不干
                }
                WebRequest(student_id,password);
            }
        });


    }
    //网络请求
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
                    Intent intent=new Intent(LoginActivity.this, Login_over.class);
                    startActivity(intent);
                    //?????
                    saved=response.body().getNickname();
                    //保存初次是否为登录数据，
                    SharedPreferences sharedPreferences = getSharedPreferences("saved",0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("saved",saved);
                    editor.commit();
                    editor.apply();
                }
                else{
                Toast.makeText(LoginActivity.this,"账号或密码错误",Toast.LENGTH_SHORT).show();
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
    }




}

package com.example.team.login.logining;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;不要这个，改为下面的
import androidx.appcompat.app.AppCompatActivity;
//import android.telecom.Call;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.team.Api;
import com.example.team.FirstActivity;
import com.example.team.R;
import com.example.team.StatusBar;
import com.example.team.home_page.HomePageActivity;
import com.example.team.login.login_ok.Login_over;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends StatusBar {

    private EditText mStudent_Id;
    private EditText mPassword;
    private ImageButton mLoginButton;
    private CheckBox mRemember;

    private String student_id;
    private String password;

    public static String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //设置状态栏透明
        StatusBar_to_transparent(this);
        //状态栏文字自适应
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        /*获得SharedPreferences，并创建文件名为saved
        SharedPreferences sp = getSharedPreferences("Token", 0);
        //获得Editor对象，用于储存用户信息,保存密码
        //SharedPreferences.Editor editor = sp.edit();
        //判断是否为初次登录
        token = sp.getString("Token", null);
        if (token != null) {
            Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);//
            startActivity(intent);
            finish();
        }*/

        //重新登录,如果之前记住过密码，直接先导入。
        mStudent_Id = (EditText) findViewById(R.id.et5);
        mPassword = (EditText) findViewById(R.id.et6);

        //隐藏密码
        mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        /**if (sp.getString("account", student_id) != null
         && sp.getString("password", password) != null) {
         mStudent_Id.setText(sp.getString("account", student_id));
         mPassword.setText(sp.getString("password", password));
         }*/
        //
        //mRemember=(CheckBox)findViewById(R.id.key_remember);
        mLoginButton = (ImageButton) findViewById(R.id.enter);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                student_id = mStudent_Id.getText().toString();
                password = mPassword.getText().toString();
                //保存密码
                /**  if(mRemember.isChecked()){
                 editor.putString("account",student_id);
                 editor.putString("password",password);
                 editor.commit();
                 editor.apply();
                 }
                 else{
                 //啥也不干
                 }*/
                WebRequest(student_id, password);
            }
        });
    }

    //网络请求
    private void WebRequest(String student_id, String password) {
        //api实例
        Retrofit retrofit = Api.getInstance().getApi();
        //对象，用body
        LoginUser.DataDTO mLoginUser = new LoginUser.DataDTO(student_id, password);
        //web实例
        LoginAPI mLogin = retrofit.create(LoginAPI.class);
        //call实例
        Call<LoginResponse> call = mLogin.postLogin(mLoginUser);
        //异步网络请求
        call.enqueue(new retrofit2.Callback<LoginResponse>() {
            //web成功
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                    startActivity(intent);

                    //Data
                    token = response.body().getData();
                    //保存初次是否为登录数据，
                    SharedPreferences sharedPreferences = getSharedPreferences("Token", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Token",token);
                    editor.commit();
                    editor.apply();
                } else {
                    Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                }

            }

            //web失败
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                //抛出异常
                t.printStackTrace();
                Log.e("tag", t.getMessage());
            }
        });
    }
}

package com.example.team.login.logining;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;不要这个，改为下面的
//import android.telecom.Call;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.team.network.Api;
import com.example.team.R;
import com.example.team.StatusBar;
import com.example.team.home_page.HomePageActivity;
import com.example.team.network.ServiceCreator;

import retrofit2.Call;
import retrofit2.Response;


public class LoginActivity extends StatusBar {

    private static final String TOKEN = "token";
    private static final String DATA = "data";
//    private static final String ID = "id";
//    private static final String PASSWORD = "password";

    private EditText et_id;
    private EditText et_password;
    private Button btn_login;
    private CheckBox check_remember;

    private String student_id;
    private String password;

    public static void actionStart(Context context){
        Intent intent = new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //设置状态栏透明
        StatusBar_to_transparent(this);
        //状态栏文字自适应
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        et_id = (EditText) findViewById(R.id.et_id);
        et_password = (EditText) findViewById(R.id.et_password);

        //重新登录,如果之前记住过密码，直接先导入
        et_id.setText(restoreData("id"));
        et_password.setText(restoreData("password"));

        //隐藏密码
        //check_remember.setTransformationMethod(PasswordTransformationMethod.getInstance());
        btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                student_id = et_id.getText().toString();
                password = et_password.getText().toString();
                loginRequest(student_id, password);
            }
        });
        check_remember = findViewById(R.id.check_remember);
    }

    private void saveData(String key,String value){
        SharedPreferences sp = getSharedPreferences(DATA,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key,value);
        editor.apply();
    }

    private String restoreData(String key){
        SharedPreferences sp = getSharedPreferences(key,Context.MODE_PRIVATE);
        String value = sp.getString(key,"");
        return value;
    }

    //网络请求
    private void loginRequest(String student_id, String password) {
        //登陆输入数据
        LoginUser.Data mLoginUser = new LoginUser.Data(student_id, password);
        //创建Api的动态代理对象
        Api loginApi =  ServiceCreator.create(Api.class);
        //创建登陆请求
        Call<LoginResponse> call = loginApi.postLogin(mLoginUser);
        //异步网络请求
        call.enqueue(new retrofit2.Callback<LoginResponse>() {
            //请求成功
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();

                    //token数据
                    String token = response.body().getData();
                    //登陆成功后保存token数据
                    saveData(TOKEN,token);
                    if(check_remember.isChecked()){
                        saveData("id",student_id);
                        saveData("password",password);
                    }
                    HomePageActivity.actionStart(LoginActivity.this);
                } else {
                    Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                }
            }

            //请求失败
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

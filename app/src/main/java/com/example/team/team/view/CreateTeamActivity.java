package com.example.team.team.view;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.team.Api;
import com.example.team.R;
import com.example.team.StatusBar;
import com.example.team.home_page.home_pagefragment.Callback.Callback2;
import com.example.team.login.login_ok.utils.BitmapUtils;
import com.example.team.login.login_ok.utils.CameraUtils;
import com.example.team.team.Bean.TeamData;
import com.example.team.team.net.TeamAPI;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.imageview.ShapeableImageView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreateTeamActivity extends StatusBar {
    private EditText mEditText1;
    private EditText mEditText2;
    private String mTeamNick;
    private String mTeamInstruction;
    private ImageButton wancheng;
    private ImageButton fenxiang;
    private ImageButton fanhui;
    private String token;
    private String image;
    private static Callback2 mCallback2;

    //权限请求
    private RxPermissions rxPermissions;

    //是否拥有权限
    private boolean hasPermissions = false;

    //底部弹窗
    private BottomSheetDialog bottomSheetDialog;
    //弹窗视图
    private View bottomView;

    //存储拍完照后的图片
    private File outputImagePath;
    //启动相机标识
    public static final int TAKE_PHOTO = 1;
    //启动相册标识
    public static final int SELECT_PHOTO = 2;

    //图片控件
    private ShapeableImageView ivHead;
    //Base64
    private String base64Pic;
    //拍照和相册获取图片的Bitmap
    private Bitmap orc_bitmap;

    //Glide请求图片选项配置
    private RequestOptions requestOptions = RequestOptions.circleCropTransform()
            .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
            .skipMemoryCache(true);//不做内存缓存

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        StatusBar_to_transparent(this);
        //状态栏文字自适应
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_team1);

        //name coding
        mEditText1 = (EditText) findViewById(R.id.create_team3);
        mEditText2 = (EditText) findViewById(R.id.create_team4);

        mTeamNick = mEditText1.getText().toString();
        mTeamInstruction = mEditText2.getText().toString();


        wancheng = (ImageButton) findViewById(R.id.create_team5);
        wancheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=mEditText1.getText().toString();
                String coding=mEditText2.getText().toString();
                if (image != null&& !name.equals("") && !coding.equals("")) {
                    File file = new File(image);
                    WebRequest2(file, mEditText1.getText().toString(), mEditText2.getText().toString());
                } else {
                    String mas = "请填写完团队信息";
                    showMsg(mas);
                }

            }
        });
        fenxiang = (ImageButton) findViewById(R.id.create_team6);
        fenxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent=new Intent(CreateTeamActivity.this, ShareTeamActivity.class);
                startActivity(intent);*/
                String mas = "该功能还未实现哦~";
                showMsg(mas);
            }
        });
        fanhui = (ImageButton) findViewById(R.id.create_team1);
        fanhui.setBackgroundResource(R.mipmap.fan_hui2);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivHead = (ShapeableImageView) findViewById(R.id.create_team2);
        ivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAvatar(v);
            }
        });

        //检查版本
        checkVersion();
        initWidgets();

        //取出缓存
        /*String imageUrl = SPUtils.getString("imageUrl2",null,this);
        if(imageUrl != null){
            Glide.with(this).load(imageUrl).apply(requestOptions).into(ivHead);
        }*/
    }

    /**
     * 检查版本
     */
    private void checkVersion() {
        //Android6.0及以上版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //如果你是在Fragment中，则把this换成getActivity()
            rxPermissions = new RxPermissions(this);
            //权限请求
            rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(granted -> {
                        if (granted) {//申请成功
                            showMsg("已获取权限");
                            hasPermissions = true;
                        } else {//申请失败
                            showMsg("权限未开启");
                            hasPermissions = false;
                        }
                    });
        } else {
            //Android6.0以下
            showMsg("无需请求动态权限");
        }
    }

    /**
     * 更换头像
     *
     * @param view
     */
    public void changeAvatar(View view) {
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomView = getLayoutInflater().inflate(R.layout.headdialog_bottom, null);
        bottomSheetDialog.setContentView(bottomView);
        bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundColor(Color.TRANSPARENT);
        TextView tvTakePictures = bottomView.findViewById(R.id.tv_take_pictures);
        TextView tvOpenAlbum = bottomView.findViewById(R.id.tv_open_album);
        TextView tvCancel = bottomView.findViewById(R.id.tv_cancel);

        //拍照
        tvTakePictures.setOnClickListener(v -> {
            //takePhoto();
            showMsg("该功能还未实现哦~");
            //bottomSheetDialog.cancel();
        });
        //打开相册
        tvOpenAlbum.setOnClickListener(v -> {
            openAlbum();
            showMsg("打开相册");
            bottomSheetDialog.cancel();
        });
        //取消
        tvCancel.setOnClickListener(v -> {
            bottomSheetDialog.cancel();
        });
        //底部弹窗显示
        bottomSheetDialog.show();
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        if (!hasPermissions) {
            showMsg("未获取到权限");
            checkVersion();
            return;
        }
        SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                "yyyy_MM_dd_HH_mm_ss");
        String filename = timeStampFormat.format(new Date());
        outputImagePath = new File(getExternalCacheDir(),
                filename + ".jpg");
        Intent takePhotoIntent = CameraUtils.getTakePhotoIntent(this, outputImagePath);
        // 开启一个带有返回值的Activity，请求码为TAKE_PHOTO
        startActivityForResult(takePhotoIntent, TAKE_PHOTO);//???????
    }

    /**
     * 打开相册
     */
    private void openAlbum() {
        if (!hasPermissions) {
            showMsg("未获取到权限");
            checkVersion();
            return;
        }
        startActivityForResult(CameraUtils.getSelectPhotoIntent(), SELECT_PHOTO);

    }

    /**
     * 返回到Activity
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //拍照后返回
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    //显示图片
                    image = outputImagePath.getAbsolutePath();
                    displayImage2(image);
                }
                break;
            //打开相册后返回
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    String imagePath = null;
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                        //4.4及以上系统使用这个方法处理图片
                        imagePath = CameraUtils.getImageOnKitKatPath(data, this);
                    } else {
                        imagePath = CameraUtils.getImageBeforeKitKatPath(data, this);
                    }
                    //显示图片
                    image = imagePath;
                    displayImage2(image);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 通过图片路径显示图片
     */
    private void displayImage2(String imagePath) {
        if (!TextUtils.isEmpty(imagePath)) {

            //放入缓存
            //SPUtils.putString("imageUrl2",imagePath,this);

            //显示图片
            Glide.with(this).load(imagePath).apply(requestOptions).into(ivHead);

            //压缩图片
            orc_bitmap = CameraUtils.compression(BitmapFactory.decodeFile(imagePath));
            //转Base64
            base64Pic = BitmapUtils.bitmapToBase64(orc_bitmap);

        } else {
            showMsg("图片获取失败");
        }
    }

    private void initWidgets() {
        //fragment中得getActivity
        SharedPreferences sharedPreferences2 = CreateTeamActivity.this.getSharedPreferences("Token", 0);
        token = sharedPreferences2.getString("Token", null);
    }

    //网络请求POST---创建团队
    private void WebRequest2(File file, String name, String coding) {
        //api实例
        Retrofit retrofit = Api.getInstance().getApi();
        //web实例
        TeamAPI teamAPI = retrofit.create(TeamAPI.class);
        //学长代码
        //多数据类型----MediaType.parse("image/jpg")
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        //传入文本
        Map<String, RequestBody> map = new HashMap<>();
        map.put("name", RequestBody.create(MediaType.parse("text/plain"), name));
        map.put("coding", RequestBody.create(MediaType.parse("text/plain"), coding));
        //call实例
        Call<TeamData> call = teamAPI.createTeam(token, part, map);
        //异步网络请求
        call.enqueue(new retrofit2.Callback<TeamData>() {
            @Override
            public void onResponse(Call<TeamData> call, Response<TeamData> response) {
                if (response.isSuccessful()) {
                    showMsg(response.body().getMessage());
                    //WebRequest1(response.body().getData().getTeam_coding());
                    mCallback2.UpdateUI();
                    CreateTeamActivity.this.finish();


                } else {
                    showCode(response.body().getCode());
                }
            }

            @Override
            public void onFailure(Call<TeamData> call, Throwable t) {
                Toast.makeText(CreateTeamActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                //抛出异常
                t.printStackTrace();
                Log.e("tag", t.getMessage());
            }

        });
    }

    public static void setCallback2(Callback2 callback2) {
        mCallback2 = callback2;
    }

    /**
     * Toast提示
     *
     * @param msg
     */
    private void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void showCode(int code) {
        Toast.makeText(this, code, Toast.LENGTH_SHORT).show();
    }

}

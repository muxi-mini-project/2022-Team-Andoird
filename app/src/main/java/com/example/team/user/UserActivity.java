package com.example.team.user;


import android.Manifest;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.team.Api;
import com.example.team.R;
import com.example.team.StatusBar;
import com.example.team.home_page.home_pagefragment.net.InitAPI;
import com.example.team.login.logining.LoginUser;
import com.example.team.home_page.home_pagefragment.Callback.Callback;
import com.example.team.login.login_ok.utils.BitmapUtils;
import com.example.team.login.login_ok.utils.CameraUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.imageview.ShapeableImageView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserActivity extends StatusBar {
    //回调返回数据
    //成为静态的？？？？？？？？？？？？？？？？？？？
    private static Callback mCallback;
    private static final String EXTRA_AVATAR = "avatar";
    private static final String EXTRA_NICKNAME = "nickName";

    private static final int CHANGE_NICKNAME = 0;
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

    private ShapeableImageView avatarImageView;
    private TextView mNickname;
    private String mNick;
    private String image;
    //Base64
    private String base64Pic;
    //拍照和相册获取图片的Bitmap
    private Bitmap orc_bitmap;

    private String token;
    private String name;
    private String avatar;
    //Glide请求图片选项配置
    private RequestOptions requestOptions = RequestOptions.circleCropTransform()
            .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
            .skipMemoryCache(true);//不做内存缓存

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // setCallback(HomePageActivity.this);
        //设置状态栏透明
        StatusBar_to_transparent(this);
        //状态栏文字自适应
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_information);

        //接收HomePagerActivity的图片信息
        image = getIntent().getStringExtra(EXTRA_AVATAR);
        mNick = getIntent().getStringExtra(EXTRA_NICKNAME);

        avatarImageView = (ShapeableImageView) findViewById(R.id.avatar);

        //可以不使用了
        displayImage3(image);

        mNickname = (TextView) findViewById(R.id.nickName);
        //mNickname.setText(mNick);
        //检查版本
        checkVersion();

        initWidgets();
        WebRequest1();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCallback.SetHead(image);
        mCallback.SetName(mNick);
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
    public void changeAvatar2(View view) {
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
            case CHANGE_NICKNAME:
                String nickName = data.getStringExtra("nickname");
                mNickname.setText(nickName);
            //拍照后返回
//            case TAKE_PHOTO:
//                if (resultCode == RESULT_OK) {
//                    //显示图片
//                    image = outputImagePath.getAbsolutePath();
//                    displayImage3(image);
//                    WebRequest2(outputImagePath);
//                }
//                break;
//            //打开相册后返回
//            case SELECT_PHOTO:
//                if (resultCode == RESULT_OK) {
//                    String imagePath = null;
//                    //判断手机系统版本号
//                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
//                        //4.4及以上系统使用这个方法处理图片
//                        imagePath = CameraUtils.getImageOnKitKatPath(data, this);
//                    } else {
//                        imagePath = CameraUtils.getImageBeforeKitKatPath(data, this);
//                    }
//                    //显示图片
//                    image = imagePath;
//                    displayImage3(image);
//                    WebRequest2(new File(image));
//                }
                break;
            default:
                break;
        }
    }

    //显示头像的方法
    private void displayImage3(String imagePath) {
        if (!TextUtils.isEmpty(imagePath)) {

            //放入缓存
            //SPUtils.putString("imageUrl", imagePath, this);

            //显示图片
            Glide.with(this).load(imagePath).apply(requestOptions).into(avatarImageView);

            //压缩图片
            orc_bitmap = CameraUtils.compression(BitmapFactory.decodeFile(imagePath));
            //转Base64
            base64Pic = BitmapUtils.bitmapToBase64(orc_bitmap);

        } else {
            //Toast.makeText(this, "图片获取失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Toast提示
     *
     * @param msg
     */
    private void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public static Intent newIntent(Context context, String head, String nick) {
        Intent intent = new Intent(context, UserActivity.class);
//        intent.putExtra(EXTRA_UserHead, head);
//        intent.putExtra(EXTRA_UserNick, nick);
        return intent;
    }
    /*public static Intent newIntent(Context context){
        Intent intent =new Intent(context,UserActivity.class);
        return intent;
    }*/

    /*
    修改昵称
     */
    public void changeNickname(View view) {
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomView = getLayoutInflater().inflate(R.layout.change_nickname, null);
        bottomSheetDialog.setContentView(bottomView);
        //设置背景颜色
        bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundColor(Color.TRANSPARENT);

        //底部弹窗显示
        bottomSheetDialog.show();

        TextView changeNickname = bottomView.findViewById(R.id.changeNickName);
        TextView cancel = bottomView.findViewById(R.id.cancel);

        //修改昵称
        changeNickname.setOnClickListener(v -> {
            //Intent intent = ChangeNickActivity.newIntent(UserActivity.this, mNick);
            Intent intent = new Intent(UserActivity.this,ChangeNickActivity.class);
            startActivityForResult(intent,CHANGE_NICKNAME);
            showMsg("修改昵称");
            bottomSheetDialog.cancel();
        });
        //取消
        cancel.setOnClickListener(v -> {
            bottomSheetDialog.cancel();
        });
    }

    private void initWidgets() {
        SharedPreferences sharedPreferences = getSharedPreferences("Token", 0);
        token = sharedPreferences.getString("Token", null);
    }

    //网络请求---Get获取用户信息
    private void WebRequest1() {
        //api实例
        Retrofit retrofit = Api.getInstance().getApi();
        //web实例
        InitAPI mInit = retrofit.create(InitAPI.class);
        //call实例
        Call<LoginUser> call = mInit.getNick(token);
        //异步网络请求
        call.enqueue(new retrofit2.Callback<LoginUser>() {
            @Override
            public void onResponse(Call<LoginUser> call, Response<LoginUser> response) {
                if (response.isSuccessful()) {
                    //数据一定以这种形式
                    name = response.body().getData().getNickname();
                    avatar = response.body().getData().getAvatar();
                    mNick = name;
                    mNickname.setText(mNick);
                    //网络url显示图片
                    Glide.with(UserActivity.this).load(avatar).apply(requestOptions)
                            .into(avatarImageView);

                } else {
                    Toast.makeText(UserActivity.this, "信息获取失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginUser> call, Throwable t) {
                Toast.makeText(UserActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                //抛出异常
                t.printStackTrace();
                Log.e("tag", t.getMessage());
            }

        });

    }
    //网络请求POST---设置头像
    private void WebRequest2(File file) {
        //api实例
        Retrofit retrofit = Api.getInstance().getApi();
        //web实例
        InitAPI mInit = retrofit.create(InitAPI.class);
        //学长代码
        RequestBody requestFile=RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part part=MultipartBody.Part.createFormData("file",file.getName(),requestFile);
        //call实例
        Call<LoginUser> call = mInit.initAvatar(token, part);
        //异步网络请求
        call.enqueue(new retrofit2.Callback<LoginUser>() {
            @Override
            public void onResponse(Call<LoginUser> call, Response<LoginUser> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UserActivity.this,"头像设置成功",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(UserActivity.this,"头像设置失败",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginUser> call, Throwable t) {
                Toast.makeText(UserActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                //抛出异常
                t.printStackTrace();
                Log.e("tag", t.getMessage());
            }

        });
    }

    //为什么变为静态的可以使用
    public static void setCallback(Callback callback) {
        mCallback = callback;
    }

}

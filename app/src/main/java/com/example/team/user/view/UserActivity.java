package com.example.team.user.view;


import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.team.R;
import com.example.team.StatusBar;
import com.example.team.home_page.HomePageActivity;
import com.example.team.login.login_ok.utils.BitmapUtils;
import com.example.team.login.login_ok.utils.CameraUtils;
import com.example.team.login.logining.LoginActivity;
import com.example.team.user.presenter.UserPresenter;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity extends StatusBar implements IUserView {

    private static final String EXTRA_AVATAR = "avatar";
    public static final String EXTRA_NICKNAME = "nickName";
    private static final String TAG = "UserActivity";

    //启动相机标识
    private static final int TAKE_PHOTO = 1;
    //启动相册标识
    private static final int SELECT_PHOTO = 2;
    //裁剪图片标识
    private static final int CROP_PHOTO = 3;
    //修改昵称标识
    private static final int CHANGE_NICKNAME = 4;

    private Button btn_return,btn_about,btn_update,btn_logout;
    private TextView tv_nickName;
    private CircleImageView iv_avatar;

    private Uri imageUri;
    private String imagePath;

    private String base64Pic;
    private Bitmap orc_bitmap;

    private ImageUtil u = new ImageUtil(this);

    //Glide请求图片选项配置
    private RequestOptions requestOptions = RequestOptions.circleCropTransform()
            .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
            .skipMemoryCache(true);//不做内存缓存

    //presenter
    private UserPresenter userPresenter = new UserPresenter(this);

    //dialog
    private BottomSheetDialog bottomSheetDialog;
    private View bottomView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //设置状态栏透明
        StatusBar_to_transparent(this);
        //状态栏文字自适应
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_information);

        initWidget();
    }

    private void initWidget(){
        Log.d("mytag","init");
        btn_return = findViewById(R.id.btn_return);
        btn_about = findViewById(R.id.btn_about);
        btn_update = findViewById(R.id.btn_update);
        btn_logout = findViewById(R.id.btn_logout);
        iv_avatar = findViewById(R.id.iv_avatar);
        tv_nickName = findViewById(R.id.nickName);
        btn_return.setOnClickListener(view -> {
            finish();
        });
        btn_about.setOnClickListener(view -> {
            AboutActivity.actionStart(UserActivity.this);
        });
        btn_update.setOnClickListener(view -> {
            showMsg("当前为最新版本！");
        });
        btn_logout.setOnClickListener(view -> {
            LoginActivity.actionStart(this);
            this.finish();
            //HomePageActivity.finish();
        });
        //初始化信息
        userPresenter.initUserInfo();
    }


    /*
    返回到Activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            //拍照后返回
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    u.cropPhoto(CROP_PHOTO);
                }
                break;
            case CROP_PHOTO:
                if(resultCode == RESULT_OK){
                    Bitmap bitmap = getBitmapFromUri(u.getImageUri());
                    iv_avatar.setImageBitmap(bitmap);// 显示图片
                    userPresenter.uploadAvatar(u.getImageFile());
                }
                break;
            //打开相册后返回
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK && data != null) {
                     //String path = u.uriToPath(data.getData());
                    imageUri = data.getData();
                    Log.d("UserActivity",imageUri.toString());
                    Bitmap bitmap = getBitmapFromUri(imageUri);
                    //Glide.with(this).clear(iv_avatar);
                    iv_avatar.setImageBitmap(bitmap);
                    //Log.d(TAG,file);
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                        //4.4及以上系统使用这个方法处理图片
                        imagePath = CameraUtils.getImageOnKitKatPath(data,this);
                    } else {
                        imagePath = CameraUtils.getImageBeforeKitKatPath(data, this);
                    }
                    String path = u.uriToPath(imageUri);
                    File file = new File(imagePath);
                    Log.d(TAG,imagePath);
                    userPresenter.uploadAvatar(file);
                }
                break;
            //修改昵称后返回
            case CHANGE_NICKNAME:
                if(data!=null){
                    String nickName = data.getStringExtra("nickname");
                    tv_nickName.setText(nickName);
                    userPresenter.uploadNickname();
                }
                break;
            default:
                break;
        }
    }

    public String getImagePath(){
        return imagePath;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showMsg("获取摄像头权限~");
                    //doTake();//去往拍照功能
                } else {
                    Toast.makeText(this, "你没有获得摄像头权限~", Toast.LENGTH_SHORT).show();
                }
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showMsg("获取访问相册权限~");
                } else {
                    Toast.makeText(this, "你没有获得访问相册的权限~", Toast.LENGTH_SHORT).show();
                }
        }
    }

    /*
     更换头像
     */
    public void changeAvatar(View view) {
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomView = getLayoutInflater().inflate(R.layout.headdialog_bottom, null);
        bottomSheetDialog.setContentView(bottomView);
        bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundColor(Color.TRANSPARENT);
        TextView tv_takePhoto = bottomView.findViewById(R.id.tv_take_pictures);
        TextView tv_openAlbum = bottomView.findViewById(R.id.tv_open_album);
        TextView tv_Cancel = bottomView.findViewById(R.id.tv_cancel);
        //底部弹窗显示
        bottomSheetDialog.show();

        //拍照
        tv_takePhoto.setOnClickListener(v -> {
            showMsg("拍照");
            bottomSheetDialog.cancel();
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                // 执行拍照
                //doTake();
                Log.d("UserActivity","test");
//                userPresenter.takePhoto(TAKE_PHOTO);
                u.takePhoto(TAKE_PHOTO);
            } else {
                // 申请权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            }
        });
        //打开相册
        tv_openAlbum.setOnClickListener(v -> {
            //userPresenter.selectPhoto(SELECT_PHOTO);
            u.openAlbum(SELECT_PHOTO);
            showMsg("打开相册");
            bottomSheetDialog.cancel();
        });
        //取消
        tv_Cancel.setOnClickListener(v -> {
            bottomSheetDialog.cancel();
        });
    }

    /*
    将uri转换为bitmap
     */
    private Bitmap getBitmapFromUri(Uri uri){
        try{
            Bitmap bitmap = BitmapFactory.decodeStream(
                    getContentResolver().openInputStream(uri));
            return bitmap;
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

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

        TextView tv_changeNick = bottomView.findViewById(R.id.changeNickName);
        TextView tv_cancel = bottomView.findViewById(R.id.cancel);

        //修改昵称
        tv_changeNick.setOnClickListener(v -> {
            //Intent intent = ChangeNickActivity.newIntent(UserActivity.this, mNick);
            Intent intent = new Intent(UserActivity.this, ChangeNickActivity.class);
            intent.putExtra(EXTRA_NICKNAME,tv_nickName.getText());
            startActivityForResult(intent,CHANGE_NICKNAME);
            showMsg("修改昵称");
            bottomSheetDialog.cancel();
        });
        //取消
        tv_cancel.setOnClickListener(v -> {
            bottomSheetDialog.cancel();
        });
    }

    @Override
    public void initNickname(String name) {
        tv_nickName.setText(name);
    }

    @Override
    public void initAvatar(String url){
        try{
            URL murl = new URL(url);
            Glide.with(this).load(murl)
                    .apply(new RequestOptions().placeholder(R.mipmap.tou_xiang)).into(iv_avatar);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String getNickname() {
        return tv_nickName.getText().toString();
    }

    @Override
    public Uri getImageUri() {
        return imageUri;
    }

    @Override
    public Context getContext() {
        return this;
    }
}

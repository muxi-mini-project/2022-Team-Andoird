package com.example.team.user.view;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.example.team.R;
import com.example.team.StatusBar;
import com.example.team.login.logining.LoginActivity;
import com.example.team.user.presenter.UserPresenter;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity extends StatusBar implements IUserView {

    private static final String EXTRA_AVATAR = "avatar";
    public static final String EXTRA_NICKNAME = "nickName";

    //启动相机标识
    private static final int TAKE_PHOTO = 1;
    //启动相册标识
    private static final int SELECT_PHOTO = 2;
    //裁剪图片标识
    private static final int CROP_PHOTO = 3;
    //修改昵称标识
    private static final int CHANGE_NICKNAME = 4;

    private Uri imageUri;

    private Button btn_return,btn_about,btn_update,btn_logout;
    private TextView tv_nickName;
    private CircleImageView iv_avatar;

    //presenter
    private UserPresenter userPresenter = new UserPresenter(this);

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

        //接收HomePagerActivity的图片信息
        //image = getIntent().getStringExtra(EXTRA_AVATAR);
        //mNick = getIntent().getStringExtra(EXTRA_NICKNAME);
    }

    private void initWidget(){
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
            //AboutActivity.actionStart(UserActivity.this);
        });
        btn_update.setOnClickListener(view -> {
            showMsg("当前为最新版本！");
        });
        btn_logout.setOnClickListener(view -> {
            LoginActivity.actionStart(this);
            this.finish();
        });
        //initNickname();
        //SharedPreferences.Editor editor = getSharedPreferences("data").edit();
        //String token = new LoginActivity().restoreData("token");
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2Nzg3MDk5NTYsImlhdCI6MTY3ODEwNTE1NiwiaWQiOjN9._ivCwbvCt1XHCURDywO2wPC_nYJOnX5ZeEY8Olw4ue0";
        try{
            userPresenter.initUserInfo(token);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /*
    拍照
     */
    private void takePhoto(){
        File avatarImage = new File(getExternalCacheDir(),"avatar_iamge.jpg");
        if(avatarImage.exists()){
            avatarImage.delete();
        }
        try{
            avatarImage.createNewFile();
        }catch(IOException e){
            e.printStackTrace();
        }

        //获取imageUri
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            imageUri = FileProvider.getUriForFile(this,
                    "com.example.team.user.UserActivity.fileprovider",avatarImage);
        }else{
            imageUri = Uri.fromFile(avatarImage);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,TAKE_PHOTO);
    }

    /*
    从相册选择照片
     */
    private void selectPhoto(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent,SELECT_PHOTO);
    }

    /*
    裁剪图片
     */
    private void cropPhoto(){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(imageUri,"image/*");
        intent.putExtra("scale",true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        startActivityForResult(intent,CROP_PHOTO);
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
            takePhoto();
        });
        //打开相册
        tv_openAlbum.setOnClickListener(v -> {
            selectPhoto();
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
                    Log.d("mytag",imageUri.toString());
                    //Log.d("mytag",imageUri.toString());
                    cropPhoto();
                }
                break;
            case CROP_PHOTO:
                if(resultCode == RESULT_OK){
                    //imageUri = data.getData();
                    Bitmap bitmap = getBitmapFromUri(imageUri);
                    iv_avatar.setImageBitmap(bitmap);
                }
                break;
            //打开相册后返回
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK && data != null) {
                    Uri imageUri = data.getData();
                    Bitmap bitmap = getBitmapFromUri(imageUri);
                    iv_avatar.setImageBitmap(bitmap);
                    //WebRequest2(new File(image));
                }
                break;
            //修改昵称后返回
            case CHANGE_NICKNAME:
                String nickName = data.getStringExtra("nickname");
                tv_nickName.setText(nickName);
                break;
            default:
                break;
        }
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
    public void initAvatar() {

    }
}

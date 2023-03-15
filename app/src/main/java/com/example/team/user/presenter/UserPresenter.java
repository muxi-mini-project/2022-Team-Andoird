package com.example.team.user.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.team.MyApplication;
import com.example.team.login.logining.LoginUser;
import com.example.team.user.model.UserModel;
import com.example.team.user.model.requestListener;
import com.example.team.user.view.IUserView;

import java.io.File;
import java.io.IOException;

public class UserPresenter {
    private UserModel userModel;
    private IUserView userView;
    private Uri imageUri;
    private AppCompatActivity context;

    public UserPresenter(IUserView view){
        this.userModel = new UserModel();
        //userModel.getUserinfo();
        this.userView = view;
        context = (AppCompatActivity)userView.getContext();
    }

    /*
    初始化信息
     */
    public void initUserInfo(){
        userModel.getUserinfo(new requestListener() {
            @Override
            public void onSuccess() {
                LoginUser.Data info = userModel.getUser().getData();
                userView.initNickname(info.getNickname());
                userView.initAvatar(info.getAvatar());
            }

            @Override
            public void onFailed() {

            }
        });
    }

    public void uploadNickname(){
        String nickname = userView.getNickname();
        userModel.setNickRequest(nickname);
    }

    public void uploadAvatar(){
        File file = userView.getImagefile();
        userModel.setAvatar(file);
    }

    /*
    拍照
     */
    public void takePhoto(int requestId){
        File avatarImage = new File(context.getExternalCacheDir(),"avatar.jpg");
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
            imageUri = FileProvider.getUriForFile(context,
                    "UserActivity.fileprovider",avatarImage);
        }else{
            imageUri = Uri.fromFile(avatarImage);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        context.startActivityForResult(intent,requestId);
    }

    public Uri getImageUri(){
        return imageUri;
    }

    /*
    裁剪图片
     */
    public void cropPhoto(AppCompatActivity activity,int requestId){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(imageUri,"image/*");
        intent.putExtra("scale",true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        //允许读写文件
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        activity.startActivityForResult(intent,requestId);
    }

    /*
    从相册选择照片
     */
    public void selectPhoto(int requestId){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        context.startActivityForResult(intent,requestId);
    }
}

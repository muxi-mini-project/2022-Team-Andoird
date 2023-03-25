package com.example.team.user.presenter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.team.MyApplication;
import com.example.team.login.login_ok.utils.CameraUtils;
import com.example.team.login.logining.LoginUser;
import com.example.team.user.model.UserModel;
import com.example.team.user.model.requestListener;
import com.example.team.user.view.IUserView;
import com.example.team.user.view.UserActivity;

import java.io.File;
import java.io.IOException;

public class UserPresenter {
    private UserModel userModel;
    private IUserView userView;
    private UserActivity context;

    private Uri imageUri;
    public File imageFile;

    public UserPresenter(UserActivity view){
        this.userModel = new UserModel();
        this.userView = view;
        this.context = (UserActivity) userView.getContext();
    }

    public static void main(String[] args) {
        System.out.println("hello,world");
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

    public void uploadAvatar(File file){
        //File file = userView.getImagefile();
        //Log.d("UserPresenter",imageFile.toString());
//        if(imageFile==null) {
//            String path = context.getImagePath();
//            imageFile = new File(path);
//        }
        userModel.setAvatarRequest(file);
    }



    /*
    拍照
     */
    public void takePhoto(int requestId){

        imageFile = new File(context.getExternalCacheDir(),"avatar.jpg");
        Log.d("UserPresenter",imageFile.toString());

        if(imageFile.exists()){
            imageFile.delete();
        }
        try{
            imageFile.createNewFile();
        }catch(IOException e){
            e.printStackTrace();
        }
        //获取imageUri
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            imageUri = FileProvider.getUriForFile(context,
                    "UserActivity.fileprovider",imageFile);
        }else{
            imageUri = Uri.fromFile(imageFile);
        }
        Intent intent = new Intent();
        intent.setAction("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        //Intent intent = CameraUtils.getTakePhotoIntent(context,imageFile);
//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        context.startActivityForResult(intent,requestId);
    }

    public Uri getImageUri(){
        return imageUri;
    }

    /*
    裁剪图片
     */
    public void cropPhoto(int requestId){
        context.startActivityForResult(CameraUtils.getSelectPhotoIntent(),requestId);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(imageUri,"image/*");
        intent.putExtra("scale",true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        //允许读写文件
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        context.startActivityForResult(intent,requestId);
    }

    /*
    从相册选择照片
     */
    public void selectPhoto(int requestId){
//        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("image/*");
        //imageUri = intent.getData();
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        //startActivityForResult(intent, REQUEST_CODE_CHOOSE);
        context.startActivityForResult(intent,requestId);

    }
}

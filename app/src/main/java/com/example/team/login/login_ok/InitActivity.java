package com.example.team.login.login_ok;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.example.team.MyApplication;
import com.example.team.R;
import com.example.team.user.model.UserModel;
import com.example.team.user.presenter.UserPresenter;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class InitActivity extends AppCompatActivity {
    //启动相机标识
    private static final int TAKE_PHOTO = 1;
    //启动相册标识
    private static final int SELECT_PHOTO = 2;
    //裁剪图片标识
    private static final int CROP_PHOTO = 3;

    private CircleImageView iv_avatar;
    private EditText et_nickname;
    private Button btn_finish;

    private File avatarImage;
    private Uri imageUri;

    private UserModel userModel = new UserModel();

    public static void actionStart(Context context){
        Intent intent = new Intent(context,InitActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_dialog);
        initWidgets();
    }

    private void initWidgets(){
        iv_avatar = findViewById(R.id.iv_avatar);
        et_nickname = findViewById(R.id.et_nickName);
        btn_finish = findViewById(R.id.btn_finish);

        iv_avatar.setOnClickListener(view -> {
            changeAvatar();
        });

        btn_finish.setOnClickListener(view -> {
            String nickname = et_nickname.getText().toString();
            userModel.setNickRequest(nickname);
            //userModel.setAvatarRequest(avatarImage);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            //拍照后返回
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    //Log.d("mytag",imageUri.toString());
                    //Log.d("mytag",imageUri.toString());
                    cropPhoto();
                }
                break;
            case CROP_PHOTO:
                if(resultCode == RESULT_OK){
                    //imageUri = data.getData();
                    //Uri imageUri = userPresenter.getImageUri();
                    Bitmap bitmap = getBitmapFromUri(imageUri);
                    Glide.with(this).clear(iv_avatar);
                    iv_avatar.setImageBitmap(bitmap);
                    //userModel.setAvatarRequest(imageUri);
                }
                break;
            //打开相册后返回
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK && data != null) {
                    Uri imageUri = data.getData();
                    Bitmap bitmap = getBitmapFromUri(imageUri);
                    Glide.with(this).clear(iv_avatar);
                    iv_avatar.setImageBitmap(bitmap);
                    //userPresenter.uploadAvatar();
                }
                break;
        }
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
         更换头像
         */
    public void changeAvatar() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomView = getLayoutInflater().inflate(R.layout.headdialog_bottom, null);
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

    private void takePhoto(){
        avatarImage = new File(getExternalCacheDir(),"avatar.jpg");
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
                    "UserActivity.fileprovider",avatarImage);
        }else{
            imageUri = Uri.fromFile(avatarImage);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,TAKE_PHOTO);
    }

    /*
    裁剪图片
     */
    public void cropPhoto(){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(imageUri,"image/*");
        intent.putExtra("scale",true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        //允许读写文件
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        startActivityForResult(intent,CROP_PHOTO);
    }

    /*
    从相册选择照片
     */
    public void selectPhoto(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent,SELECT_PHOTO);
    }

    private void showMsg(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}

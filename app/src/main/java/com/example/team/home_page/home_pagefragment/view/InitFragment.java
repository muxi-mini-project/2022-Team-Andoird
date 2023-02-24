package com.example.team.home_page.home_pagefragment.view;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.team.Api;
import com.example.team.R;
import com.example.team.home_page.home_pagefragment.Callback.Callback;
import com.example.team.home_page.home_pagefragment.net.InitAPI;
import com.example.team.login.login_ok.utils.BitmapUtils;
import com.example.team.login.login_ok.utils.CameraUtils;
import com.example.team.login.logining.LoginUser;
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

public class InitFragment extends DialogFragment {

    private EditText nickname;
    private Button mButton;
    private Callback mCallback;

    private String name;
    private String token;

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
    //提高作用域
    private String imagePath = null;
    private File file;
    //启动相机标识
    public static final int TAKE_PHOTO = 1;
    //启动相册标识
    public static final int SELECT_PHOTO = 2;
    //图片控件
    private ShapeableImageView ivHead;
    //
    private String image;
    //Base64
    private String base64Pic;
    //拍照和相册获取图片的Bitmap
    private Bitmap orc_bitmap;

    //Glide请求图片选项配置
    private RequestOptions requestOptions = RequestOptions.circleCropTransform()
            .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
            .skipMemoryCache(true);//不做内存缓存

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //点击外部不消失
        this.getDialog().setCanceledOnTouchOutside(false);
        //点击返回键不消失
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });

        View view = inflater.inflate(R.layout.init_dialog, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initWidgets();
        ivHead = view.findViewById(R.id.init_dialog3);
        ivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeAvatar(v);
            }
        });
        nickname = (EditText) view.findViewById(R.id.init_dialog5);
        mButton = (Button) view.findViewById(R.id.init_dialog6);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //回调大法
                name = nickname.getText().toString();
                mCallback.SetName(name);
                if (image == null) {
                    //
                } else {
                    mCallback.SetHead(image);
                }
                if (image == null || name.equals(""))
                    Toast.makeText(getActivity(), "请完善个人信息", Toast.LENGTH_SHORT).show();
                else {
                    //网络请求
                    WebRequest1(name);
                    if(outputImagePath==null){
                        file =new File(image);
                    }
                    else
                        file=outputImagePath;
                    WebRequest2(file);
                }

            }
        });
        //检查版本
        checkVersion();

        return view;
    }

    /**
     * 检查版本
     */
    private void checkVersion() {
        //Android6.0及以上版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //如果你是在Fragment中，则把this换成getActivity()
            rxPermissions = new RxPermissions(getActivity());
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
    public void ChangeAvatar(View view) {
        bottomSheetDialog = new BottomSheetDialog(getActivity());
        //底部弹窗
        bottomView = getLayoutInflater().inflate(R.layout.headdialog_bottom, null);
        //
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
        outputImagePath = new File(getActivity().getExternalCacheDir(),
                filename + ".jpg");
        Intent takePhotoIntent = CameraUtils.getTakePhotoIntent(getActivity(), outputImagePath);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //拍照后返回
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    //显示图片
                    image = outputImagePath.getAbsolutePath();
                    displayImage(outputImagePath.getAbsolutePath());
                }
                break;
            //打开相册后返回
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    imagePath = null;
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                        //4.4及以上系统使用这个方法处理图片
                        imagePath = CameraUtils.getImageOnKitKatPath(data, getActivity());
                    } else {
                        imagePath = CameraUtils.getImageBeforeKitKatPath(data, getActivity());
                    }
                    //显示图片
                    image = imagePath;
                    displayImage(imagePath);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 通过图片路径显示图片
     */
    private void displayImage(String imagePath) {
        if (!TextUtils.isEmpty(imagePath)) {
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


    /**
     * Toast提示
     *
     * @param msg
     */
    private void showMsg(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    //实例化
    public static InitFragment newInstance() {
        InitFragment fragment = new InitFragment();
        return fragment;
    }

    //接口实例
    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    private void initWidgets() {
        //fragment中得getActivity
        SharedPreferences sharedPreferences2 = getActivity().getSharedPreferences("Token", 0);
        token = sharedPreferences2.getString("Token", null);
    }

    //网络请求POST---nickname
    private void WebRequest1(String nickname) {
        //api实例
        Retrofit retrofit = Api.getInstance().getApi();
        //对象，用body
        LoginUser.DataDTO mLoginUser = new LoginUser.DataDTO(nickname);
        //web实例
        InitAPI mInit = retrofit.create(InitAPI.class);
        //call实例
        Call<LoginUser> call = mInit.initNick(token, mLoginUser);
        //异步网络请求
        call.enqueue(new retrofit2.Callback<LoginUser>() {
            @Override
            public void onResponse(Call<LoginUser> call, Response<LoginUser> response) {
                if (response.isSuccessful()) {
                    //Toast.makeText(getActivity(), "信息初始化成功", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(getActivity(), "信息初始化失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginUser> call, Throwable t) {
                Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getActivity(),"头像设置成功",Toast.LENGTH_SHORT).show();
                    mCallback.dismiss();
                }
                else{
                    Toast.makeText(getActivity(),"头像设置失败",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginUser> call, Throwable t) {
                Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
                //抛出异常
                t.printStackTrace();
                Log.e("tag", t.getMessage());
            }

        });
    }
}


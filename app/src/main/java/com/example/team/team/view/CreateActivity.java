package com.example.team.team.view;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.team.Api;
import com.example.team.R;
import com.example.team.StatusBar;
import com.example.team.home_page.home_pagefragment.Callback.Callback2;
import com.example.team.team.Bean.TeamData;
import com.example.team.team.net.TeamAPI;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreateActivity extends StatusBar implements View.OnClickListener{
    private ImageView iv_photo;
    private static final int TAKE_PHOTO = 0X66;
    private static final int PICK_PHOTO = 0X88;
    private Bitmap bitmap;
    public static SharedPreferences sharedPreferences;
    private final String fileName = "Img_team.jpg";
    private String path;
    private ImageButton mBackButton;
    private Button mCreateButton,mShareButton;
    private TextView mChange;
    private EditText mName,mCode,mIntro;
    private TeamImageChange t = new TeamImageChange(CreateActivity.this);
    private String name,code;
    private String token;
    private static Callback2 mCallback2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBar_to_transparent(this);
        //状态栏文字自适应
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        init();
    }

    private void init() {

        iv_photo = findViewById(R.id.create_image);
        iv_photo.setOnClickListener(this);

        mBackButton = findViewById(R.id.create_back);
        mBackButton.setOnClickListener(this);

        mCreateButton = findViewById(R.id.create_finish);
        mCreateButton.setOnClickListener(this);

        mChange = findViewById(R.id.create_change);
        mChange.setOnClickListener(this);

//        mShareButton = findViewById(R.id.create_share);
//        mShareButton.setOnClickListener(this);

        mName = findViewById(R.id.create_name);
        mCode = findViewById(R.id.create_code);
//        mIntro = findViewById(R.id.create_intro);

        /*实现加载*/
        sharedPreferences = this.getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        String string = sharedPreferences.getString("getFilePath",null);
        if(string!=null){
            Bitmap bitmap = BitmapFactory.decodeFile(string);
            iv_photo.setImageBitmap(bitmap);
        }

        SharedPreferences sharedPreferences2 = this.getSharedPreferences("Token", 0);
        token = sharedPreferences2.getString("Token", null);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == TAKE_PHOTO) {
                if (t.getFile() == null || !t.getFile().exists()) {
                    iv_photo.setImageBitmap(null);
                } else {
                    Bitmap bitmap = t.getScaledBitmap(t.getFile().getPath(), CreateActivity.this);
                    iv_photo.setImageBitmap(bitmap);
                    savePhotos(t.getFile().getPath());

                    //网络请求
//                    userPresenter.changeAvatar(t.getFile().getPath(),token);

                }

            }else if(requestCode==PICK_PHOTO){
                Uri uri = data.getData();
                CreateActivity.this.revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                Log.d("TRy", uri.toString());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    path = t.handleImageOnKitKat(uri);
                } else {
                    path = t.handleImageBeforeKitKat(uri);
                }

                Bitmap bitmap = BitmapFactory.decodeFile(path);
                Log.d("相册",path);
                iv_photo.setImageBitmap(bitmap);
                savePhotos(path);


            } else {
                Log.d("Demo", "结果无");
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create_image:
            case R.id.create_change:
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                    if (!checkPermission()) {
                        requestPermissions(PICK_PHOTO);
                    }
                    if (!checkPermission()) {
                        requestPermissions(TAKE_PHOTO);
                    }
                }
                t.showDialog();
                Log.d("Demo", "点击");
                break;
            case R.id.create_back:
                finish();
                break;
            case R.id.create_finish:
                name = mName.getText().toString();
                code = mCode.getText().toString();
//                intro = mIntro.getText().toString();
                if(code.isEmpty()||name.isEmpty()||path.isEmpty()||(t.getFile() == null || !t.getFile().exists())){
                    Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    WebRequest2(t.getFile(), name, code);
                }

                break;
//            case R.id.create_share:
//                name = mName.getText().toString();
//                code = mCode.getText().toString();
//                intro = mIntro.getText().toString();
//                if(code.isEmpty()||name.isEmpty()||intro.isEmpty()){
//                    Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
//                }else{
//
//                    showInputDialog();
//
//                    Intent share_intent = new Intent();
//                    share_intent.setAction(Intent.ACTION_SEND);//设置分享行为
//                    share_intent.setType("image/*");  //设置分享内容的类型
//                    share_intent.putExtra(Intent.EXTRA_STREAM, saveBitmap(bitmap));
//                    //创建分享的Dialog
//                    share_intent = Intent.createChooser(share_intent, "分享至");
//                    startActivity(share_intent);
//                }

            default:break;
        }

    }
    /*动态权限请求*/
    public boolean checkPermission() {
        boolean haveCameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        boolean haveWritePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        return haveCameraPermission && haveWritePermission;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermissions(int request) {
        switch (request) {
            case TAKE_PHOTO:
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, TAKE_PHOTO);
                break;
            case PICK_PHOTO:
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_PHOTO);
                break;
        }
    }

    /*实现推出后再加载*/
    public void savePhotos(String filePath) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("getFilePath",filePath);
        editor.commit();
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
                    iv_photo.setImageResource(R.drawable.ic_image);
                    CreateActivity.this.finish();


                } else {
                    showCode(response.body().getCode());
                }
            }

            @Override
            public void onFailure(Call<TeamData> call, Throwable t) {
                Toast.makeText(CreateActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                //抛出异常
                t.printStackTrace();
                Log.e("tag", t.getMessage());
            }

        });
    }

    public static void setCallback2(Callback2 callback2) {
        mCallback2 = callback2;
    }

    private void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void showCode(int code) {
        Toast.makeText(this, code, Toast.LENGTH_SHORT).show();
    }

//    public static Bitmap Create2DCode(String str) {
//        //生成二维矩阵,编码时要指定大小,
//        //不要生成了图片以后再进行缩放,以防模糊导致识别失败
//        try {
//            BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, 240, 240);
//            int width = matrix.getWidth();
//            int height = matrix.getHeight();
//            //  二维矩阵转为一维像素数组（一直横着排）
//            int[] pixels = new int[width * height];
//            for (int y = 0; y < height; y++) {
//                for (int x = 0; x < width; x++) {
//                    if (matrix.get(x, y)) {
//                        pixels[y * width + x] = 0xff000000;
//                    }
//                }
//            }
//            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//            // 通过像素数组生成bitmap, 具体参考api
//            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
//            return bitmap;
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    private void showInputDialog() {
//
//        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_qrcode, null, false);
//        final PopupWindow popupWindow = new PopupWindow(v, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        //参数为1.View 2.宽度 3.高度
//        popupWindow.setOutsideTouchable(true);//设置点击外部区域可以取消popupWindow
//        popupWindow.setFocusable(true);
//        backgroundAlpha(0.5f);
//        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                backgroundAlpha(1.0f);
//            }
//        });
//
//
//        TextView textView = (TextView)v.findViewById(R.id.text1);
//        ImageView imageView = (ImageView)v.findViewById(R.id.qrcode);
//
//        bitmap = Create2DCode(name+code);
//
//        imageView.setImageBitmap(bitmap);
//
//        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
//
//    }
//
//    public void backgroundAlpha(float v) {
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.alpha = v; //0.0-1.0
//        getWindow().setAttributes(lp);
//    }
//
//    /** * 将图片存到本地 */
//    private Uri saveBitmap(Bitmap bm) {
//        String sdStatus = Environment.getExternalStorageState();
//        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
//            Toast.makeText(this, "内存卡异常，请检查内存卡插入是否正确", Toast.LENGTH_SHORT).show();
//            return null;
//        }
//        String path = System.currentTimeMillis() + ".jpg";
//        File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/qrcode/");
//        if (!f.exists()) {
//            f.mkdir();
//        }
//        File file = new File(f, path);
//        try {
//            file.createNewFile();
//            FileOutputStream fOut = new FileOutputStream(file);
//            bm.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
//            fOut.flush();
//            fOut.close();
//            Uri uri = Uri.fromFile(file);
//            return uri;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//
//    }


}

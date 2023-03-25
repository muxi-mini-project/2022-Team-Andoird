package com.example.team.team.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.example.team.R;

import java.io.File;

public class TeamImageChange {
    private final Activity activity;
    private static File file;
    private Uri uri;
    private final String fileName = "IMG_team.jpg";
    private static final int TAKE_PHOTO = 0X66;
    private static final int PICK_PHOTO = 0X88;

    public TeamImageChange(Activity activity){
        this.activity = activity;
    }

    /**
     * 点击出现弹窗
     */
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        AlertDialog dialog = builder.create();
        View v = View.inflate(activity, R.layout.team_dialog, null);
        TextView textView2 = (TextView) v.findViewById(R.id.team_dialog_camera);
        TextView textView3 = (TextView) v.findViewById(R.id.team_dialog_album);
        TextView textView4 = (TextView) v.findViewById(R.id.team_dialog_cancel);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartCamera();
                dialog.dismiss();
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChoosePhoto();
                dialog.dismiss();
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.setView(v);
        dialog.show();

    }

    /**
     * 调用相机
     */
    public void StartCamera(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d("Demo","进行到high");
            TakePhoto_high();;
        }else{
            Log.d("Demo","进行到low");
            TakePhoto_low();
        }

    }

    /*  针对低版本的SDK */
    private void TakePhoto_low() {
        file = new File(activity.getFilesDir(), fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        uri = Uri.fromFile(file);
        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent1.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(intent1, TAKE_PHOTO);


    }

    /*针对android6.0后的所有版本，使用FileProvider来处理uri*/
    private void TakePhoto_high() {
        file = new File(activity.getFilesDir(), fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        uri = FileProvider.getUriForFile(activity, "com.example.android.teamkotlin.fileprovider", file);
        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent1.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(intent1, TAKE_PHOTO);
    }

    /**
     * 调用相册经行选择
     */

    private void ChoosePhoto() {
        Intent intent2 = new Intent(Intent.ACTION_PICK);
        file = new File(activity.getFilesDir(), fileName);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            uri = FileProvider.getUriForFile(activity, "com.example.android.teamkotlin.fileprovider", file);
        }else{
            uri = Uri.fromFile(file);
        }
        intent2.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, uri);
        intent2.setDataAndType(uri, "image/*");
        activity.startActivityForResult(intent2, PICK_PHOTO);
    }





    /**
     * 回调处理方法
     */
    public String handleImageBeforeKitKat(Uri uri) {
        String path = getImagePath(uri, null);
        return path;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public  String handleImageOnKitKat(Uri uri) {
        String path = null;
        if (DocumentsContract.isDocumentUri(activity, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if (uri.getAuthority().equals("com.android.providers.media.documents")) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                path = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if (uri.getAuthority().equals("com.android.providers.downloads.documents")) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content: //downloads/public_downloads"), Long.valueOf(docId));
                path = getImagePath(contentUri, null);
            }
        } else if (uri.getScheme().equalsIgnoreCase("content")) {
            path = getImagePath(uri, null);

        } else if (uri.getScheme().equalsIgnoreCase("file")) {
            path = uri.getPath();

        }
        return path;
    }


    @SuppressLint("Range")
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = activity.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private static Bitmap getScaledBitmap(String path, int destWidth, int destHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path,options);
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

        int inSampleSize = 1;

        if(srcHeight>destHeight||srcWidth>destWidth){
            float ww = srcWidth/destWidth;
            float hh = srcHeight/destHeight;
            inSampleSize = Math.round(hh>ww?hh:ww);
        }

        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeFile(path,options);
    }

    public static Bitmap getScaledBitmap(String path, Activity activity){
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return getScaledBitmap(path,size.x,size.y);
    }

    public File getFile(){
        return file;
    }


}

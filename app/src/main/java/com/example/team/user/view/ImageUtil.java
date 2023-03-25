package com.example.team.user.view;

import android.app.Application;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.team.MyApplication;
import com.example.team.login.login_ok.utils.CameraUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ImageUtil {

    public File imageFile;
    public Uri imageUri;

    private AppCompatActivity context;
    public ImageUtil(AppCompatActivity context){
        this.context = context;
    }

    public Uri getImageUri(){
        return imageUri;
    }

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

    /*
    裁剪图片
     */
    public void cropPhoto(int requestId){
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

    public void openAlbum(int requestId){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        //startActivityForResult(intent, REQUEST_CODE_CHOOSE);
        context.startActivityForResult(intent,requestId);
    }

    public static String imageToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] buffer = byteArrayOutputStream.toByteArray();
        String baseStr = Base64.encodeToString(buffer, Base64.DEFAULT);
        return baseStr;
    }

    public static Bitmap base64ToImage(String bitmap64) {
        byte[] bytes = Base64.decode(bitmap64, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }

    public String uriToPath(Uri uri){
        if (Build.VERSION.SDK_INT < 19) {
            return getImagePath(uri);
        }
        return handleImageOnApi19(uri);
    }

    public File getImageFile(){
        if(imageFile != null){
            return imageFile;
        }else{
            return new File(uriToPath(imageUri));
        }
        //return file;
    }

    private void handleImageBeforeApi19(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri);
    }

    private String getImagePath(Uri uri) {
        String path = null;
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private String handleImageOnApi19(Uri uri) {
        String imagePath = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            String documentId = DocumentsContract.getDocumentId(uri);

            if (TextUtils.equals(uri.getAuthority(), "com.android.providers.media.documents")) {
                String id = documentId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            } else if (TextUtils.equals(uri.getAuthority(), "com.android.providers.downloads.documents")) {
                if (documentId != null && documentId.startsWith("msf:")) {
                    resolveMSFContent(uri, documentId);
                    //return;
                }
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                imagePath = getImagePath(contentUri);
            }

        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        return imagePath;
    }

    private void resolveMSFContent(Uri uri, String documentId) {

        File file = new File(context.getCacheDir(), "temp_file" + context.getContentResolver().getType(uri).split("/")[1]);
//先生成一个文件
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);

            OutputStream outputStream = new FileOutputStream(file);

            byte[] buffer = new byte[4 * 1024];//缓冲区
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();//清除

            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//            ivAvatar.setImageBitmap(bitmap);
//            imageBase64 = ImageUtil.imageToBase64(bitmap);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

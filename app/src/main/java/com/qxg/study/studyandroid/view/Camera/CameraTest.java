package com.qxg.study.studyandroid.view.Camera;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.qxg.study.studyandroid.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraTest extends AppCompatActivity {

    @BindView(R.id.img)
    ImageView img;

    Uri imageUri = null;
    private static final int TAKE_PICTURE = 0;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 2;
    private static final int WRITE_PERMISSION_REQUEST_CODE = 5;
    private static final int CHOOSE_PICTURE = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_test);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.take_picture, R.id.choose_picture})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.take_picture:
                //拍照并显示到ImageVIew中。

                //创建File对象，用于存储拍照后的图片，getExternalCacheDir是取得SD卡专门存储应用缓存的目录，路径是/sdcard/Android/data/<packagename>/cache
                File imgFile = new File(getExternalCacheDir(),"out_image.jpg");

                if(imgFile.exists()){
                    imgFile.delete();
                }

                try {
                    imgFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //取得iamgeUri,如果安卓版本低于7.0，直接通过Uri.fromFile()方法将file转换为Uri，这个Uri标记着file的真实路径，而安卓版本在7.0以上，则使用FileProvider.getUriForFile()来将file转换为封装过后的Uri对象，之所以这样是因为安卓7.0开始，直接使用本地真实路径的Uri会被认为是不安全的。
                if(Build.VERSION.SDK_INT >= 24){
                    //因为使用了FileProvider所以要对FileProvider进行注册
                    imageUri = FileProvider.getUriForFile(CameraTest.this,"com.qxg.study.studyandroid.fileprovider",imgFile);
                }else{
                    imageUri = Uri.fromFile(imgFile);
                }

                //启动相机程序
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

                //指定图片的输入地址，输出到imageUri标记的file中
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);

                //在开始之前，判断是否是安卓6.0以上,如果是，就检查CAMERA权限
                if(Build.VERSION.SDK_INT > 23){
                    if(ContextCompat.checkSelfPermission(CameraTest.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                        //如果没有申请权限，就申请
                        ActivityCompat.requestPermissions(CameraTest.this,new String[]{Manifest.permission.CAMERA},CAMERA_PERMISSION_REQUEST_CODE);
                    }else{
                        startActivityForResult(intent,TAKE_PICTURE);
                    }
                }
                break;
            case R.id.choose_picture:
                //需要READ_EXTERNAL_STORAGE或者WRITE_EXTERNAL_STORAGE
                if(ContextCompat.checkSelfPermission(CameraTest.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_PERMISSION_REQUEST_CODE);
                }else{
                    choosePicture();
                }
                break;
        }
    }

    private void choosePicture(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PICTURE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case CAMERA_PERMISSION_REQUEST_CODE:
                    if(grantResults != null && grantResults.length>0){
                        //已经被授权，就执行那啥方法，因为比较麻烦，就不写了。
                        Toast.makeText(this, "请重新点击拍照按钮", Toast.LENGTH_SHORT).show();
                    }
                break;
            case WRITE_PERMISSION_REQUEST_CODE:
                if(grantResults!=null && grantResults.length>0){
                    choosePicture();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PICTURE:
                if(resultCode == RESULT_OK){
                    //拍照成功，将图片显示出来
                    try {
                        Bitmap bitmap =
                                BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        img.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PICTURE:
                if(Build.VERSION.SDK_INT >= 19){
                    handleImageOnKitKat(data);
                }else{
                    handleImageBeforeKitKat(data);
                }
                break;
        }
    }

    private void handleImageBeforeKitKat(Intent data){
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(this,uri)){
            //如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1]; //解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            //如果是content类型的Uri，则使用普通方法处理
            imagePath = getImagePath(uri,null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            imagePath = uri.getPath();
        }

        displayImage(imagePath);
    }

    private String getImagePath(Uri uri,String selection){
        String path = null;
        //通过uri和Selection来获取真是的图片路径
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath){
        if(imagePath != null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            img.setImageBitmap(bitmap);
        }
    }
}

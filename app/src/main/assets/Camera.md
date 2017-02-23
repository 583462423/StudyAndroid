# 调用摄像头拍照

直接上代码：

点击拍照后执行的代码

```
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
    //因为使用了FileProvider所以要对FileProvider进行注册，注册的authority和第二个参数要一模一样
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
```

这里要注意，安卓7.0以上要使用FileProvider将File对象转换为Uri，所以要配置FileProvider，其配置如下：

```
<provider
android:name="android.support.v4.content.FileProvider"
android:authorities="com.qxg.study.studyandroid.fileprovider"
android:enabled="true"
android:exported="false"
android:grantUriPermissions="true">
<meta-data
    android:name="android.support.FILE_PROVIDER_PATHS"
    android:resource="@xml/file_paths" />
</provider>
```

一定要注意authorities中的值在所有的provider中都是唯一的，如果还有一个provider的名字跟FileProvider的这个配置中的authorities一样，运行的时候必然会出错。

在meta-data中的resource引用了xml目录下的file_paths所以还要配置file_paths.xml文件。

在res目录下新建xml文件夹，然后在xml文件夹下建file_paths.xml文件，

配置如下：
```
<?xml version="1.0" encoding="utf-8"?>
<paths xmlns:android="http://schemas.android.com/apk/res/android">
    <external-path name="my_iamges" path="" />
</paths>
```

因为拍照和存照片都用到对应的权限，所以记得添加权限，CAMERA权限是危险权限，所以安卓6.0以后的要申请权限。

```
    <uses-permission-sdk-23 android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission-sdk-23 android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```

最后配置拍照成功后的代码：
```
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
        }
    }
```
通过imageUri取得输入流，读取解析该对象变为Bitmap对象。之后显示就行。

# 直接从相册中选择图片

说实话，这里面的好多方法目前我还不理解什么意思，所以先把代码贴这，等日后再研究研究：

```
    private void choosePicture(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PICTURE);
    }

   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
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

```

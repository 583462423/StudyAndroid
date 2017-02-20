安卓6.0以后，权限的管理改变了很多，权限分为了两种，一种是危险权限，可能会威胁到用户的安全，一种是普通权限。对于危险权限，在程序运行的时候会询问客户是否允许这种权限。

# 危险权限

共9组24个权限

|权限组名|权限名|
|-------|-----|
|CALENDAR|READ_CALENDAR  WRITE_CALENDAR|
|CAMERA|CAMERA|
|CONTACATS|READ_CONTACTS,WRITE_CONTACTS_GET_ACCOUNTS|
|LOCATION|ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION|
|MICROPHONE|RECORD_AUDIO|
|PHONE|READ_PHONE_STATE,CALL_PHONE,READ_CALL_LOG,WRITE_CALL_LOG,ADD_VOICEMAIL,USE_SIP,PROCESS_OUTGOING_CALLS|
|SENSORS|BODY_SENSORS|
|SMS|SEND_SMS,RECEIVE_SMS,READ_SMS,RECEIVE_WAP_PUSH,RECEIVE_MMS|
|STORAGE|READ_EXTERNAL_STORAGE,WRITE_EXTERNAL_STORAGE|


# 在运行时申请权限

这里针对的是安卓6.0的情况，用户在运行某段需要某种危险权限的代码时，需要提前向用户申请。

比如我们要运行一段代码，来拨打电话，正常思路是直接使用Intent即可：
```
Intent intent = new Intent(Intent.ACTION_CALL);
intent.setData(Uri.parse("tel:10086"));
startActivity(intent);
```

但是安卓6.0以后就变了，必须在运行这段代码前申请权限，方式如下：

```
Intent intent = new Intent(Intent.ACTION_CALL);
intent.setData(Uri.parse("tel:10086"));
if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
    // TODO: Consider calling
    //    ActivityCompat#requestPermissions
    // here to request the missing permissions, and then overriding
    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
    //                                          int[] grantResults)
    // to handle the case where the user grants the permission. See the documentation
    // for ActivityCompat#requestPermissions for more details.
    return;
}else{
    startActivity(intent);
}

```

其中startActivity前边的判断的代码，是AS自动帮我们生成的。

首先使用checkSelfPermission来检验权限是否被授予，如果未授予就执行if里的方法,否则执行else代码。

可以看到其中的英文注释，使用ActivityCompat的requestPermissions来申请权限，在请求之后，会访问重写的方法onRequestPermissionResult方法。

所以我们修改代码如下：
```
Intent intent = new Intent(Intent.ACTION_CALL);
intent.setData(Uri.parse("tel:10086"));
if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
    //如果没有授予该权限，就申请权限
    ActivityCompat.requestPermissions(ProcessTest.this,new String[]{Manifest.permission.CALL_PHONE},1);
}else{
    startActivity(intent);
}
```

然后重写onRequestPermissionResult方法：

```
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //权限已经授予
                    call();
                }else{
                    //权限被拒绝

                }
                break;
        }
    }
```

这里说明一下，授予返回的结果会被封装在onRequestPermissionResult方法中的参数grantResults中。，其参数的值是与申请的权限一一对应，在前边的申请的权限的new String[]中传入几个，grantResults中就有几个值，且顺序对应，而0代表被授予权限，-1表示拒绝权限。

不过还有一个方法：

```
// Should we show an explanation?
if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
        Manifest.permission.READ_CONTACTS)) 
    // Show an expanation to the user *asynchronously* -- don't block
    // this thread waiting for the user's response! After the user
    // sees the explanation, try again to request the permission.

}
```

这个方法表示如果用户上一次拒绝了你的权限，那么就会弹出该对话框向用户解释为什么需要该权限。

所以一般申请权限的代码如下：

```
// Here, thisActivity is the current activity
if (ContextCompat.checkSelfPermission(thisActivity,
                Manifest.permission.READ_CONTACTS)
        != PackageManager.PERMISSION_GRANTED) {

    // Should we show an explanation?
    if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
            Manifest.permission.READ_CONTACTS)) {

        // Show an expanation to the user *asynchronously* -- don't block
        // this thread waiting for the user's response! After the user
        // sees the explanation, try again to request the permission.

    } else {

        // No explanation needed, we can request the permission.

        ActivityCompat.requestPermissions(thisActivity,
                new String[]{Manifest.permission.READ_CONTACTS},
                MY_PERMISSIONS_REQUEST_READ_CONTACTS);

        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
        // app-defined int constant. The callback method gets the
        // result of the request.
    }
}else{
    //DO SOMETHING(have granted)
}
```
注意解释权限后，一定还要申请权限，不然以后点击按钮都不会弹出申请权限的提示框，只会提示解释权限的提示框。



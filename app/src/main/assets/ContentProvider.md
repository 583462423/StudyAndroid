# ContentResolver
访问外部程序的共享数据，需要借助ContentResolver对象，通过Context获取方法`getContentResolver()`

该对象提供了一系列CRUD操作，其和SQLiteDatabase的区别于，该对象中的CRUD方法不用传入表明，而是传入URI

如程序：com.qxg.app中的表table1，其对应的URI就是`content://com.qxg.app.provider/table1`

所以通过URI非常清晰的表明要访问哪个程序的哪个数据库。

以查询为例：
```
resolver.query(
    uri,
    projection,
    selection,
    selectionArgs,
    sortOrder
);
```

参数描述如下：
* Uri uri：访问的数据表
* String[] projection:指定查询的列名
* String selection: 指定where约束条件
* String[] selectionArgs: 为where中的占位符提供具体值
* Strign sortOrder 指定排序方式


query返回的对象类型是Cursor，所以取得Cursor对象后，可以遍历进行操作：

```
if(cursor != null){
    while(cursor.moveToNext()){
        String column1 = cursor.getString(cursor.getColumnIndex("column1"));
        int column2 = cursor.getInt(cursor.getColumnIndex("column2"));
    }
    cursor.close();
}
```

那么如何添加数据呢？

```
ContentValues values = new ContentValues();
values.put("column1","some data");
values.put("column2",2);
getContentResolver().insert(uri,values);
```

更新数据：
```
ContentValues values = new ContentValues();
values.put("column1","修改后的数据");
getContentResolver().update(uri,values,"id = ?",new String[]{"1"});
```

删除数据：
```
getContentResolver().delete(uri,"id = ?",new String[]{"1"});
```

## 读取联系人
读取所有联系人写法如下
```
Cusor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI
,null, null ,null ,null);
```

其中参数后四个为null表示获取所有的联系人。
那么获取联系人姓名和手机号为：
```
String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

String phoneNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
```

不过要读取联系人，需要危险权：READ_CONTACTS,所以要记得申请。

大致代码如下：
```
    //首先要判断是否申请了权限
    if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
        //如果没有申请，就要申请权限
        //如果有必要，可以ActivityCompat.shouldShowRequestPermissionRationale()来解释为何要获取该权限
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},1);
    }else{
        readContacts();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //如果授权了
                    readContacts();
                }else{
                    //未授权
                    Toast.makeText(this, "授权失败，读取联系人失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }



    public void readContacts(){
        Cursor cursor =
                getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        if(cursor != null){
            while(cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactList.add(name + ":" + number);
                adapter.notifyDataSetChanged();
            }
            cursor.close();
        }
    }
```

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


# ContentProvider

创建自己的ContentProvider，创建一个类继承ContentProvider.需要重写以下6个方法：

* onCreate():初始化时调用。通常在这里完成对数据库的创建或升级操作，返回true表示初始化成功，返回则表示失败。该方法通常在程序开始运行的时候，就初始化了。0

* query():查询数据。使用uri参数确定查询哪张表。返回Cursor
* insert()：添加数据，添加完成后，返回一个用于表示这条新记录的URI
* update()：更新数据，返回受影响的行数。
* delete() 删除数据，返回被影响的行数
* getType():根据传入的内容URI来返回相应的MIME类型。

URI类型可以使用通配符：
其中"*"表示匹配任意长度的任意字符，"#"表示匹配任意长度的数字。

所以，一个能够匹配任意表的内容格式的URI就可以写成：`content://com.qxg.app.provider/*`,而匹配某数据表中任意一行数据的内容URI格式：`content://com.qxg.app.provider/table/#`

通过UriMatcher这个类，就可以轻松实现匹配内容URI的功能，该类中有一个addURI方法，分别传入authority,path和自定义代码。当调用UriMather的match()方法时，就可以将一个Uri对象传入。返回值就是我们自定义的代码，利用这个返回值，就可以判断用户要访问什么数据了。

那么自定义的ContentProvider就可以这样写：

```
    public static final int TABLE_DIR = 0;  //匹配整张表
    public static final int TABLE_ITEM = 1; //匹配某条数据

    private static UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.qxg.study.studyandroid.provider","table",TABLE_DIR);
        uriMatcher.addURI("com.qxg.study.studyandroid.provider","table/#",TABLE_ITEM);
    }


    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        switch (uriMatcher.match(uri)){
            case TABLE_DIR:
                //查询TABLE中的所有数据
                break;
            case TABLE_ITEM:
                //查询某条数据
                break;
        }
        return null;
    }
```
还有一个重要的方法getType()，它是所有Provider都必须实现的方法，用于获取Uri对象所对应的MIME类型。一个URI对应的MIME类型由3部分组成：
1. 必须以vnd开头
2. URI以路径结尾，则接android.cursor.dir/,如果URI以id结尾，则接android.cursor.item/
3. 最后接上 vnd.<authority>.<path>

所以content://com.qxg.study.studyandroid.provider/table对应：
`vnd.android.cursor.dir/vnd.com.qxg.study.studyandroid.provider.table`

所以对于getType中应该这么来写：
```
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case TABLE_DIR:
                return "vnd.android.cursor.dir/vnd.com.qxg.study.studyandroid.provider.table";
            case TABLE_ITEM:
                return "vnd.android.cursor.item/vnd.com.qxg.study.studyandroid.provider.table";

        }
        return null;
    }
```

那么如何开发一个可以被外部访问的provider，我们可以借助SQLiteOpenHelper，比如之前做的自定义helper类，可以借助于这个类来创建数据库或者升级数据库，之后查询数据，就可以通过提供的SQLiteDatabase来获取数据。

比如：
```

public class MyProvider extends ContentProvider {

    public static final int CAT_DIR = 0;  //匹配整张表
    public static final int CAT_ITEM = 1; //匹配某条数据
    public static final String AUTHORITY = "com.qxg.study.studyandroid.provider";
    private MyDBHelper helper;

    private static UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.qxg.study.studyandroid.provider","Cat",CAT_DIR);
        uriMatcher.addURI("com.qxg.study.studyandroid.provider","Cat/#",CAT_ITEM);
    }

    @Override
    public boolean onCreate() {
        helper = new MyDBHelper(getContext(),"MyDb.db",null,2);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)){
            case CAT_DIR:
                //查询TABLE中的所有数据
                cursor = db.query("Cat",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case CAT_ITEM:
                //查询某条数据
                String id = uri.getPathSegments().get(1);
                cursor = db.query("Cat",projection,"id=?",new String[]{id},null,null,sortOrder);
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case CAT_DIR:
                return "vnd.android.cursor.dir/vnd.com.qxg.study.studyandroid.provider.Cat";
            case CAT_ITEM:
                return "vnd.android.cursor.item/vnd.com.qxg.study.studyandroid.provider.Cat";

        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)){
            case CAT_DIR:
            case CAT_ITEM:
                long newId = db.insert("Cat",null,values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/Cat/" + newId);
                break;
        }
        return uriReturn;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int delSum = 0;
        switch (uriMatcher.match(uri)){
            case CAT_DIR:
                delSum = db.delete("Cat",selection,selectionArgs);
                break;
            case CAT_ITEM:
                String newId = uri.getPathSegments().get(1);
                delSum = db.delete("Cat","id=?",new String[]{newId});
                break;
        }
        return delSum;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int updateSum = 0;
        switch (uriMatcher.match(uri)){
            case CAT_DIR:
                updateSum = db.update("Cat",values,selection,selectionArgs);
                break;
            case CAT_ITEM:
                String newId = uri.getPathSegments().get(1);
                updateSum = db.update("Cat",values,"id=?",new String[]{newId});
                break;
        }
        return updateSum;
    }
}

```

注意代码编写完成后，还要到manifest文件中进行注册：
```
<provider
            android:authorities="com.qxg.study.studyandroid.provider"
            android:name=".provider.MyProvider"
            android:enabled="true"
            android:exported="true"/>
```


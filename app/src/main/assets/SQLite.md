SQLite是安卓自带的轻量级的关系型数据库。

# 创建数据库

为了更加方便管理数据库，安卓提供了一个叫SQLiteOpenHelper的帮助类。创建数据库和升级数据库都是借助这个类完成。但是SQLiteOpenHelper是抽象类，所以我们要创建一个类继承于他。

其中必须要实现的方法是:`onCreate()`和`onU[grade()`分别表示创建和升级。

SQLiteOpenHelper还有两个重要的方法，getReadableDatabase()和getWriteableDatabase()，用来创建或打开一个现有的数据库(如果该数据库存在则打开，否则创建)，返回一个可对数据库进行读或写操作的对象。注意getWritableDatabase也是可读的。

对于SQLiteOpenHelper的构造方法一般使用4个参数的，第一个参数是context,第二个是数据库名，第三个一般传null,第四个表示数据库当前版本号，这个用于升级数据库时使用。

数据库文件存在于/data/data/<packageName>/databases/目录下。

来看一个简单示例，假设我们要创建一个名为MyDb的数据库，并且创建一张表名为Cat(id,name,type)，那么创建表的SQL代码如下：
```
create table Cat(
    id integer primary key autoincrement,
    name text,
    type text
)
```
那么创建操作应在helper类的onCreate中执行，执行代码如下：
```
//创建Cat表的SQL代码
private static final String CREATE_CAT = "create table Cat("
        +"id integer primary key autoincrement,"
        +"name text,"
        +"type text)";

@Override
public void onCreate(SQLiteDatabase db) {
    //执行SQL代码来创建表
    db.execSQL(CREATE_CAT);
    Toast.makeText(context,"创建Cat表成功",Toast.LENGTH_SHORT).show();
}
```

那么创建方式就是使用helper类执行getWritableDatabase()或getReadableDatabase()方法。
```
private MyDBHelper helper = new MyDBHelper(context,"MyDb.db",null,1);
helper.getWritableDatabase();//如果数据库存在，则直接返回一个操作数据库的对象，否则先创建，然后再返回。
```


# 升级数据库

如果要升级数据库，只需要new MyDBHelper()的时候，第四个参数传一个比之前版本高的数字即可。这个时候，onCreate()方法不会被执行，只会执行onUpgrader()方法。

假设我们想要升级数据库，创建一个新的表为User(id,name,age)，那么onUpgrade()方法如下：


```
//创建User表的SQL代码
private static final String CREATE_USER = "create table User("
        +"id integer primary key autoincrement,"
        +"name text,"
        +"age text)";


@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL(CREATE_USER);
    Toast.makeText(context,"升级并创建User表成功",Toast.LENGTH_SHORT).show();
}
```

那么什么时候开始执行呢：

```
helper = new MyDBHelper(this,"MyDb.db",null,2);将第四个参数变为2就好
db = helper.getWritableDatabase(); //执行到这，就开始执行onUpgrade方法了
```

# CRUD

使用自带的方法insert()，query()等等方法，我觉得不方便，所以就不去学了，这里总结下使用原生SQL语言访问方法，方法如下：
```
//添加数据
db.execSQL("insert into Cat(name,type) values(?,?)",new String[]{"xiaocat","dahua"});

//更新数据
db.execSQL("update Cat set name = ? where id = ?",new String[]{"xiaoming","2"});

//删除数据
db.execSQL("delete from User where id = ?",new String[]{"5"});

//查询数据，只有查询数据特殊，使用rawQuery方法，其他都用的是execSQL方法
Cursor cursor = db.rawQuery("select * from Cat",null);
if(cursor.moveToFirst()){
    do{
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String type = cursor.getString(cursor.getColumnIndex("type"));
        Toast.makeText(this, name + "," + type, Toast.LENGTH_SHORT).show();
    }while(cursor.moveToNext());
}
cursor.close();
```



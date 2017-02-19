package com.qxg.study.studyandroid.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by qxg on 17-2-19.
 */

public class MyDBHelper extends SQLiteOpenHelper {

    //创建Cat表的SQL代码
    private static final String CREATE_CAT = "create table Cat("
            +"id integer primary key autoincrement,"
            +"name text,"
            +"type text)";

    //创建User表的SQL代码
    private static final String CREATE_USER = "create table User("
            +"id integer primary key autoincrement,"
            +"name text,"
            +"age text)";

    private Context context;


    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //执行SQL代码来创建表
        db.execSQL(CREATE_CAT);
        Toast.makeText(context,"创建Cat表成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CREATE_USER);
        Toast.makeText(context,"升级并创建User表成功",Toast.LENGTH_SHORT).show();
    }
}

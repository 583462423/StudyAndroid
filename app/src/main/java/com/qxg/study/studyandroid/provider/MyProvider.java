package com.qxg.study.studyandroid.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.qxg.study.studyandroid.helper.MyDBHelper;

/**
 * Created by qxg on 17-2-21.
 */

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

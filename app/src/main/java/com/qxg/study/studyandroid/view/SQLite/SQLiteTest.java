package com.qxg.study.studyandroid.view.SQLite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.qxg.study.studyandroid.R;
import com.qxg.study.studyandroid.helper.MyDBHelper;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SQLiteTest extends AppCompatActivity {

    private MyDBHelper helper;
    SQLiteDatabase db;
    int createVersion = 1;
    int upgradeVersion = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_test);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.create_db, R.id.upgrade_db,R.id.add_cat,R.id.update_cat,R.id.delete_cat,R.id.select_cat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create_db:
                helper = new MyDBHelper(this,"MyDb.db",null,createVersion);
                db = helper.getWritableDatabase();
                break;
            case R.id.upgrade_db:
                helper = new MyDBHelper(this,"MyDb.db",null,upgradeVersion);
                db = helper.getWritableDatabase();
                break;

            case R.id.add_cat:
                db.execSQL("insert into Cat(name,type) values(?,?)",new String[]{"xiaocat","dahua"});
                break;

            case R.id.update_cat:
                db.execSQL("update Cat set name = ? where id = ?",new String[]{"xiaoming","1"});
                break;

            case R.id.delete_cat:
                db.execSQL("delete from Cat where id = ?",new String[]{"1"});
                break;

            case R.id.select_cat:
                int index = 1;
                Cursor cursor = db.rawQuery("select * from Cat",null);
                if(cursor.moveToFirst()){
                    do{
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String type = cursor.getString(cursor.getColumnIndex("type"));
                        Toast.makeText(this, index++ + ":" + name + "," + type, Toast.LENGTH_SHORT).show();
                    }while(cursor.moveToNext());
                }else{
                    Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
                }
                cursor.close();
                break;
        }
    }
}

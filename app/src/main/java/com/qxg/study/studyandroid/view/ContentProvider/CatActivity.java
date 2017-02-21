package com.qxg.study.studyandroid.view.ContentProvider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.qxg.study.studyandroid.R;
import com.qxg.study.studyandroid.adapter.RVAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CatActivity extends AppCompatActivity {
    @BindView(R.id.cats)
    RecyclerView cats;
    RecyclerView.Adapter adapter;
    List<String> catList;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.type)
    EditText type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);
        ButterKnife.bind(this);

        catList = new ArrayList<>();
        cats.setAdapter(adapter = new RVAdapter(this, catList));
        cats.setLayoutManager(new LinearLayoutManager(this));

        readCats();
    }

    private void readCats() {
        Cursor cursor =
                getContentResolver().query(Uri.parse("content://com.qxg.study.studyandroid.provider/Cat"), null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String type = cursor.getString(cursor.getColumnIndex("type"));

                catList.add(name + "," + type);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @OnClick(R.id.add)
    public void onClick() {
        //添加数据
        String catName = name.getText().toString();
        String catType = type.getText().toString();

        ContentValues values = new ContentValues();
        values.put("name",catName);
        values.put("type",catType);

        getContentResolver().insert(Uri.parse("content://com.qxg.study.studyandroid.provider/Cat"),values);

        catList.clear();
        readCats();
    }
}

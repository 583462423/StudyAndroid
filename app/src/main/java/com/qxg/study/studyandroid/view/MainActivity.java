package com.qxg.study.studyandroid.view;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.qxg.study.studyandroid.R;
import com.qxg.study.studyandroid.adapter.MdAdapter;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

//该activity主要目的是显示所有的activity的列表,后期再写
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.md_list)
    RecyclerView mdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        AssetManager as = getAssets();
        try {
            //获取lists下的列表
            String[] lists = as.list("");

            //获取完成后，显示在recyclerView中
            mdList.setAdapter(new MdAdapter(lists,this));
            mdList.setLayoutManager(new LinearLayoutManager(this));

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

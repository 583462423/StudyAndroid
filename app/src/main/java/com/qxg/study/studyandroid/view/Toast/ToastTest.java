package com.qxg.study.studyandroid.view.Toast;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.qxg.study.studyandroid.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.widget.Toast.makeText;

public class ToastTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast_test);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.toast_add_pic, R.id.custom_toast,R.id.simple_toast_position,R.id.simple_toast})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.simple_toast:
                makeText(this, "简单演示", Toast.LENGTH_SHORT).show();
                break;
            case R.id.simple_toast_position:
                Toast toast = Toast.makeText(this, "自定义位置(中间)", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                break;
            case R.id.toast_add_pic:
                Toast picToast = Toast.makeText(this,"添加图片",Toast.LENGTH_SHORT);
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setImageResource(R.drawable.test);
                ViewGroup layout = (ViewGroup) picToast.getView();
                layout.addView(imageView,0);
                picToast.show();
                break;
            case R.id.custom_toast:
                View customView = LayoutInflater.from(this).inflate(R.layout.custom_toast,null);
                Toast cusToast = new Toast(getApplicationContext());
                cusToast.setGravity(Gravity.CENTER,0,0);
                cusToast.setDuration(Toast.LENGTH_SHORT);
                cusToast.setView(customView);
                cusToast.show();
                break;
        }
    }
}

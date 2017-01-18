package com.qxg.study.studyandroid.view.AlertDialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.qxg.study.studyandroid.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlertDialogTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_dialog_test);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.simple_alter_dialog, R.id.alter_dialog_with_icon,R.id.alter_dialog_with_items,R.id.alter_dialog_with_item
    ,R.id.alter_dialog_with_multi_item,R.id.alter_dialog_custom_view})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.simple_alter_dialog:
                new AlertDialog.Builder(this)
                        .setTitle("简单演示标题")
                        .setMessage("简单演示内容")
                        .setPositiveButton("好", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(AlertDialogTest.this, "点击了好", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("不好", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //无操作就会退出。
                            }
                        })
                        .setNeutralButton("中间级别", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //无操作
                            }
                        })
                        .show();
                break;
            case R.id.alter_dialog_with_icon:
                new AlertDialog.Builder(this)
                        .setIcon(R.drawable.test)
                        .setTitle("标题")
                        .setMessage("消息")
                        .setPositiveButton("Ok",null)
                        .show();
                break;
            case R.id.alter_dialog_with_items:
                new AlertDialog.Builder(this)
                        .setIcon(R.drawable.test)
                        .setTitle("列表项")
                        .setItems(new String[]{"1", "2", "3"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(AlertDialogTest.this,"点击了第" + which + "个",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setPositiveButton("Ok",null)
                        .show();
                break;
            case R.id.alter_dialog_with_item:
                new AlertDialog.Builder(this)
                    .setIcon(R.drawable.test)
                    .setTitle("单选")
                    .setSingleChoiceItems(new String[]{"1", "2", "3"}, 0,new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(AlertDialogTest.this,"点击了第" + which + "个",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setPositiveButton("Ok",null)
                    .show();
                break;
            case R.id.alter_dialog_with_multi_item:
                new AlertDialog.Builder(this)
                        .setIcon(R.drawable.test)
                        .setTitle("多选")
                        .setMultiChoiceItems(new String[]{"1", "2", "3"}, new boolean[]{true, false, true}, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                            }
                        })
                        .setPositiveButton("Ok",null)
                        .show();
                break;
            case R.id.alter_dialog_custom_view:
                View cView = LayoutInflater.from(AlertDialogTest.this).inflate(R.layout.custom_toast,null);
                new AlertDialog.Builder(this)
                        .setTitle("自定义View")
                        .setView(cView)
                        .setPositiveButton("OK",null)
                        .show();
                break;

        }
    }
}

package com.qxg.study.studyandroid.view.ProgressDialog;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.qxg.study.studyandroid.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProgressDialogTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_dialog_test);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.simple_progress_dialog, R.id.circle_progress_dialog,R.id.long_progress_dialog})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.simple_progress_dialog:
                ProgressDialog dialog = new ProgressDialog(this);
                dialog.setTitle("标题");
                dialog.setMessage("正在加载");
                dialog.show();
                break;
            case R.id.circle_progress_dialog:
                ProgressDialog dialog2 = new ProgressDialog(this);
                dialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog2.setTitle("标题");
                dialog2.setMessage("正在加载");
                dialog2.setButton(DialogInterface.BUTTON_POSITIVE, "好", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog2.setButton(DialogInterface.BUTTON_NEGATIVE, "不好", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog2.show();
                break;
            case R.id.long_progress_dialog:
                final ProgressDialog dialog3 = new ProgressDialog(this);
                dialog3.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                dialog3.setMax(100);
                dialog3.setTitle("标题");
                dialog3.setMessage("正在加载");
                dialog3.setButton(DialogInterface.BUTTON_POSITIVE, "好", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog3.setButton(DialogInterface.BUTTON_NEGATIVE, "不好", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog3.show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int i = 0;
                        while(i<=100){
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            i++;
                            dialog3.incrementProgressBy(1);
                        }
                    }
                }).start();
                break;
        }
    }
}

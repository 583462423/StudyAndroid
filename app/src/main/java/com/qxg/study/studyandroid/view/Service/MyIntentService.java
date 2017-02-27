package com.qxg.study.studyandroid.view.Service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;


public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("当前线程", Thread.currentThread().getId() + "");
        Log.i("-------->","onHandleIntent");
    }

    @Override
    public void onDestroy() {
        Log.i("-------->","onDestroy");
    }
}

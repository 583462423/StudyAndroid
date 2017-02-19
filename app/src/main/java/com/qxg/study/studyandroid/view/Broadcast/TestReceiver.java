package com.qxg.study.studyandroid.view.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class TestReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"静态注册方式接受的广播",Toast.LENGTH_SHORT).show();
    }
}

package com.qxg.study.studyandroid.view.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.qxg.study.studyandroid.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class BroadcastTest extends AppCompatActivity {

    BroadcastReceiver receiver;
    BroadcastReceiver localReceiver;
    LocalBroadcastManager localBroadcastManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_test);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.simple_dbc, R.id.simpe_ndbc, R.id.simpe_local_bc})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.simple_dbc:
                //动态注册
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("com.qxg.MyAction");
                receiver = new MyBroadcastReceiver();
                registerReceiver(receiver,intentFilter);
                sendBroadcast(new Intent("com.qxg.MyAction"));
                break;
            case R.id.simpe_ndbc:
                sendBroadcast(new Intent("com.qxg.MyAction2"));
                break;
            case R.id.simpe_local_bc:

                //获取实例
                localBroadcastManager = LocalBroadcastManager.getInstance(BroadcastTest.this);

                //动态注册
                IntentFilter intentFilter2 = new IntentFilter();
                intentFilter2.addAction("com.qxg.MyAction3");
                localReceiver = new MyBroadcastReceiver();

                //使用localBroadcastManager进行注册
                localBroadcastManager.registerReceiver(localReceiver,intentFilter2);

                //使用localBroadcastManager发送广播
                localBroadcastManager.sendBroadcast(new Intent("com.qxg.MyAction3"));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null){
            unregisterReceiver(receiver);
        }
        if(localReceiver != null){
            localBroadcastManager.unregisterReceiver(localReceiver);
        }
    }

    class MyBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            //响应广播
            Toast.makeText(BroadcastTest.this,"动态注册方式响应的广播",Toast.LENGTH_SHORT).show();
        }
    }
}

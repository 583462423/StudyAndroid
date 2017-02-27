package com.qxg.study.studyandroid.view.Service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.qxg.study.studyandroid.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServiceTest extends AppCompatActivity {

    ServiceConnection connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_test);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.start_simple_service, R.id.stop_simple_service,R.id.bind_service,R.id.unbind_service,
            R.id.foreground_service,R.id.close_foreground_service,R.id.intent_service})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_simple_service:
                    startService(new Intent(this,MyService.class));
                break;
            case R.id.stop_simple_service:
                    stopService(new Intent(this,MyService.class));
                break;

            case R.id.bind_service:
                connection = new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        //绑定成功后,传递的IBinder service就是服务中onBind()中的返回值,所以在这个地方把这个对象取出来
                        BindService.MyBinder binder = (BindService.MyBinder) service;

                        //取得IBinder对象后,就可以使用该对象自定义的方法做一些操作了
                        binder.startSomething();
                        binder.endSomething();
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                        //这里是解绑后的操作
                    }
                };
                bindService(new Intent(this,BindService.class),connection,BIND_AUTO_CREATE);
                break;

            case R.id.unbind_service:
                    unbindService(connection);
                break;

            case R.id.foreground_service:
                    startService(new Intent(this,ForegroundService.class));
                break;
            case R.id.close_foreground_service:
                    stopService(new Intent(this,ForegroundService.class));
                break;
            case R.id.intent_service:
                    Log.i("主线程id",Thread.currentThread().getId() + "");

                    startService(new Intent(this, MyIntentService.class));
                break;
        }
    }


}

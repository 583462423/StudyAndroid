package com.qxg.study.studyandroid.view.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class BindService extends Service {
    public BindService() {
    }

    class MyBinder extends Binder{
        public void startSomething(){
            Toast.makeText(BindService.this, "开始binder中的一些方法", Toast.LENGTH_SHORT).show();
        }

        public void endSomething(){
            Toast.makeText(BindService.this, "结束binder中的方法", Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "服务已销毁", Toast.LENGTH_SHORT).show();
    }
}

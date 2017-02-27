package com.qxg.study.studyandroid.view.Service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;

import com.qxg.study.studyandroid.R;

public class ForegroundService extends Service {
    public ForegroundService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Notification notification = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.tmp)
                .setContentTitle("前台服务")
                .setContentText("我就是前台服务,你来关我哦")
                .setWhen(System.currentTimeMillis())
                .build();

        startForeground(1,notification);

    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}

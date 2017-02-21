package com.qxg.study.studyandroid.view.Notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

import com.qxg.study.studyandroid.R;
import com.qxg.study.studyandroid.view.MainActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationTest extends AppCompatActivity {


    NotificationManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_test);
        ButterKnife.bind(this);

        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @OnClick({R.id.create_notification, R.id.show_big_text,R.id.show_picture,R.id.show_1_priority,
    R.id.show_2_priority,R.id.show_3_priority,R.id.show_4_priority,R.id.show_5_priority,R.id.show_progress,R.id.show_float,R.id.show_custom})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create_notification:
                //创建PendingIntent
                PendingIntent pi = PendingIntent.getActivity(this,0,new Intent(this, MainActivity.class),0);

                //创建Notification
                Notification notification = new NotificationCompat.Builder(this)
                        .setContentTitle("标题")
                        .setContentText("主体内容")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.test)
                        .setContentIntent(pi)
                        .setAutoCancel(true)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.test))
                        .build();

                //显示,第一个参数是id，保证每个通知的id都是不同的。
                manager.notify(1,notification);
                break;
            case R.id.show_big_text:
                //创建Notification
                Notification notification2 = new NotificationCompat.Builder(this)
                        .setContentTitle("标题")
                        .setContentText("主体内容")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫烫"))
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.test)
                        .setAutoCancel(true)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.test))
                        .build();

                //显示,第一个参数是id，保证每个通知的id都是不同的。
                manager.notify(2,notification2);
                break;

            case R.id.show_picture:

                //创建Notification
                Notification notification3 = new NotificationCompat.Builder(this)
                        .setContentTitle("标题")
                        .setContentText("主体内容")
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(),R.drawable.tmp)))
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.test)
                        .setAutoCancel(true)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.test))
                        .build();

                //显示,第一个参数是id，保证每个通知的id都是不同的。
                manager.notify(3,notification3);
                break;

            case R.id.show_1_priority:

                Notification notification4 = new NotificationCompat.Builder(this)
                        .setContentTitle("标题")
                        .setContentText("主体内容")
                        .setPriority(NotificationCompat.PRIORITY_MIN)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.test)
                        .setAutoCancel(true)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.test))
                        .build();
                manager.notify(4,notification4);
                break;

            case R.id.show_2_priority:
                Notification notification5 = new NotificationCompat.Builder(this)
                        .setContentTitle("标题")
                        .setContentText("主体内容")
                        .setPriority(NotificationCompat.PRIORITY_LOW)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.test)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setAutoCancel(true)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.test))
                        .build();
                manager.notify(5,notification5);
                break;

            case R.id.show_3_priority:
                Notification notification6 = new NotificationCompat.Builder(this)
                        .setContentTitle("标题")
                        .setContentText("主体内容")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.test)
                        .setAutoCancel(true)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.test))
                        .build();
                manager.notify(6,notification6);
                break;

            case R.id.show_4_priority:
                Notification notification7 = new NotificationCompat.Builder(this)
                        .setContentTitle("标题")
                        .setContentText("主体内容")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.test)
                        .setAutoCancel(true)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.test))
                        .build();
                manager.notify(7,notification7);
                break;

            case R.id.show_5_priority:
                Notification notification8 = new NotificationCompat.Builder(this)
                        .setContentTitle("标题")
                        .setContentText("主体内容")
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.test)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .build();
                manager.notify(8,notification8);
                break;

            case R.id.show_progress:

                final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                builder.setContentTitle("标题")
                        .setSmallIcon(R.drawable.test)
                        .setProgress(100,0,false);
                manager.notify(9,builder.build());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            for (int i = 1; i <= 100; i++) {
                                Thread.sleep(1000);
                                builder.setProgress(100,i,false);
                                manager.notify(9,builder.build());
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;

            case R.id.show_float:
                PendingIntent pi2 = PendingIntent.getActivity(this,0,new Intent(this, MainActivity.class),0);

                Notification notification10 = new NotificationCompat.Builder(this)
                        .setContentTitle("标题")
                        .setContentText("主体内容")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.test)
                        .setAutoCancel(true)
                        .setFullScreenIntent(pi2,false)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.test))
                        .build();
                manager.notify(10,notification10);
                break;

            case R.id.show_custom:
                PendingIntent pi3 = PendingIntent.getActivity(this,0,new Intent(this, MainActivity.class),0);

                RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.notification_layout);
                remoteViews.setOnClickPendingIntent(R.id.reback,pi3);
                Notification notification11 = new NotificationCompat.Builder(this)
                        .setContent(remoteViews)
                        .setSmallIcon(R.drawable.test)
                        .build();

                manager.notify(11,notification11);
                break;
        }
    }
}

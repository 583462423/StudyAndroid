# 创建Notification
通知可以在服务中创建，也可以在广播接收器中创建，不论在哪创建，套路都是一样的。步骤如下：

1. 创建NotifactionManager。
2. 创建Notification
3. 使用NotificationManager的notify来显示通知

如代码：
```
//取得NOtificationManager
NotificationManager notificationManager =
        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

//创建Notification
Notification notification = new NotificationCompat.Builder(this)
        .setContentTitle("标题")
        .setContentText("主体内容")
        .setWhen(System.currentTimeMillis())
        .setSmallIcon(R.drawable.test)
        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.test))
        .build();

//显示,第一个参数是id，保证每个通知的id都是不同的。
notificationManager.notify(1,notification);

```
虽然创建了，但是点击了没有任何效果，如果想要实现点击后跳转的相应界面，这个时候就需要PendingIntent。


PendingIntent与Intent的区别是，Intent更倾向于立即执行，而PendingIntent则是在某一合适的时机执行，所以可以把PendingIntent理解为延时的Intent。

PendingIntent的获取方法有三个getActivity(),getBroadcast(),getService(),所接受的参数相同，第一个参数是Context,第二个传0，第三个是Intent，第四个确定PendingIntent的行为，通常传0.

所以代码这样写：
```
//创建PendingIntent
                PendingIntent pi = PendingIntent.getActivity(this,0,new Intent(this, MainActivity.class),0);
```

创建完后，在创建notification的时候使用setContentIntent给其设置上去
```
Notification notification = new NotificationCompat.Builder(this)
    ...
    .setContentIntent(pi)
    ...
    .build();
```

# 通知的取消
取消的方式有两种，一种是点击的时候取消，在builder中使用setAutoCancel(true)即可：
```
Notification notification = new NotificationCompat.Builder(this)
    ...
    .setAutoCancel(true)
    ...
    .build();
```

第二种是主动取消，通过NotificationManager取消：`manager.canel(1)`，其传的参数是id，即在之前显示的时候传入的id.

# 通知的时候多媒体表现

通过初始化的时候设置声音，震动，指示灯等：
```
//设置声音
.setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Luna.ogg")));

//设置震动,需要声明VIBRATE权限，以下参数表示，静止0秒，震动1000ms,再静止1000ms，再震动1000ms。
.setVibrate(new long[]{0,1000,1000,1000});

//设置指示灯,第二个参数表示亮的时长，第三个是暗的时长
.setLights(Color.GREEN,1000,1000);

//默认值，即根据手机的设置决定是否响铃，是否震动等。
.setDefaults(NotificationCompat.DEFAULT_ALL)
```

# 高级用法

当通知文字特别多的时候，通知一般会显示省略号，如何让通知显示全部内容呢？

```
.setStyle(new NotificationCompat.BigTextStyle().bigText(someBigText))
```

显示图片
```
.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResource(),R.drawable.test)))
```

设置级别：
```
.setPriority(NotificationCompat.PRIORITY_MAX)
```

进度条样式的通知：
```
//其实就是一直在更新通知而已，注意一定要设置小图标，不然报错，setProgresss中第三个参数是确定性，传入true，通知栏中的进度条就会一直在跳。
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

浮动通知(安卓5.0以后):
```
.setFullScreenIntent(pendingIntent, false);
```
注意以下情况也会出现浮动通知：
* setFullScreenIntent()，如上述示例。
* 通知拥有高优先级且使用了铃声和振动


# 自定义通知

自定义通知需要自定义layout,然后通过RemoteView来接受这个layout，然后将该layout显示，如果给该layout设置自定义按钮监听还是需要配合PendingIntent。
```
PendingIntent pi3 = PendingIntent.getActivity(this,0,new Intent(this, MainActivity.class),0);
//创建RemoteViews
RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.notification_layout);
//设置监听
remoteViews.setOnClickPendingIntent(R.id.reback,pi3);

Notification notification11 = new NotificationCompat.Builder(this)
        .setContent(remoteViews)
        .setSmallIcon(R.drawable.test)
        .build();

manager.notify(11,notification11);
```



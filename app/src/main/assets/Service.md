# 简介
四大组件之一,非常适合执行不需要和用户交互的而且还要长期运行的任务.

服务默认运行在主线程中,一般需要在服务内部手动创建子线程,这样才能保证主线程不被阻塞.


# 简单使用

1. 创建服务类继承Service.
2. AndroidManifest中注册
3. 重写onBind(),onStartCommand(),onCreate()等方法
4. 启动
5. 停止

在服务启动的时候,会调用onCreate()和onStartCommand()方法,如果不销毁服务的情况下,多次启动该服务,那么每次都会调用onStartCommand()方法,但是onCreate()方法只会调用一次.那么对于简单版的代码可以这样写:

创建服务,onBind()方法必须重写
```

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(),"service已启动",Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(getApplicationContext(),"onBind is runnig",Toast.LENGTH_SHORT).show();
        return null;
    }

}

```

注册,AS会自动帮我们注册:
```
     <service
            android:name=".view.Service.MyService"
            android:enabled="true"
            android:exported="true"></service>
```

启动:`startService(context,MyService.class);`

停止有两种方法,一种是MyServcie使用stopSelf()自行停止,一种是外部停止:`stopService`

# Service和Activity进行通信

1. 创建服务,并且创建内部类继承Binder,并添加自己的方法
2. 注册
3. 在Activity中创建ServiceConnection,在Activity和Service绑定的时候会自动调用里面的方法
4. 绑定服务
5. 解绑

所以代码如下

创建服务
```
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

}

```


Activity创建ServiceConnection
```
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
```

绑定:
```
bindService(new Intent(this,BindService.class),connection,BIND_AUTO_CREATE);
```

解绑,注意传的是ServiceConnection对象:

```
unbindService(connection);
```


每个服务只会存在一个实例,所以无论调用了多少次startService(),只需要调用一次stopService()方法.


# 前台服务

使用startForeground()方法在通知栏开启一个通知

如:

```
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
```

在onCreate方法中创建一个Notification,然后使用startForeground将其显示,这个地方的通知显示用的不是NotificationManger.

其实不论在onCreate()方法中,还是在startCommand()方法中,都可以使用startForeground来显示前台通知.

# IntentService

IntentService和普通的Service的区别是:

IntentService不需要调用stopService使其终结,当他的一些方法执行完毕,会自动终结.

第二点是在他的重写方法中 onHandleIntent中,其线程为子线程,所以不用担心线程阻塞问题.

代码如下:

```
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

```

打印日志为:

```
02-27 21:24:39.638 28621-28621/com.qxg.study.studyandroid I/主线程id: 1
02-27 21:24:39.648 28621-28829/com.qxg.study.studyandroid I/当前线程: 315
02-27 21:24:39.648 28621-28829/com.qxg.study.studyandroid I/-------->: onHandleIntent
02-27 21:24:39.649 28621-28621/com.qxg.study.studyandroid I/-------->: onDestroy
```


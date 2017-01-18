# 显示调用方式
代码:
```java
Intent intent = new Intent(FirstActivity.this,SecondActivity.class);
startActivity(intent);
```
显示调用方式，就是指明了要调用哪个，要哪个，你就得给我显示哪个。

# 隐式调用方式
隐式调用方式，通过指明intent-filter，让系统自己去选择要调用的软件，而用户并不需要指明。

比如，如果我们的手机中有两个视频播放器，当我找到一个MP4文件，打开的时候，系统会显示两个播放器待选择，这个过程就是隐式调用

首先是如下代码：
```
<activity android:name = "TestActivity">
	<intent-filter>
		<action android:name="com.qxg.studyandroid.TEST_START"/>
		<category android:name="android.intent.category.DEFAULT"/>
	</intent-filter>
</activity>
```
主要代码是标签intent-filter中的代码，系统在匹配某个intent时，只有intent的action和category和该activity配置中的action和category都匹配的时候，这个activity才会进行响应。不过需要注意，如果给intentn只添加了action，那么在调用startActivity的时候，会自动给该Intent添加category，所以会发现如下代码：

```
Intent intent = new Intent("com.qxg.studyandroid.TEST_START");
startActivity(intent);
```
注意，new Intent()传入一个参数并且是字符串，那么这个字符串就是action.

该代码同样会响应TestActivity。

## action和category

在匹配过程中，只给intent添加了category的时候不能匹配，但是只添加了action是可以匹配的，因为在startActivity会默认给intent添加category。

对于一个Activity来说，可指明多个activity，多个category，但是对于intent来说，一个intent对象中，只有一个action,但可以存储多个category，而在匹配中，不管intent中有几个category，在匹配过程中，其中的category必须全部匹配，否则相应的activity不会响应，这个过程不用理会AndroidMainfest.xml中Activity中有几个category.

# 其他隐式Intent大全

web浏览器
```
Uri uri= Uri.parse("http://www.baidu.com:8080/image/a.jpg");
Intent intent = new Intent(Intent.ACTION_VIEW, uri);
startActivity(intent);
```

地图(要在 Android 手机上才能测试)
```
Uri uri = Uri.parse("geo:38.899533,-77.036476");
Intent intent = new Intent(Intent.ACTION_VIEW, uri);
startActivity(intent);
```

拨打电话-调用拨号程序
```
Uri uri = Uri.parse("tel:15980665805");
Intent intent = new Intent(Intent.ACTION_DIAL, uri);
startActivity(intent);
```

拨打电话-直接拨打电话
要使用这个必须在配置文件中加入<uses-permission android:name="android.permission.CALL_PHONE"/>
```
Uri uri = Uri.parse("tel:15980665805");
Intent intent = new Intent(Intent.ACTION_CALL, uri);
startActivity(intent);
```

调用发送短信程序(方法一)
```
Uri uri = Uri.parse("smsto:15980665805");
Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
intent.putExtra("sms_body", "The SMS text");
startActivity(intent);
```

调用发送短信程序(方法二)
```
Intent intent = new Intent(Intent.ACTION_VIEW);
intent.putExtra("sms_body", "The SMS text");
intent.setType("vnd.android-dir/mms-sms");
startActivity(intent);
```

发送彩信
```
Uri uri = Uri.parse("content://media/external/images/media/23");
Intent intent = new Intent(Intent.ACTION_SEND);
intent.putExtra("sms_body", "some text");
intent.putExtra(Intent.EXTRA_STREAM, uri);
intent.setType("image/png");
startActivity(intent);
```

发送Email(方法一)(要在 Android 手机上才能测试)
```
Uri uri = Uri.parse("mailto:zhangsan@gmail.com");
Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
startActivity(intent);
```

发送Email(方法二)(要在 Android 手机上才能测试)
```
Intent intent = new Intent(Intent.ACTION_SENDTO); 
intent.setData(Uri.parse("mailto:zhangsan@gmail.com")); 
intent.putExtra(Intent.EXTRA_SUBJECT, "这是标题"); 
intent.putExtra(Intent.EXTRA_TEXT, "这是内容"); 
startActivity(intent); 
```

发送Email(方法三)(要在 Android 手机上才能测试)
```
Intent intent = new Intent(Intent.ACTION_SEND);
intent.putExtra(Intent.EXTRA_EMAIL, "me@abc.com");
intent.putExtra(Intent.EXTRA_SUBJECT, "这是标题"); 
intent.putExtra(Intent.EXTRA_TEXT, "这是内容");
intent.setType("text/plain");
//选择一个邮件客户端
startActivity(Intent.createChooser(intent, "Choose Email Client")); 
```

发送Email(方法四)(要在 Android 手机上才能测试)
```
Intent intent = new Intent(Intent.ACTION_SEND);
//收件人
String[] tos = {"to1@abc.com", "to2@abc.com"};
//抄送人
String[] ccs = {"cc1@abc.com", "cc2@abc.com"};
//密送人
String[] bcc = {"bcc1@abc.com", "bcc2@abc.com"};
intent.putExtra(Intent.EXTRA_EMAIL, tos);
intent.putExtra(Intent.EXTRA_CC, ccs);
intent.putExtra(Intent.EXTRA_BCC, bcc);
intent.putExtra(Intent.EXTRA_SUBJECT, "这是标题"); 
intent.putExtra(Intent.EXTRA_TEXT, "这是内容");
intent.setType("message/rfc822"); 
startActivity(Intent.createChooser(intent, "Choose Email Client"));
```
发送Email且发送附件(要在 Android 手机上才能测试)
```
Intent intent = new Intent(Intent.ACTION_SEND); 
intent.putExtra(Intent.EXTRA_SUBJECT, "The email subject text"); 
intent.putExtra(Intent.EXTRA_STREAM, "file:///sdcard/mp3/醉红颜.mp3"); 
intent.setType("audio/mp3"); 
startActivity(Intent.createChooser(intent, "Choose Email Client"));
```
播放媒体文件(android 对中文名的文件支持不好)
```
Intent intent = new Intent(Intent.ACTION_VIEW);
Uri uri = Uri.parse("file:///sdcard/a.mp3"); 
intent.setDataAndType(uri, "audio/mp3"); 
startActivity(intent);


Uri uri = Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "1"); 
Intent intent = new Intent(Intent.ACTION_VIEW, uri);
startActivity(intent); 
```

音乐选择器
它使用了Intent.ACTION_GET_CONTENT 和 MIME 类型来查找支持 audio/* 的所有 Data Picker，允许用户选择其中之一
```
Intent intent = new Intent(Intent.ACTION_GET_CONTENT); 
intent.setType("audio/*");
//Intent.createChooser：应用选择器，这个方法创建一个 ACTION_CHOOSER Intent
startActivity(Intent.createChooser(intent, "选择音乐"));

Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT); 
intent1.setType("audio/*");

Intent intent2 = new Intent(Intent.ACTION_CHOOSER);
intent2.putExtra(Intent.EXTRA_INTENT, intent1);
intent2.putExtra(Intent.EXTRA_TITLE, "aaaa");
startActivity(intent2);
```

设置壁纸
```
Intent intent = new Intent(Intent.ACTION_SET_WALLPAPER); 
startActivity(Intent.createChooser(intent, "设置壁纸")); 
```

卸载APK
fromParts方法
参数1：URI 的 scheme
参数2：包路径
参数3：
```
Uri uri = Uri.fromParts("package", "com.great.activity_intent", null);
Intent intent = new Intent(Intent.ACTION_DELETE, uri);
startActivity(intent);
```

安装APK
```
Uri uri = Uri.fromParts("package", "com.great.activity_intent", null);  
Intent intent = new Intent(Intent.ACTION_PACKAGE_ADDED, uri);
startActivity(intent);
```
调用搜索

```
Intent intent = new Intent(); 
intent.setAction(Intent.ACTION_WEB_SEARCH); 
intent.putExtra(SearchManager.QUERY, "android"); 
startActivity(intent);
```

注意，其中new Intent(Intent.VIEW,uri);这样的写法uri其实就是data数据，以上写法也可以换做：
```
Intent intent = new Intent(Intent.VIEW);
intent.setData(uri);
```

## data

setData()这个方法并不复杂，它接受一个Uri对象，主要用于指定当前Intent正在操作的数据，而这些数据通常都是以字符串的形式传入到Uri.parse()方法中解析产生的。对应的，也可以在<intent-filter>中配置一个<data>标签，用于更精确地制定当前活动能够响应什么类型的数据。<data>标签主要可以配置以下内容
× android:scheme:用于制定数据协议部分
× android:host：用于制定数据的主机部分
× android:port:数据端口
× android:path:路径部分
× android:mimeType:指定可以处理的数据类型。

特别注意，如果intent-filter中有data标签信息，只有在data标签中的内容和intent中携带的data完全一致时，当前活动才能够相应该Intent.换句话收，如果intent-filter中有data，那么intent中必须携带data，不然就不能匹配。

但是在intent-filter中不需要每个属性都有值，比如我们可以只给设置intent-filter设置scheme为http，那么这个activity就可以响应http协议的数据。

Intent中的data必须和过滤规则中的某一个data完全匹配；

过滤规则中可以有多个data存在，但是Intent中的data只需匹配其中的任意一个data即可；

过滤规则中可以没有指定URI，但是系统会赋予其默认值：content和file，这一点在Intent中需要注意；

为Intent设定data的时候必须要调用setDataAndType（）方法，而不能先setData再setType，因为这两个方法是互斥的，都会清除对方的值，这个有兴趣可以参见源码；

在匹配规则中，data的scheme，host，port，path等属性可以写在同一个< />中，也可以分开单独写，其功效是一样的；


# startActivityForResult

用法：

1. 在第一个Activity中，跳转Activity的代码写成如下：startActivityForResult(intent,REQUEST_CODE);
其中，第二个参数是请求码，这个后边会讲到。
2. 在第二个Activity，即将要跳转的activity中，返回数据时，使用如下代码，setResult(RESULT_OK_CODE,intent); 其中第一个参数是成功返回码，第二个参数是intent，这个intent是一个携带数据的intent，携带数据可以使用intent.putExtraXXX等方法为其设置数据。
3. 这个时候在第一个Activity中就可以获取数据，不过获取数据需要使用请求吗和成功返回码。重写方法：onActivityResult(int requestCode,int ResultCode,Intent data)方法。

为什么要使用请求码和返回码，因为在请求的时候，可能有多出调用了该方法，那么如何确定是哪处调用的呢？这个时候请求码就起到的作用，通过switch来进行判断，就可以对不同地方的请求做相应的处理。

一般onActivityResult方法中需要switch来进行处理不同的数据请求。
如代码：
```
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       switch(resultCode){
		case 1:
               		if(resultCode == 2){
                    		//其他操作
                	}
		break;
       }
    }
```

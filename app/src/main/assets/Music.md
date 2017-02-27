# 安卓播放音乐

一般使用MediaPlayer类来实现,下面是该类中较常用的方法:

* setrDataSource() 设置要播放音频的位置
* prepare() 准备播放
* start() 开始或继续播放
* pause() 暂停播放
* reset() 将MediaPlayer对象重置到刚刚创建的状态
* seekTo() 从指定的位置开始播放音频
* stop() 停止播放音频.调用这个方法后的MediaPlayer对象无法再播放音频
* release() 释放掉与MediaPlayer对象相关的资源
* isPlaying() 判断当前MediaPlayer是否正在播放音频
* getDuration() 获取载入的音频文件的时长

根据以上方法,可以知道一般播放音频的方法调用顺序是:

1. 创建MediaPlayer对象
2. setDataSource设置音频位置
3. prepare 准备
4. start开始播放

注意,setDataSource是重载方法,所以也可以向里面传入uri.

所以,我们可以通过以下代码来选择音乐文件
```
    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
    intent.setType("audio/*");
    startActivityForResult(intent,CHOOSE_MUSIC);
```

之后在onActivityResult方法中获取到音乐文件的uri,获取成功后,通过setDataSource将该uri设置进去,然后运行prepare和start方法,就可以进行播放,在播放的时候,也可以配合seekBar完成滚动的操作.这个实现的方法有很多,自行研究.


注意,谨慎使用stop()方法,使用stop()后,该MediaPlayer对象就无法再播放其他音乐文件,一旦你选择播放,软件就会崩溃.



# 播放视频

播放视频和播放音乐基本一样,只是播放视频需要的是VideoView,其方法有:

* setVideoPath() 设置要播放的视频文件的位置
* start() 开始或继续播放视频
* pause() 暂停播放视频
* resume() 将视频重头开始播放
* seekTo() 从指定的位置开始播放视频
* isPlaying() 判断当前是否正在播放视频
* getDuration() 获取载入的视频文件的时长

选择视频:
```
Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
startActivityForResult(i, REQUEST_CODE); 
```

就不再做演示了.

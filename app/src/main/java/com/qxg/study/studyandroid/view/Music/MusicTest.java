package com.qxg.study.studyandroid.view.Music;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.qxg.study.studyandroid.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MusicTest extends AppCompatActivity {

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private static final int CHOOSE_MUSIC = 1;
    private Handler handler;

    @BindView(R.id.music_name)
    TextView musicName;
    @BindView(R.id.music_seekbar)
    SeekBar seekBar;

    Uri musicUri = null;
    int musicTime = 0; //转换为s为单位

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_test);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                //执行滚动
                //首先获取当前播放时长
                int current = mediaPlayer.getCurrentPosition() / 1000;
                seekBar.setProgress(current);
                handler.sendEmptyMessage(1);
            }
        };
        ButterKnife.bind(this);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //
                //Log.i("---->","onProgressChanged");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //如果用手拖动就会到这开始播放
                int current = seekBar.getProgress();
                mediaPlayer.seekTo(current * 1000);

                if(!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                }
            }
        });
//        //测试代码
//        File file = new File(Environment.getExternalStorageDirectory(),"kgmusic.ver");
//        musicName.setText(file.getPath());
    }

    @OnClick({R.id.choose_music, R.id.start, R.id.pause, R.id.stop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.choose_music:
                //使用Intent来启动
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("audio/*");
                startActivityForResult(intent,CHOOSE_MUSIC);

                break;
            case R.id.start:
                if(musicUri != null){
                    mediaPlayer.seekTo(seekBar.getProgress()*1000);
                    mediaPlayer.start();
                    //发送播放的消息,让handler去滚动seekbar
                    handler.sendEmptyMessage(1);
                }

                seekBar.setVisibility(View.VISIBLE);
                break;
            case R.id.pause:
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    handler.removeMessages(1);
                }
                break;
            case R.id.stop:
                //注意,一般搞音乐播放器,停止播放的含义并不是调用下面的这个方法,而是将播放的位置至于0
                if (mediaPlayer.isPlaying()){
                    handler.removeMessages(1);
                    mediaPlayer.stop();
                    musicUri = null;
                    //将seekbar消失
                    seekBar.setVisibility(View.GONE);
                }else {
                    seekBar.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case CHOOSE_MUSIC:
                if(resultCode == RESULT_OK){
                    musicUri = data.getData();
                    //通过uri获取文件名
                    musicName.setText(getRealPath(musicUri));

                    if(musicUri == null) throw new RuntimeException("获取文件出错!");

                    try {
                        //reset是防止之前有音乐正在播放
                        mediaPlayer.reset();
                        //musiUri不为空,说明有值,这个时候传值进去就好
                        mediaPlayer.setDataSource(this,musicUri);
                        //开始做准备工作
                        mediaPlayer.prepare();
                        //准备完成后,要计算音乐时间长,并且显示seekBar
                        musicTime = mediaPlayer.getDuration() / 1000;
                        seekBar.setMax(musicTime);
                        seekBar.setVisibility(View.VISIBLE);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                break;
        }
    }

    //通过Uri获取文件名
    private String getRealPath(Uri uri){
        String path = null;
        String scheme = uri.getScheme();
        //如果Uri返回的是file:// 类型的,直接获取路径
        if(ContentResolver.SCHEME_FILE.equals(scheme) || scheme == null){
            path = uri.getPath();
        }else if(ContentResolver.SCHEME_CONTENT.equals(scheme)){
            //如果Uri返回的是content://类型,则需要通过ContentResolver到数据库中提取文件名
            Cursor cursor = getContentResolver().query(uri,new String[]{MediaStore.Images.ImageColumns.DISPLAY_NAME},null,null,null);
            if(cursor != null){
                if(cursor.moveToFirst()){
                    path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME));
                }
            }
        }
        return path;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
    }
}

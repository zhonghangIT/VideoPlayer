package com.lz.longyang.videoplayer;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mp;
    private EditText et_path;
    private SurfaceView surfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_path = (EditText) findViewById(R.id.et_path);

        //找到视频播放的控件
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        //进行初始化的设置
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);//把视频数据直接显示

        //保证手机的屏幕一直高亮(任何的控件都可以)
        surfaceView.setKeepScreenOn(true);

        mp = new MediaPlayer();//媒体播放器
    }

    public void play(View v){
        try {
            String path = et_path.getText().toString().trim();
            mp.reset();//重置
            mp.setDataSource(path);//设置要播放的资源
            prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步准备方法
     */
    private void prepareAsync() {
        //把媒体播放器和SurfaceView关联起来
        mp.setDisplay(surfaceView.getHolder());//设置显示
        mp.prepareAsync();//异步准备
        //从开始的位置开始播放
        mp.seekTo(0);
        //设置准备完成监听
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            //准备完成
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();//播放
            }
        });
    }

    //暂停   继续
    public void pausemoveon(View v){
        Button bt = (Button) v;
        if(mp.isPlaying()){//判断媒体播放器是否在播放音乐
            //暂停   文字变成继续
            mp.pause();
            bt.setText("继续");
        }else{
            //继续播放
            mp.start();
            bt.setText("暂停");
        }
    }

    public void stop(View v){
        //停止
        mp.stop();
    }

    public void replay(View v){
        //停止
        mp.stop();
        //重播
        prepareAsync();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp.release();
        mp = null;
    }
}

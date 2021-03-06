package com.example.bbs.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.bbs.MainActivity;
import com.example.bbs.R;
import com.example.bbs.mylayout.FullVideoView;
import com.example.bbs.utils.CountDownTime;

import java.io.File;

public class OpenActivity extends AppCompatActivity {

    private FullVideoView mOnOpen;
    private TextView mTVTime;
    private CountDownTime countDownTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);

        mOnOpen = findViewById(R.id.vv);
        mTVTime = findViewById(R.id.tv_time);
        onOpen();
        mTVTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OpenActivity.this, MainActivity.class));
            }
        });

    }

    /**
     * 开机启动界面，沉浸式开机
     */
    private void onOpen() {
        mOnOpen.setVideoURI(Uri.parse("android.resource://" + getPackageName() + File.separator + R.raw.open));
        mOnOpen.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
        mOnOpen.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
        countDownTime = new CountDownTime(5, new CountDownTime.ICountDownTimeHandle() {
            @Override
            public void onTime(int time) {
                mTVTime.setText(time + "秒");
            }

            @Override
            public void onFinish() {
                mTVTime.setText("跳过");
            }
        });
        countDownTime.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTime.destroy();
    }
}
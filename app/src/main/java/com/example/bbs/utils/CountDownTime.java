package com.example.bbs.utils;


import android.os.Handler;

public class CountDownTime implements Runnable {
    private int time;
    private int currentTime;
    private final Handler handler;
    private final ICountDownTimeHandle iCountDownTimeHandle;
    private boolean isRun;

    public CountDownTime(int time, ICountDownTimeHandle iCountDownTimeHandle) {
        handler = new Handler();
        this.time = time;
        this.currentTime = time;
        this.iCountDownTimeHandle = iCountDownTimeHandle;
    }

    @Override
    public void run() {
        if (isRun) {
            if (iCountDownTimeHandle != null) {
                iCountDownTimeHandle.onTime(currentTime);
            }
            if (currentTime == 0) {
                destroy();
                if (iCountDownTimeHandle != null) {
                    iCountDownTimeHandle.onFinish();
                }
            } else {
                currentTime = time--;
                handler.postDelayed(this, 1000);
            }
        }
    }

    public void start() {
        isRun = true;
        handler.post(this);
    }

    public void destroy() {
        isRun = false;
        handler.removeCallbacks(this);
    }

    public interface ICountDownTimeHandle {
        void onTime(int time);

        void onFinish();
    }
}

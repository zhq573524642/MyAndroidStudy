package com.zhq.exclusivememory.ui.activity.four_module.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.ui.activity.four_module.content_provide.ContentProviderActivity;

public class MyService extends Service {
    public static final int MAX_PROGRESS = 100;
    private   int progress = 0;
    public MyService() {
    }

    private MyBinder mMyBinder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "第一次开启服务", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,ContentProviderActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("前台服务")
                .setContentText("hahahahhahahahaha")
                .setWhen(SystemClock.elapsedRealtime())
                .setContentIntent(pi)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .build();
        startForeground(1,notification);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "开启服务", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }
    public String start() {
        return "开始计数";
    }

    public void startProgress(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progress<MAX_PROGRESS) {
                    progress=progress+5;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        }).start();
    }

    public int getProgress() {
        return progress;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMyBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        progress=0;
        Toast.makeText(this, "关闭服务", Toast.LENGTH_SHORT).show();
    }

    class MyBinder extends Binder {

        public MyService getService(){
            return MyService.this;
        }


    }
}

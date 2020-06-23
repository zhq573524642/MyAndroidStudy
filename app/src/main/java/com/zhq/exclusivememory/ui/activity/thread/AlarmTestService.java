package com.zhq.exclusivememory.ui.activity.thread;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.zhq.exclusivememory.utils.LogUtil;

public class AlarmTestService extends Service {
    private int index;

    public AlarmTestService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();

        Toast.makeText(this, "开启一个服务", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "开始服务", Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                index++;
                LogUtil.i("AlarmTestService", "hehehhehehe");
            }
        }).start();
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int time = 10 * 1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + time;
        if (index<3) {
            Intent i = new Intent(this, AlarmTestService.class);
            PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        }else {
            Toast.makeText(this, "已经到达"+index+"次，关闭服务", Toast.LENGTH_SHORT).show();
            stopSelf();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
        Toast.makeText(this, "关闭服务", Toast.LENGTH_SHORT).show();
    }
}

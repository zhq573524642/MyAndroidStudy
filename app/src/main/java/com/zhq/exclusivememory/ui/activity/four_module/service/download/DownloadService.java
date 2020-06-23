package com.zhq.exclusivememory.ui.activity.four_module.service.download;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.ui.activity.four_module.content_provide.ContentProviderActivity;

import java.io.File;

public class DownloadService extends Service {
    private String downloadUrl;
    private DownloadTask mDownloadTask;
    private DownloadListener mDownloadListener = new DownloadListener() {
        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(1, getNotification("正在下载...", progress));
        }

        @Override
        public void onSuccess() {
            mDownloadTask=null;
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("下载成功", -1));
            Toast.makeText(DownloadService.this, "下载成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed() {
            mDownloadTask=null;
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("下载失败", -1));
            Toast.makeText(DownloadService.this, "下载失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPaused() {
            mDownloadTask=null;
            Toast.makeText(DownloadService.this, "暂停下载", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCanceled() {
            mDownloadTask=null;
            stopForeground(true);
            Toast.makeText(DownloadService.this, "下载取消", Toast.LENGTH_SHORT).show();
        }
    };

    public DownloadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mDownloadBinder;
    }

    private DownloadBinder mDownloadBinder = new DownloadBinder();

   public class DownloadBinder extends Binder {
        public void startDownload(String url) {
            if (mDownloadTask == null) {
                downloadUrl = url;
                mDownloadTask = new DownloadTask(mDownloadListener);
                mDownloadTask.execute(downloadUrl);
                startForeground(1, getNotification("正在下载...", 0));
                Toast.makeText(DownloadService.this, "正在下载", Toast.LENGTH_SHORT).show();
            }
        }
       public void pauseDownload() {
           if (mDownloadTask != null) {
               mDownloadTask.pauseDownload();
           }
       }
       public void cancelDownload() {
           if (mDownloadTask != null) {
               mDownloadTask.cancelDownload();
           } else {
               if (downloadUrl != null) {
                   //取消下载是需要将文件删除，并将通知关闭
                   String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                   String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                   File file = new File(directory + fileName);
                   if (file.exists()) {
                       file.delete();
                   }
                   getNotificationManager().cancel(1);
                   stopForeground(true);
                   Toast.makeText(DownloadService.this, "下载取消", Toast.LENGTH_SHORT).show();

               }
           }
       }
    }





    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String title, int progress) {
        Intent intent = new Intent(this, ContentProviderActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentIntent(pi);
        builder.setContentTitle(title);
        if (progress > 0) {
            //当progress大于或等于0时才显示下载进度
            builder.setContentText(progress + "%");
            builder.setProgress(100, progress, false);
        }
        return builder.build();
    }
}

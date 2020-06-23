package com.zhq.exclusivememory.ui.activity.view.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.api.API;
import com.zhq.exclusivememory.base.BaseSimpleActivity;
import com.zhq.exclusivememory.constant.Constant;
import com.zhq.exclusivememory.ui.activity.four_module.content_provide.ContentProviderActivity;
import com.zhq.exclusivememory.ui.activity.web_view.WebViewActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/22.
 */

public class NotificationActivity extends BaseSimpleActivity {
    @BindView(R.id.ll_left_back)
    LinearLayout mLlLeftBack;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.tv_right_text)
    TextView mTvRightText;
    @BindView(R.id.btn_normal_notification)
    Button mBtnNormalNotification;
    @BindView(R.id.btn_sound_notification)
    Button mBtnSoundNotification;
    private NotificationManager mManager;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mTvCenterTitle.setText("通知");
        mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_notification;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_left_back, R.id.btn_normal_notification, R.id.btn_sound_notification, R.id.btn_style_notification, R.id.btn_see_note})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_left_back:
                finish();
                break;
            case R.id.btn_normal_notification:
                normalNotification(mManager);
                break;
            case R.id.btn_sound_notification:
                soundNotification(mManager);
                break;
            case R.id.btn_style_notification:
                styleNotification(mManager);
                break;
            case R.id.btn_see_note:
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(Constant.WEB_PAGE_URL, API.NOTIFICATION);
                intent.putExtra(Constant.TITLE_NAME, "通知");
                startActivity(intent);
                break;
        }
    }

    private void styleNotification(NotificationManager manager) {
        Intent intent = new Intent(NotificationActivity.this, ContentProviderActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("通知的标题")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("你发誓要从石头里要口粮锐利的刀锋，在坚硬之处开沟播种你用月光灌溉庄稼月光养育的粮食含辛茹苦从此，你迷上了字你在龟甲上挖掘字你用刀的风暴打磨字你寻找字与字之间的格律之美"))
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.hhh)))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setWhen(SystemClock.elapsedRealtime())
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        manager.notify(1, notification);
    }

    private void soundNotification(NotificationManager manager) {
        Intent intent = new Intent(NotificationActivity.this, ContentProviderActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("带声音振动LED通知的标题")
                .setContentText("通知的内容内容内容哈哈哈哈哈哈哈")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setWhen(SystemClock.elapsedRealtime())
                .setContentIntent(pendingIntent)
                .setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Drops.ogg")))
                .setVibrate(new long[]{0, 1000, 1000, 1000})
                .setLights(Color.RED, 1000, 1000)
                .setAutoCancel(true)
                .build();
        manager.notify(1, notification);
    }

    /**
     * 普通的通知
     *
     * @param manager
     */
    private void normalNotification(NotificationManager manager) {
        Intent intent = new Intent(NotificationActivity.this, ContentProviderActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("通知的标题")
                .setContentText("通知的内容内容内容")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setWhen(SystemClock.elapsedRealtime())
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        manager.notify(1, notification);
    }
}

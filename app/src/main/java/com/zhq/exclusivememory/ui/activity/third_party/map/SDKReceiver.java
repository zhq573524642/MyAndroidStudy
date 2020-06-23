package com.zhq.exclusivememory.ui.activity.third_party.map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;

public class SDKReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
            //key验证失败，做相应处理
            Toast.makeText(context, "key验证失败", Toast.LENGTH_SHORT).show();
        } else if (action.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK)) {
            //key验证成功，做相应处理
            Toast.makeText(context, "key验证成功", Toast.LENGTH_SHORT).show();
        }
    }
}

package com.zhq.exclusivememory.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.widget.Toast;

import com.zhq.exclusivememory.utils.LogUtil;

/**
 * Created by Huiqiang Zhang
 * on 2019/3/10.
 */

public class ProximityAlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.i("哈哈哈哈","====z走了吗");
        boolean isEnter = intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, false);
        if(isEnter){
            Toast.makeText(context, "已经进入范围区域", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "已经离开范围区域", Toast.LENGTH_SHORT).show();
        }
    }
}

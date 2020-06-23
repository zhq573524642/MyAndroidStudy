package com.zhq.exclusivememory.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.inputmethod.InputMethodManager;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Huiqiang Zhang
 * on 2018/10/20.
 */

public class MyUtils {

    private static String sPhone = "";

    /**
     * 判断输入的手机号是否合法
     *
     * @param number
     * @return
     */
    public static boolean isMobilePhone(String number) {
        String num = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            return number.matches(num.trim());
        }
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }



    /**
     * 获取两个时间戳之间的差：单位 天
     *
     * @param currentTime
     * @param startTime
     * @return
     */
    public static String getTimeDiff(long currentTime, long startTime) {
        long d1 = new Date(currentTime).getTime();
        long d2 = new Date(startTime).getTime();
        long diff = d1 - d2;
        long day = diff / (24 * 60 * 60 * 1000);
        if (day == 0) {
            return 1 + "";
        } else {
            return day + "";
        }

    }

    public static long getTimeDiffMin(long currentTime, long startTime) {
        long d1 = new Date(currentTime).getTime();
        long d2 = new Date(startTime).getTime();
        long diff = d1 - d2;
        long min = diff / (60 * 1000);
        return min;
    }

    /**
     * 获取一天中的时间段的问候
     *
     * @return
     */
    public static String getWelcomeStatus() {
        Calendar calendar = Calendar.getInstance();
        int i = calendar.get(Calendar.HOUR_OF_DAY);
        if (i > 0 && i <= 7) {
            return "早上好~";
        }

        if (i >= 8 && i < 11) {
            return "上午好~";
        }
        if (i >= 11 && i < 13) {
            return "中午好~";
        }
        if (i >= 13 && i < 18) {
            return "下午好~";
        }
        if (i >= 18 && i <= 22) {
            return "晚上好~";
        }
        return "";

    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getNowTime() {
        String timeString = null;
        Time time = new Time();
        time.setToNow();
        String year = thanTen(time.year);
        String month = thanTen(time.month + 1);
        String monthDay = thanTen(time.monthDay);
        String hour = thanTen(time.hour);
        String minute = thanTen(time.minute);

        timeString = year + "-" + month + "-" + monthDay + " " + hour + ":"
                + minute;
        // System.out.println("-------timeString----------" + timeString);
        return timeString;
    }

    /**
     * 十一下加零
     *
     * @param str
     * @return
     */
    private static String thanTen(int str) {

        String string = null;

        if (str < 10) {
            string = "0" + str;
        } else {

            string = "" + str;

        }
        return string;
    }


    /**
     * 隐藏键盘
     *
     * @param activity
     */
    public static void hintKb(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && activity.getCurrentFocus() != null) {
            if (activity.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 点击拨打电话
     */
    public static void requestForCallUp(Activity activity, String tel) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, 2);
        } else {
            sPhone = tel;
            call(activity, tel);//打电话的逻辑
        }
    }

    private static void call(Activity activity, String tel) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + tel));
            activity.startActivity(intent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public static void onRequestCallUpPermissionResult(Activity activity, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (!TextUtils.isEmpty(sPhone)) {
                    call(activity, sPhone);
                }
            } else {
//                ToastUtils.showMessage(activity, "您未授权此权限");
            }
        }
    }
}

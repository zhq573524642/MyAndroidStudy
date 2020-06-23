package com.zhq.exclusivememory.utils;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;

import com.zhq.exclusivememory.base.APP;


/**
 * Created by Huiqiang Zhang
 * on 2019/3/5.
 */

public class DensityUtil {

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取屏幕的宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        Resources res = context.getResources();
        return res.getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        Resources res = context.getResources();
        return res.getDisplayMetrics().heightPixels;
    }

    /**
     * 设置margin
     * @param v
     * @param l
     * @param t
     * @param r
     * @param b
     */
    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }



    /**
     * 描述：根据分辨率获得字体大小.
     *
     * @param screenWidth the screen width
     * @param screenHeight the screen height
     * @param textSize the text size
     * @return the int
     */
    public static int resizeTextSize(int screenWidth,int screenHeight,int textSize){
        float ratio =  1;
        try {
            float ratioWidth = (float)screenWidth / 480;
            float ratioHeight = (float)screenHeight / 800;
            ratio = Math.min(ratioWidth, ratioHeight);
        } catch (Exception e) {
        }
        return Math.round(textSize * ratio);
    }

    /**
     *
     * 描述：dip转换为px
     * @param context
     * @param dipValue
     * @return
     * @throws
     */
    public static int dip2px2(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return Math.round(dipValue * scale);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 单位转化 dp转px
     * @param dpValue
     * @return
     */
    public static int dp2px(final float dpValue) {
        final float scale = APP.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     *
     * 描述：px转换为dip
     * @param context
     * @param pxValue
     * @return
     * @throws
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return Math.round(pxValue / scale);
    }

    /**
     *
     * 描述：px转换为sp
     * @param context
     * @param pxValue
     * @return
     * @throws
     */
    public static int px2sp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return Math.round(pxValue / scale);
    }

    /**
     *
     * 描述：sp转换为px
     * @param context
     * @param spValue
     * @return
     * @throws
     */
//    public static int sp2px(Context context, float spValue) {
//        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
//        return Math.round(spValue * scale);
//    }
}

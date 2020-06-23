package com.zhq.exclusivememory.ui.activity.view;

import android.nfc.Tag;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseSimpleActivity;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/26.
 */

public class SpringMenuActivity extends BaseSimpleActivity {

//    private SpringMenu mSpringMenu;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
//        mSpringMenu = new SpringMenu(getApplicationContext(), R.layout.layout_spring_menu);
//        mSpringMenu.attachToActivity(SpringMenuActivity.this);
//        mSpringMenu.setMenuSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(20, 3));
//        mSpringMenu.setChildSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(20, 5));
//        mSpringMenu.setFadeEnable(true);
//        mSpringMenu.setDirection(SpringMenu.DIRECTION_RIGHT);
//        mSpringMenu.setMenuListener(new MenuListener() {
//            @Override
//            public void onMenuOpen() {
//                Toast.makeText(SpringMenuActivity.this, "菜单打开", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onMenuClose() {
//                Toast.makeText(SpringMenuActivity.this, "菜单关闭", Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onProgressUpdate(float value, boolean bouncing) {
//                Log.i("SpringMenuActivity","====菜单的状态 "+value+"== "+bouncing);
//            }
//        });
    }
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        return mSpringMenu.dispatchTouchEvent(ev);
//    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_spring_menu;
    }
}

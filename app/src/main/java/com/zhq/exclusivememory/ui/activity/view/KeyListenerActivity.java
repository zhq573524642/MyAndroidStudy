package com.zhq.exclusivememory.ui.activity.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseSimpleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Huiqiang Zhang
 * on 2019/2/18.
 */

public class KeyListenerActivity extends AppCompatActivity {

    private LinearLayout mLlLayout;
    private int speed=12;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉窗口标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_key_listener);

        mLlLayout = findViewById(R.id.ll_layout);
        initView(mLlLayout);
    }

    private void initView(LinearLayout llLayout) {
        final PlaneView planeView = new PlaneView(this);
        llLayout.addView(planeView);
        planeView.setBackgroundColor(Color.GREEN);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeight = display.getHeight();
        //设置飞机的初始位置
        planeView.currentX = screenWidth / 2;
        planeView.currentY = screenHeight -500;
        planeView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (event.getKeyCode()){
                    case KeyEvent.KEYCODE_DPAD_LEFT://左
                    case KeyEvent.KEYCODE_A://左
                        planeView.currentX-=speed;
                        break;
                    case KeyEvent.KEYCODE_DPAD_RIGHT://右
                    case KeyEvent.KEYCODE_D://右
                        planeView.currentX+=speed;
                        break;
                    case KeyEvent.KEYCODE_DPAD_UP://上
                    case KeyEvent.KEYCODE_W://上
                        planeView.currentY-=speed;
                        break;
                    case KeyEvent.KEYCODE_DPAD_DOWN://下
                    case KeyEvent.KEYCODE_S://下
                        planeView.currentY+=speed;
                        break;
                }
                //通知planeView重新绘制
                planeView.invalidate();
                return true;
            }
        });
    }
}

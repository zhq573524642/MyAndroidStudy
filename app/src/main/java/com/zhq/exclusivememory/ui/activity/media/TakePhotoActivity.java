package com.zhq.exclusivememory.ui.activity.media;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseSimpleActivity;
import com.zhq.exclusivememory.utils.DensityUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Huiqiang Zhang
 * on 2019/6/16.
 */

public class TakePhotoActivity extends BaseSimpleActivity {
    @BindView(R.id.surface_view)
    SurfaceView mSurfaceView;
    private int mScreenWidth;
    private int mScreenHeight;
    private SurfaceHolder mSurfaceHolder;
    private boolean isPreview = false;
    private android.hardware.Camera mCamera;

    @Override
    protected void initData() {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        //获取屏幕的宽高
        mScreenWidth = DensityUtil.getScreenWidth(this);
        mScreenHeight = DensityUtil.getScreenHeight(this);
        mSurfaceHolder = mSurfaceView.getHolder();
        //surfaceView回调监听器
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                initCamera();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                //如果Camera不为null，释放摄像头
                if (mCamera != null) {
                    if (isPreview) {
                        mCamera.stopPreview();
                        mCamera.release();
                        mCamera = null;
                    }
                }
            }
        });
        //设置改SurfaceView自己不维护缓冲
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    private void initCamera() {
        if (!isPreview) {
            mCamera = android.hardware.Camera.open();
        }

        if (mCamera != null && !isPreview) {

            try {
                android.hardware.Camera.Parameters parameters = mCamera.getParameters();
                //设置浏览照片的大小
                parameters.setPictureSize(mScreenWidth, mScreenHeight);
                //每秒显示4帧
                parameters.setPreviewFrameRate(4);
                //设置图片格式
                parameters.setPictureFormat(PixelFormat.JPEG);
                //设置照片的质量
                parameters.set("jpeg-quality", 85);
                //设置照片的大小
                parameters.setPictureSize(mScreenWidth, mScreenHeight);
                mCamera.setParameters(parameters);
                //通过SurfaceView显示取景画面
                mCamera.setPreviewDisplay(mSurfaceHolder);
                //开始预览
                mCamera.startPreview();
                //自动对焦
                mCamera.autoFocus(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            isPreview = true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            //当用户单击照相键、中央键时执行拍照
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_CAMERA:
                if (mCamera != null && event.getRepeatCount() == 0) {
                    //拍照
                    mCamera.takePicture(null, null, mPictureCallback);
                    return true;
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    android.hardware.Camera.PictureCallback mPictureCallback = new android.hardware.Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, android.hardware.Camera camera) {
            //根据拍照所得的数据创建位图
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            View view = getLayoutInflater().inflate(R.layout.layout_show_image, null);
            ImageView ivShow = view.findViewById(R.id.iv_show);
            ivShow.setImageBitmap(bitmap);

            //重新浏览
            mCamera.stopPreview();
            mCamera.startPreview();
            isPreview = true;

        }
    };

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_take_photo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

package com.zhq.exclusivememory.ui.activity.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.zhq.exclusivememory.R;

/**
 * Created by Huiqiang Zhang
 * on 2019/5/9.
 */

public class LuckyPanView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder mSurfaceHolder;
    /**
     * 绘制弧的直径
     */
    private int mDiameter;
    /**
     * 整个view的padding值
     */
    private int mPadding;
    /**
     * 中心点
     */
    private int mCenter;
    /**
     * 绘制圆弧的画笔
     */
    private Paint mArcPaint;
    /**
     * 绘制文字的画笔
     */
    private Paint mTextPaint;
    /**
     * 转盘区域分块数量
     */
    private int mItemCount = 6;
    /**
     * 判断绘制的线程的状态
     */
    private boolean isRunning;
    /**
     * 每个区域上的icon
     */
    private int[] icon = {R.mipmap.ic_money, R.mipmap.ic_money, R.mipmap.ic_money, R.mipmap.ic_money, R.mipmap.ic_money, R.mipmap.ic_money};
    /**
     * 每个区域上对应的文字
     */
    private String[] text = {"一百万", "十元", "一百元", "一元", "两百元", "再接再厉"};
    /**
     * 每个盘块的颜色
     */
    private int[] mColors = new int[]{0xFFFFC300, 0xFFF17E01, 0xFFFFC300,
            0xFFF17E01, 0xFFFFC300, 0xFFF17E01};
    /**
     * 背景图的bitmap
     */
    private Bitmap mBgBitmap = BitmapFactory.decodeResource(getResources(),
            R.mipmap.ic_bg);

    /**
     * 文字的大小
     */
    private float mTextSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics());
    /**
     * 绘制的范围
     */
    private RectF mRange = new RectF();
    /**
     * icon的集合
     */
    private Bitmap[] mImgBitmap;
    /**
     * 绘制的线程
     */
    private Thread mThread;
    private Canvas mCanvas;
    /**
     * 滚动的速度
     */
    private double mSpeed;
    private volatile float mStartAngle = 0;
    /**
     * 是否点击了停止
     */
    private boolean isShouldEnd;


    public LuckyPanView(Context context) {
        this(context, null);
    }

    public LuckyPanView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LuckyPanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取SurfaceHolder
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);

        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
        //获取圆的直径
        mDiameter = width - getPaddingLeft() - getPaddingRight();
        //获取padding值
        mPadding = getPaddingLeft();
        //获取中心点 原点
        mCenter = width / 2;
        setMeasuredDimension(width, width);
    }

    /**
     * 做一些初始化操作
     *
     * @param holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //创建绘制圆弧的画笔
        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setDither(true);
        //创建绘制文字的画笔
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.parseColor("#ffffff"));
        mTextPaint.setTextSize(mTextSize);
        //圆弧绘制的范围
        mRange = new RectF(getPaddingLeft(), getPaddingLeft(), mDiameter + getPaddingLeft(), mDiameter + getPaddingLeft());
        //初始化图片
        mImgBitmap = new Bitmap[mItemCount];
        for (int i = 0; i < mItemCount; i++) {
            mImgBitmap[i] = BitmapFactory.decodeResource(getResources(), icon[i]);
        }

        //开启线程
        isRunning = true;
        mThread = new Thread(this);
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
    }

    /**
     * 线程执行的run方法
     */
    @Override
    public void run() {

// 不断的进行draw
        while (isRunning) {
            long start = System.currentTimeMillis();
            draw();
            long end = System.currentTimeMillis();
            try {
                if (end - start < 50) {
                    Thread.sleep(50 - (end - start));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void draw() {
        try {
            // 获得canvas
            mCanvas = mSurfaceHolder.lockCanvas();
            if (mCanvas != null) {
                // 绘制背景图
                drawBg();

                /**
                 * 绘制每个块块，每个块块上的文本，每个块块上的图片
                 */
                float tmpAngle = mStartAngle;
                float sweepAngle = (float) (360 / mItemCount);
                for (int i = 0; i < mItemCount; i++) {
                    // 绘制快快
                    mArcPaint.setColor(mColors[i]);
                    mCanvas.drawArc(mRange, tmpAngle, sweepAngle, true,
                            mArcPaint);
                    // 绘制文本
                    drawText(tmpAngle, sweepAngle, text[i]);
                    // 绘制Icon
                    drawIcon(tmpAngle, i);

                    tmpAngle += sweepAngle;
                }

                // 如果mSpeed不等于0，则相当于在滚动
                mStartAngle += mSpeed;

                // 点击停止时，设置mSpeed为递减，为0值转盘停止
                if (isShouldEnd) {
                    mSpeed -= 1;
                }
                if (mSpeed <= 0) {
                    mSpeed = 0;
                    isShouldEnd = false;
                }
                // 根据当前旋转的mStartAngle计算当前滚动到的区域
                calInExactArea(mStartAngle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCanvas != null)
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }

    }

    private void drawIcon(float startAngle, int i) {
// 设置图片的宽度为直径的1/8
        int imgWidth = mDiameter / 8;

        float angle = (float) ((30 + startAngle) * (Math.PI / 180));

        int x = (int) (mCenter + mDiameter / 2 / 2 * Math.cos(angle));
        int y = (int) (mCenter + mDiameter / 2 / 2 * Math.sin(angle));

        // 确定绘制图片的位置
        Rect rect = new Rect(x - imgWidth / 2, y - imgWidth / 2, x + imgWidth
                / 2, y + imgWidth / 2);

        mCanvas.drawBitmap(mImgBitmap[i], null, rect, null);
    }

    private void calInExactArea(float startAngle) {
        float rotate = startAngle + 90.0F;
        rotate = (float)((double)rotate % 360.0D);

        for(int i = 0; i < this.mItemCount; ++i) {
            float from = (float)(360 - (i + 1) * (360 / this.mItemCount));
            float to = from + 360.0F - (float)(i * (360 / this.mItemCount));
            if(rotate > from && rotate < to) {
                return;
            }
        }
    }

    private void drawText(float startAngle, float sweepAngle, String s) {
        Path path = new Path();
        path.addArc(mRange, startAngle, sweepAngle);
        float textWidth = mTextPaint.measureText(s);
        // 利用水平偏移让文字居中
        float hOffset = (float) (mDiameter * Math.PI / mItemCount / 2 - textWidth / 2);// 水平偏移
        float vOffset = mDiameter / 2 / 6;// 垂直偏移
        mCanvas.drawTextOnPath(s, path, hOffset, vOffset, mTextPaint);

    }

    private void drawBg() {
        mCanvas.drawColor(0xFFFFFFFF);
        mCanvas.drawBitmap(mBgBitmap, null, new Rect(mPadding / 2,
                mPadding / 2, getMeasuredWidth() - mPadding / 2,
                getMeasuredWidth() - mPadding / 2), null);
    }

    public boolean isStart(){
        if(!isShouldEnd&&mSpeed>0){
            return true;
        }else {
            return false;
        }
    }

    public boolean isShouldEnd(){
        return isShouldEnd;
    }

    /**
     * 点击开始旋转
     *
     * @param luckyIndex
     */
    public void luckyStart(int luckyIndex)
    {
        // 每项角度大小
        float angle = (float) (360 / mItemCount);
        // 中奖角度范围（因为指针向上，所以水平第一项旋转到指针指向，需要旋转210-270；）
        float from = 270 - (luckyIndex + 1) * angle;
        float to = from + angle;
        // 停下来时旋转的距离
        float targetFrom = 4 * 360 + from;
        /**
         * <pre>
         *  (v1 + 0) * (v1+1) / 2 = target ;
         *  v1*v1 + v1 - 2target = 0 ;
         *  v1=-1+(1*1 + 8 *1 * target)/2;
         * </pre>
         */
        float v1 = (float) (Math.sqrt(1 * 1 + 8 * 1 * targetFrom) - 1) / 2;
        float targetTo = 4 * 360 + to;
        float v2 = (float) (Math.sqrt(1 * 1 + 8 * 1 * targetTo) - 1) / 2;

        mSpeed = (float) (v1 + Math.random() * (v2 - v1));
        isShouldEnd = false;
    }


    public void luckyEnd()
    {
        mStartAngle = 0;
        isShouldEnd = true;
    }

}

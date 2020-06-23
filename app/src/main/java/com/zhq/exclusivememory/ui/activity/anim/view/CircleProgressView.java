package com.zhq.exclusivememory.ui.activity.anim.view;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.zhq.exclusivememory.R;

public class CircleProgressView extends View {

    private Paint paint;
    private Path dst;
    private Path circlePath;
    private PathMeasure pathMeasure;
    private Float animatedValue;

    private float mProgress;
    private Bitmap bitmap;

    public float setProgress(int progress) {
        this.mProgress = (float) progress;
        return mProgress;
    }

    public float getProgress() {
        return mProgress;
    }

    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_airplane);
        //初始化画笔
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        //创建保存截取路径的path
        dst = new Path();
        //创建原始圆形路径
        circlePath = new Path();
        circlePath.addCircle(200,200, 200, Path.Direction.CW);
        //创建PathMeasure
        pathMeasure = new PathMeasure();
        pathMeasure.setPath(circlePath, true);
        //创建属性动画获取每次要改变的值
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatedValue = (Float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setDuration(2000);
        valueAnimator.start();
    }

    private float[] pos = new float[2];
    private float[] tan = new float[2];

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //路径的绘制
        float stopD = pathMeasure.getLength() * animatedValue;
        dst.reset();
        pathMeasure.getSegment(0, stopD, dst, true);
        canvas.drawPath(dst, paint);
        //飞机旋转的绘制
        pathMeasure.getPosTan(stopD,pos,tan);
        //计算旋转的角度
        float degrees = (float) (Math.atan2(tan[1],tan[0]) * 180.0 / Math.PI);
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees,bitmap.getWidth()/2,bitmap.getHeight()/2);
        matrix.postTranslate(pos[0]-bitmap.getWidth()/2,pos[1]-bitmap.getHeight()/2);
        canvas.drawBitmap(bitmap,matrix,paint); 
    }
}

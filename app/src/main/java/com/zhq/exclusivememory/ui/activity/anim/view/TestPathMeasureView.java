package com.zhq.exclusivememory.ui.activity.anim.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class TestPathMeasureView extends View {

    private PathMeasure pathMeasure;
    private Paint paint;

    public TestPathMeasureView(Context context) {
        this(context,null);
    }

    public TestPathMeasureView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TestPathMeasureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
     setLayerType(LAYER_TYPE_SOFTWARE,null);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        //初始化PathMeasure  只需要初始化一个即可
        //1.创建PathMeasure对象 2.通过构造方法传入path创建PathMeasure对象
        pathMeasure = new PathMeasure();


    }

    private static final String TAG = "TestPathMeasureView";

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(100,100);
        Path path = new Path();
        path.addRect(-50,-50,50,50,Path.Direction.CW);
        Path dst = new Path();
        dst.lineTo(10,120);
        pathMeasure.setPath(path,false);
        pathMeasure.getSegment(0,150,dst,false);
        canvas.drawPath(dst,paint);
    }
}

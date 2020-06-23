package com.zhq.exclusivememory.ui.activity.custom_view.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Huiqiang Zhang
 * on 2019/5/2.
 */

public class BaseView extends View {

    private Paint mPaint;

    public BaseView(Context context) {
        this(context,null);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawColor(Color.YELLOW);//参数值必须是8位的0xAARRGGBB样式颜色值
//        canvas.drawARGB(50,50,50,50);//A、R、G、B参数是每个颜色值的分量，范围0~255（0x00~0xFF）
//        canvas.drawRGB(50,50,50);//R、G、B颜色值的分量，范围0~255（0x00~0xFF），A默认取255
        /**
         * startX、startY 开始的x、y坐标
         * stopX、stopY 结束的x、y坐标
         * paint 使用的画笔
         * 直线的粗细与画笔的填充模式Style无关，与画笔的strokeWidth有关
         */
//        mPaint.setColor(Color.YELLOW);
//        canvas.drawLine(100,100,200,200,mPaint);
        /**
         * x、y 画点的位置
         * 点的大小与画笔的宽度有关，与画笔的填充模式无关
         */
//        canvas.drawPoint(300,300,mPaint);
//        //1.传Rect
//        Rect rect = new Rect(10, 10, 100, 100);
//        canvas.drawRect(rect,mPaint);
//        //2.传RectF
//        RectF rectF = new RectF();
//        //rectF.set(rect);
//        rectF.set(10f,10f,100f,100f);
//        canvas.drawRect(rectF,mPaint);
//        //3.直接传入矩形数值
//        canvas.drawRect(10f,10f,100f,100f,mPaint);

//        Path path = new Path();
//        path.moveTo(10,10);//x、y设置路径的起始点
//        path.lineTo(10,100);//x、y设置路径的终止点，也是下一次画路径的起始点
//        path.lineTo(100,200);
//        path.close();//最终首尾相接想成闭环
//        canvas.drawPath(path,mPaint);
//        mPaint.setColor(Color.YELLOW);
//        Path path1 = new Path();
//        //画路径开始的点
//        path1.moveTo(10,10);
//        //生成椭圆的矩形
//        RectF rectF = new RectF(100, 10, 200, 100);
//        //画弧 startAngle 弧开始的角度 以X轴正方向为0度 sweepAngle 弧持续的角度
//        //默认情况下路径都是连贯的，此处会和（10,10）相连，通过设置forceMoveTo这个参数
//        //设置是否强制的将弧的起始点作为绘制起始位置
//        path1.arcTo(rectF,0,180,true);
//        //addXXX系列函数，将直接添加固定形状的路径
////        path1.addCircle(50,50,20, Path.Direction.CW);
//        //
//        canvas.drawPath(path1,mPaint);

//        //创建一个Region区域
//        Region region = new Region(50, 50, 200, 100);
//        //自定义一个画Region的方法
//        drawRegion(canvas,region,mPaint);
        //设置画笔为填充模式
//        mPaint.setStyle(Paint.Style.FILL);
//        //构造一条椭圆路径
//        Path ovalPath = new Path();
//        RectF rectF = new RectF(50, 50, 200, 500);
//        ovalPath.addOval(rectF, Path.Direction.CCW);
//        //在setPath函数中传入一个比椭圆区域小的矩形区域，让其取交集
//        Region region = new Region();
//        region.setPath(ovalPath,new Region(50,50,200,200));
//        //画出路径
//        drawRegion(canvas,region,mPaint);

//        Region region = new Region(20, 20, 200, 100);
//        region.union(new Rect(20,20,50,300));
//        drawRegion(canvas,region,mPaint);

        //构造两个矩形
        Rect rect1 = new Rect(100, 100, 400, 200);
        Rect rect2 = new Rect(200, 0, 300, 300);
        //画出矩形的轮廓
        canvas.drawRect(rect1,mPaint);
        canvas.drawRect(rect2,mPaint);
        //构造两个区域
        Region region1 = new Region(rect1);
        Region region2 = new Region(rect2);


//        region1.op(region2, Region.Op.INTERSECT);//取交集
//        region1.op(region2, Region.Op.DIFFERENCE);//补集：region1对于region2来说不同的区域
//        region1.op(region2, Region.Op.UNION);//并集：region1和region2两者的并集
//        region1.op(region2, Region.Op.REPLACE);//替换：最终为region2的区域
//        region1.op(region2, Region.Op.REVERSE_DIFFERENCE);//反转补集：最终为region2对于region1来说不同的区域
//        region1.op(region2, Region.Op.XOR);//异并集：最终为regio1和region2相交区域之外的区域
        //构造一个填充的画笔便于看到相交的区域
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.YELLOW);
        //画最终的区域
//        drawRegion(canvas,region1,paint);

        Region region = new Region();
        region.op(region1,region2, Region.Op.INTERSECT);
        drawRegion(canvas,region,paint);
        setLayerType(LAYER_TYPE_SOFTWARE,null);


    }

    private void drawRegion(Canvas canvas, Region region, Paint paint) {
        RegionIterator iterator = new RegionIterator(region);
        Rect rect = new Rect();
        while (iterator.next(rect)){
            canvas.drawRect(rect,paint);
        }
    }
}

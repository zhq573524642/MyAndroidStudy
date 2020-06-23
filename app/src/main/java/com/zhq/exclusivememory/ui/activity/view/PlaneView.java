package com.zhq.exclusivememory.ui.activity.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.zhq.exclusivememory.R;

/**
 * Created by Huiqiang Zhang
 * on 2019/2/18.
 */

public class PlaneView extends View {

    private final Bitmap mBitmap;
    public float currentX;
    public float currentY;
    private final Paint mPaint;

    public PlaneView(Context context) {
        super(context);
        mPaint = new Paint();
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_plane);
        setFocusable(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mBitmap,currentX,currentY,mPaint);
    }
}

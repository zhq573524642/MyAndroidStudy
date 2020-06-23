package com.zhq.exclusivememory.widget;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Huiqiang Zhang
 * on 2019/5/26.
 */

public class FallingBallImageView extends android.support.v7.widget.AppCompatImageView {
    public FallingBallImageView(Context context) {
        super(context);
    }

    public FallingBallImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setFallingPoint(Point point){
        this.layout(point.x,point.y,point.x+getWidth(),point.y+getHeight());
    }
}

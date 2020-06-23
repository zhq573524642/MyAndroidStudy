package com.zhq.exclusivememory.widget;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.loader.ImageLoader;
import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.utils.MyUtils;


/**
 * Created by Huiqiang Zhang
 * on 2018/10/24.
 */

public class GlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        CornerTransform cornerTransform = new CornerTransform(context, MyUtils.dip2px(context, 8));
        cornerTransform.setExceptCorner(false, false, false, false);
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(R.mipmap.ic_launcher)
                .transform(cornerTransform);
        Glide.with(context)
                .load(path)
                .apply(options)
                .into(imageView);
    }
}

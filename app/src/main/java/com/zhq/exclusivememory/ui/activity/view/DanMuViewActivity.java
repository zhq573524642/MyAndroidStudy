package com.zhq.exclusivememory.ui.activity.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseSimpleActivity;
import com.zhq.exclusivememory.utils.DensityUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.loader.ILoader;
import master.flame.danmaku.danmaku.loader.IllegalDataException;
import master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.Danmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.BaseCacheStuffer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.model.android.SpannedCacheStuffer;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.parser.IDataSource;
import master.flame.danmaku.danmaku.util.IOUtils;
import master.flame.danmaku.ui.widget.DanmakuView;

import static java.security.AccessController.getContext;

/**
 * Created by Huiqiang Zhang
 * on 2019/5/14.
 */

public class DanMuViewActivity extends BaseSimpleActivity {
    @BindView(R.id.image)
    ImageView mImage;
    @BindView(R.id.dan_ma_ku_view)
    DanmakuView mDanMaKuView;
    @BindView(R.id.btn_show)
    Button mBtnShow;
    @BindView(R.id.btn_hide)
    Button mBtnHide;
    @BindView(R.id.btn_pause)
    Button mBtnPause;
    @BindView(R.id.btn_resume)
    Button mBtnResume;
    @BindView(R.id.btn_send_text)
    Button mBtnSendText;
    @BindView(R.id.btn_send_text_image)
    Button mBtnSendTextImage;
    private DanmakuContext mDanmakuContext;
    private String image="http://img2.imgtn.bdimg.com/it/u=3862162196,291897588&fm=26&gp=0.jpg";
    private BaseDanmakuParser mParser;
    private BaseCacheStuffer.Proxy mCacheStufferAdapter = new BaseCacheStuffer.Proxy() {

        private Drawable mDrawable;

        @Override
        public void prepareDrawing(final BaseDanmaku danmaku, boolean fromWorkerThread) {
            if (danmaku.text instanceof Spanned) { // 根据你的条件检查是否需要需要更新弹幕
                // FIXME 这里只是简单启个线程来加载远程url图片，请使用你自己的异步线程池，最好加上你的缓存池
                new Thread() {

                    @Override
                    public void run() {
                        String url = "http://www.bilibili.com/favicon.ico";
                        InputStream inputStream = null;
                        Drawable drawable = mDrawable;
                        if(drawable == null) {
                            try {
                                URLConnection urlConnection = new URL(url).openConnection();
                                inputStream = urlConnection.getInputStream();
                                drawable = BitmapDrawable.createFromStream(inputStream, "bitmap");
                                mDrawable = drawable;
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                IOUtils.closeQuietly(inputStream);
                            }
                        }
                        if (drawable != null) {
                            drawable.setBounds(0, 0, 100, 100);
                            SpannableStringBuilder spannable = createSpannable(drawable);
                            danmaku.text = spannable;
                            if(mDanMaKuView != null) {
                                mDanMaKuView.invalidateDanmaku(danmaku, false);
                            }
                            return;
                        }
                    }
                }.start();
            }
        }

        @Override
        public void releaseResource(BaseDanmaku danmaku) {
            // TODO 重要:清理含有ImageSpan的text中的一些占用内存的资源 例如drawable
        }
    };

    @Override
    protected void initData() {
        Glide.with(this).load(image).into(mImage);
    }

    @Override
    protected void initView() {
        //设置最大显示行数
        HashMap<Integer, Integer> maxLinesPair = new HashMap<>();
        maxLinesPair.put(Danmaku.TYPE_SCROLL_RL,5);//从右往左最大显示5行
        //设置是否禁止重叠
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<Integer, Boolean>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);
        mDanmakuContext = DanmakuContext.create();
        mDanmakuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3)
                .setDuplicateMergingEnabled(false).setScrollSpeedFactor(1.2f).setScaleTextSize(1.2f)
//                .setCacheStuffer(new SpannedCacheStuffer(), mCacheStufferAdapter) // 图文混排使用SpannedCacheStuffer
//        .setCacheStuffer(new BackgroundCacheStuffer())  // 绘制背景使用BackgroundCacheStuffer
                .setMaximumLines(maxLinesPair)//设置最大显示行数
                .preventOverlapping(overlappingEnablePair)//设置是否重叠
                .setDanmakuMargin(40);//设置margin值

        if (mDanMaKuView != null) {
            mParser = createParser();
            mDanMaKuView.setCallback(new DrawHandler.Callback() {
                @Override
                public void prepared() {
                    showDanmaku=true;
                    generateSomeDanmaku(mDanMaKuView);
                    mDanMaKuView.start();
                }

                @Override
                public void updateTimer(DanmakuTimer timer) {

                }

                @Override
                public void danmakuShown(BaseDanmaku danmaku) {

                }

                @Override
                public void drawingFinished() {

                }
            } );
            mDanMaKuView.setOnDanmakuClickListener(new IDanmakuView.OnDanmakuClickListener() {

                @Override
                public boolean onDanmakuClick(IDanmakus danmakus) {
                    Log.d("DFM", "onDanmakuClick: danmakus size:" + danmakus.size());
                    BaseDanmaku latest = danmakus.last();
                    if (null != latest) {
                        Log.d("DFM", "onDanmakuClick: text of latest danmaku:" + latest.text);
                        return true;
                    }
                    return false;
                }

                @Override
                public boolean onDanmakuLongClick(IDanmakus danmakus) {
                    return false;
                }

                @Override
                public boolean onViewClick(IDanmakuView view) {
                   //点击屏幕任意位置
                    return false;
                }
            });
            mDanMaKuView.prepare(mParser, mDanmakuContext);
            mDanMaKuView.showFPS(true);
            mDanMaKuView.enableDanmakuDrawingCache(true);
        }
    }

    private BaseDanmakuParser createParser() {

            return new BaseDanmakuParser() {

                @Override
                protected Danmakus parse() {
                    return new Danmakus();
                }
            };

    }

    /**
     * 随机生成一些弹幕内容以供测试
     */
    private boolean showDanmaku;

    private void generateSomeDanmaku(final DanmakuView danmakuView) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (showDanmaku) {
                    int time = new Random().nextInt(300);
                    String content = "" + time + time;
                    addDanmaku(danmakuView, content, false);
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * 向弹幕View中添加一条弹幕
     *
     * @param content    弹幕的具体内容
     * @param withBorder 弹幕是否有边框
     */
    private void addDanmaku(DanmakuView danmakuView, String content, boolean withBorder) {
        BaseDanmaku danmaku = mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text = content;
        danmaku.padding = 5;
        danmaku.textSize = DensityUtil.sp2px(this, 20);
        danmaku.textColor = Color.WHITE;
        danmaku.setTime(mDanMaKuView.getCurrentTime());
        if (withBorder) {
            danmaku.borderColor = Color.GREEN;
        }
        danmakuView.addDanmaku(danmaku);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDanMaKuView != null && mDanMaKuView.isPrepared() && mDanMaKuView.isPaused()) {
            mDanMaKuView.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mDanMaKuView != null && mDanMaKuView.isPrepared()) {
            mDanMaKuView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDanMaKuView != null) {
            // dont forget release!
            showDanmaku=false;
            mDanMaKuView.release();
            mDanMaKuView = null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mDanMaKuView != null) {
            // dont forget release!
            mDanMaKuView.release();
            mDanMaKuView = null;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_dan_mu_view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_show, R.id.btn_hide, R.id.btn_pause, R.id.btn_resume, R.id.btn_send_text, R.id.btn_send_text_image,R.id.btn_start_activity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_show:
                if (mDanMaKuView != null && mDanMaKuView.isPrepared()) {
                    mDanMaKuView.show();
                }
                break;
            case R.id.btn_hide:
                if (mDanMaKuView != null && mDanMaKuView.isPrepared()) {
                    mDanMaKuView.hide();
                }
                break;
            case R.id.btn_pause:
                if (mDanMaKuView != null && mDanMaKuView.isPrepared()) {
                    mDanMaKuView.pause();
                }
                break;
            case R.id.btn_resume:
                if (mDanMaKuView != null && mDanMaKuView.isPrepared()) {
                    mDanMaKuView.resume();
                }
                break;
            case R.id.btn_send_text:
                if (mDanMaKuView != null && mDanMaKuView.isPrepared()) {
//                   addDanmaku(false);
                    addDanmaku(mDanMaKuView,"哈哈哈啦啦啦",true);
                }
                break;
            case R.id.btn_send_text_image:
                break;
            case R.id.btn_start_activity:
                Intent intent = new Intent();
                intent.setClass(this,SurfaceViewTestActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void addDanmaku(boolean islive) {
        BaseDanmaku danmaku = mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        if (danmaku == null || mDanMaKuView == null) {
            return;
        }
        // for(int i=0;i<100;i++){
        // }
        danmaku.text = "这是一条弹幕" + System.nanoTime();
        danmaku.padding = 5;
        danmaku.priority = 0;  // 可能会被各种过滤器过滤并隐藏显示
        danmaku.isLive = islive;
        danmaku.setTime(mDanMaKuView.getCurrentTime() + 1200);
        danmaku.textSize = 25f * (mParser.getDisplayer().getDensity() - 0.6f);
        danmaku.textColor = Color.RED;
        danmaku.textShadowColor = Color.WHITE;
        // danmaku.underlineColor = Color.GREEN;
        danmaku.borderColor = Color.GREEN;
        mDanMaKuView.addDanmaku(danmaku);

    }

    private SpannableStringBuilder createSpannable(Drawable drawable) {
        String text = "bitmap";
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        ImageSpan span = new ImageSpan(drawable);//ImageSpan.ALIGN_BOTTOM);
        spannableStringBuilder.setSpan(span, 0, text.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.append("图文混排");
        spannableStringBuilder.setSpan(new BackgroundColorSpan(Color.parseColor("#8A2233B1")), 0, spannableStringBuilder.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return spannableStringBuilder;
    }
}

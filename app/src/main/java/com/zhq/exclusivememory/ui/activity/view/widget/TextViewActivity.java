package com.zhq.exclusivememory.ui.activity.view.widget;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.api.API;
import com.zhq.exclusivememory.base.BaseSimpleActivity;
import com.zhq.exclusivememory.constant.Constant;
import com.zhq.exclusivememory.ui.activity.web_view.WebViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Huiqiang Zhang
 * on 2019/8/19.
 */

public class TextViewActivity extends BaseSimpleActivity {
    @BindView(R.id.ll_left_back)
    LinearLayout mLlLeftBack;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.btn_watch_note)
    Button mBtnWatchNote;
    @BindView(R.id.text_view)
    TextView mTextView;
    @BindView(R.id.text_view1)
    TextView mTextView1;
    @BindView(R.id.text_view2)
    TextView mTextView2;
    @BindView(R.id.text_view3)
    TextView mTextView3;
    @BindView(R.id.ll_parent_view)
    RelativeLayout mLlParentView;

    @Override
    protected void initData() {
        mTvCenterTitle.setText("TextView");
    }

    @Override
    protected void initView() {
        mTextView.setText("这是网页：https://www.baidu.com/\n这是手机号：17610676602\n这是位置：北京市");
        mTextView.setAutoLinkMask(Linkify.EMAIL_ADDRESSES);
        /**
         * Linkify.WEB_URLS 匹配Web UR

         Linkify.PHONE_NUMBERS 匹配电话号码

         Linkify.EMAIL_ADDRESSES 匹配电子邮件地址

         Linkify.MAP_ADDRESSES 地理位置匹配

         Linkify.ALL 匹配所有可用的模式
         */
        mTextView1.setText("bufferType=\"editable\"\n我是主要的文本\n");
        Editable text = (Editable) mTextView1.getText();
        text.append("我是被拼接的文本");
        //bufferType=Spannable
        SpannableString ss = new SpannableString("字体测试字体大小一半两倍前景色背景色正常粗体斜体粗斜体下划线删除线x1x2电话邮件网站短信彩信地图X轴综合/bot");
        //标识在 Span 范围内的文本前后输入新的字符时是否把它们也应用这个效果
        //1.设置字体(default,default-bold,monospace,serif,sans-serif)
        //此处设置字体无反应
        ss.setSpan(new TypefaceSpan("default-bold"), 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        //2.设置字体大小
        //绝对字体的大小AbsoluteSizeSpan中参数二：true（单位为dip）,否则单位为像素
        ss.setSpan(new AbsoluteSizeSpan(20, true), 6, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new AbsoluteSizeSpan(20), 7, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //相对字体大小RelativeSizeSpan,单位像素，参数表示为默认字体大小的多少倍
        ss.setSpan(new RelativeSizeSpan(0.5f), 8, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new RelativeSizeSpan(2.0f), 10, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //3.设置颜色
        //文字前景色
        ss.setSpan(new ForegroundColorSpan(Color.RED), 12, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //文字背景色
        ss.setSpan(new BackgroundColorSpan(Color.GREEN), 15, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //4.设置字体样式正常，粗体，斜体，粗斜体
        ss.setSpan(new StyleSpan(Typeface.NORMAL), 18, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new StyleSpan(Typeface.BOLD), 20, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new StyleSpan(Typeface.ITALIC), 22, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 24, 27, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //5.设置下划线、删除线
        ss.setSpan(new UnderlineSpan(), 27, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new StrikethroughSpan(), 30, 33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //6.设置上下标start和end是要设置的文本的索引，如：x1，1为设置的文本
        //下标
        ss.setSpan(new SubscriptSpan(), 34, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //上标
        ss.setSpan(new SuperscriptSpan(), 36, 37, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //7.设置超链接（需要添加setMovementMethod方法附加响应）
        //电话
        ss.setSpan(new URLSpan("tel:17610676602"), 37, 39, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //邮件
        ss.setSpan(new URLSpan("mailto:zhq05410@163.com"), 39, 41, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //网址
        ss.setSpan(new URLSpan("https://www.baidu.com"), 41, 43, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //短信 使用sms或smsto
        ss.setSpan(new URLSpan("sms:17610676602"), 43, 45, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //彩信 使用mms或mmsto
        ss.setSpan(new URLSpan("mms:17610676602"), 45, 47, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //地图(纬度，经度)，直接打开手机里面的地图进行定位
        ss.setSpan(new URLSpan("geo:33.90689,109.16015"), 47, 49, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //8.设置字体的缩放 参数表示为默认字体宽度的多少倍
        ////2.0f表示默认字体宽度的两倍，即X轴方向放大为默认字体的两倍，而高度不变
        ss.setSpan(new ScaleXSpan(2.0f), 49, 51, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //9.设置字体（依次包括字体名称，字体大小，字体样式，字体颜色，链接颜色）
//        ColorStateList csllink = null;
//        ColorStateList csl = null;
//        XmlResourceParser xppcolor=getResources().getXml (R.color.color_FF0000);
//        try {
//            csl= ColorStateList.createFromXml(getResources(),xppcolor);
//        }catch(XmlPullParserException e){
//            // TODO: handle exception
//            e.printStackTrace();
//        }catch(IOException e){
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//
//        XmlResourceParser xpplinkcolor=getResources().getXml(R.color.color_orange);
//        try {
//            csllink= ColorStateList.createFromXml(getResources(),xpplinkcolor);
//        }catch(XmlPullParserException e){
//            // TODO: handle exception
//            e.printStackTrace();
//        }catch(IOException e){
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//        ss.setSpan(new TextAppearanceSpan("monospace",android.graphics.Typeface.BOLD_ITALIC, 30, csl, csllink), 51, 53, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //10.设置图片
        Drawable drawable = getResources().getDrawable(R.drawable.audio_placeholder);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        ss.setSpan(new ImageSpan(drawable), 53, 57, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView2.setText(ss);
        mTextView2.setMovementMethod(LinkMovementMethod.getInstance());

        mTextView3.setText("这是一段测试的文本数据");
        mLlParentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TextViewActivity.this, "我点击的是父布局", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_text_view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_left_back, R.id.btn_watch_note})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_left_back:
                finish();
                break;
            case R.id.btn_watch_note:
                Intent intent = new Intent();
                intent.setClass(this, WebViewActivity.class);
                intent.putExtra(Constant.WEB_PAGE_URL, API.WIDGET_TEXT_VIEW);
                intent.putExtra(Constant.TITLE_NAME, "TextView属性");
                startActivity(intent);
                break;
        }
    }
}

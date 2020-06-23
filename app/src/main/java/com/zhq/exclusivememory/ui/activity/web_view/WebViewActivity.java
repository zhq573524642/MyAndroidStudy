package com.zhq.exclusivememory.ui.activity.web_view;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseSimpleActivity;
import com.zhq.exclusivememory.constant.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Huiqiang Zhang
 * on 2018/11/23.
 */

public class WebViewActivity extends BaseSimpleActivity {
    private static final int REQUEST_SELECT_ADDRESS = 1001;
    private static final int REQUEST_SELECT_SUPERIOR = 1002;
    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.rl_parent_layout)
    RelativeLayout mRlParentLayout;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private WebSettings mSettings;


    @SuppressLint("JavascriptInterface")
    @Override
    protected void initView() {

        final Intent intent = getIntent();
        String url = intent.getStringExtra(Constant.WEB_PAGE_URL);
        String title = intent.getStringExtra(Constant.TITLE_NAME);
        mSettings = mWebView.getSettings();
        mSettings.setJavaScriptEnabled(true);//开启与JavaScript交互
        mSettings.setUseWideViewPort(true);//将图片调整到适合webview的大小
        mSettings.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        mSettings.setSupportZoom(true);//设置支持缩放
        mSettings.setDisplayZoomControls(false);//隐藏原生的缩放控件
        mSettings.setAllowFileAccess(true);//设置可以访问文件
        mSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mSettings.setDomStorageEnabled(true);
        mSettings.setSaveFormData(true);
        mSettings.setSavePassword(true);
        mSettings.setGeolocationEnabled(true);
        mSettings.setBlockNetworkImage(false);
//        mSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        mSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWebView.setWebContentsDebuggingEnabled(true);
        }
        if (!TextUtils.isEmpty(title)) {
            mTvCenterTitle.setText(title);
        }
        if(!TextUtils.isEmpty(url)){
            mWebView.loadUrl(url);
        }

        mWebView.setWebViewClient(new myWebClient());
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                    if(mProgressBar!=null){
                        if (newProgress < 100) {
                            mProgressBar.setVisibility(View.VISIBLE);
                            mProgressBar.setProgress(newProgress);
                        } else {
                            mProgressBar.setVisibility(View.GONE);
                            mProgressBar.setProgress(newProgress);
                        }
                    }

            }
        });


    }


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_web_view;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //保存图片权限申请回调

    }


    @Override
    protected void onResume() {
        mWebView.onResume();
        mWebView.resumeTimers();
        mSettings.setJavaScriptEnabled(true);
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSettings.setJavaScriptEnabled(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.pauseTimers();
        mWebView.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mRlParentLayout.removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.ll_left_back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_left_back:
                finish();
                break;
        }
    }


    public class myWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return false;

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);

        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

}

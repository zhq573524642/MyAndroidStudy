package com.zhq.exclusivememory.view.web_view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/8.
 */

public class MyWebViewClient extends BridgeWebViewClient {
    private Context mContext;
    private String mUrl;

    public MyWebViewClient(BridgeWebView webView, Context context) {
        super(webView);
        this.mContext = context;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        try {
            mUrl = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (mUrl.trim().startsWith("yy:")) {
            return super.shouldOverrideUrlLoading(view, url);
        }
        if (mUrl.trim().startsWith("tel")) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(mUrl));
            mContext.startActivity(intent);
        } else {
            view.loadUrl(mUrl);
        }
        return true;
    }
}

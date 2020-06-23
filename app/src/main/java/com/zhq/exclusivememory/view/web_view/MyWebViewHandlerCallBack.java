package com.zhq.exclusivememory.view.web_view;

import android.text.TextUtils;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/8.
 */

public class MyWebViewHandlerCallBack implements BridgeHandler {

    private OnReceiveJsDateListener mOnReceiveJsDateListener;

    public MyWebViewHandlerCallBack(OnReceiveJsDateListener onReceiveJsDateListener) {
        mOnReceiveJsDateListener = onReceiveJsDateListener;
    }

    @Override
    public void handler(String data, CallBackFunction function) {
        if (!TextUtils.isEmpty(data) && mOnReceiveJsDateListener != null) {
            mOnReceiveJsDateListener.receiveJsData(data);
        }

        function.onCallBack("Native已经收到JS的数据");
    }

    public interface OnReceiveJsDateListener {

        void receiveJsData(String data);
    }

    public void setOnReceiveJsDateListener(OnReceiveJsDateListener onReceiveJsDateListener) {
        this.mOnReceiveJsDateListener = onReceiveJsDateListener;
    }
}

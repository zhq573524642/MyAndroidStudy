package com.zhq.exclusivememory;

import android.util.Log;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/31.
 */

public class JniTest {
    private static final String TAG = "JniTest";
    static {
        System.loadLibrary("jni-test");
    }

    public static void main(String[] args) {
        JniTest jniTest = new JniTest();
        System.out.print("====JNI"+jniTest.get());
        jniTest.set("Hello World");

    }

    public native String get();
    public native void set(String str);
}

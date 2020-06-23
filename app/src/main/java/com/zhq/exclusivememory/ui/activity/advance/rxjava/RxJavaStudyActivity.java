package com.zhq.exclusivememory.ui.activity.advance.rxjava;

import android.util.Log;

import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseSimpleActivity;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Huiqiang Zhang
 * on 2019/3/25.
 */

public class RxJavaStudyActivity extends BaseSimpleActivity {
    private static final String TAG = "RxJavaStudyActivity";

    @Override
    protected void initData() {
        //创建Observer 观察者
        Subscriber<String> subscriber = new Subscriber<String>() {

            @Override
            public void onSubscribe(Subscription s) {
                Log.i(TAG, "====onSubscribe " + s);
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "====onNext " + s);
            }

            @Override
            public void onError(Throwable t) {
                Log.i(TAG, "====onError " + t.getMessage());
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "====onComplete ");
            }
        };
//        Observer<String> observer = new Observer<String>() {
//
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(String s) {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        };
        //创建被观察者Observable
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                 emitter.onNext("哈哈哈");
                 emitter.onNext("啦啦啦");
                 emitter.onNext("嘿嘿嘿");
                 emitter.onComplete();
            }


        });
//        Observable<String> observable = Observable.just("蠢猪炘", "蠢猪达", "蠢猪煜");

        observable.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i(TAG,"====接收到的数据 "+s);
            }
        });
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_rxjava_study;
    }
}

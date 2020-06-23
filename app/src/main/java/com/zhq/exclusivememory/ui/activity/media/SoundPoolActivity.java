package com.zhq.exclusivememory.ui.activity.media;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseSimpleActivity;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Huiqiang Zhang
 * on 2019/6/16.
 */

public class SoundPoolActivity extends BaseSimpleActivity {
    @BindView(R.id.ll_left_back)
    LinearLayout mLlLeftBack;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.btn_play_one)
    Button mBtnPlayOne;
    @BindView(R.id.btn_play_two)
    Button mBtnPlayTwo;
    private HashMap<Integer,Integer> mHashMap =new HashMap<Integer,Integer>();
    private SoundPool mSoundPool;

    @Override
    protected void initData() {
        /**
         * 参数一：maxStreams 指定支持声音的数量
         * 参数二：streamType 指定声音的类型
         * 参数三：srcQuality 指定声音品质
         */
        //创建SoundPool
        mSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);
        //将音效通过调用load方法放入hashMap中
        mHashMap.put(1001, mSoundPool.load(this,R.raw.yulu,1));
        mHashMap.put(1002, mSoundPool.load(this,R.raw.duan,1));
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_sound_pool;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_left_back, R.id.btn_play_one, R.id.btn_play_two})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_left_back:
                finish();
                break;
            case R.id.btn_play_one:
                /**
                 * 参数一：播放哪个声音
                 * 参数二三：leftVolume rightVolume 左右音量
                 * 参数四：priority 播放声音的优先级，数值越大，优先级越高
                 * 参数五：loop 指定是否循环 0为不循环 -1为循环
                 * 参数六：rate 指定播放比率 数值可为0.5~2,1为正常播放比率
                 */
                mSoundPool.play(mHashMap.get(1001),1,1,0,0,1);
                break;
            case R.id.btn_play_two:
                mSoundPool.play(mHashMap.get(1002),1,1,0,0,1);
                break;
        }
    }
}

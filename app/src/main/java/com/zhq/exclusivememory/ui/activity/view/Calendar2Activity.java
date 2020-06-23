package com.zhq.exclusivememory.ui.activity.view;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseSimpleActivity;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Huiqiang Zhang
 * on 2019/3/14.
 */

public class Calendar2Activity extends BaseSimpleActivity {
    @BindView(R.id.ll_left_back)
    LinearLayout mLlLeftBack;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
//    @BindView(R.id.compact_calendar_view)
//    CompactCalendarView mCompactCalendarView;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
//   mCompactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);

//        final Event event = new Event(Color.YELLOW, 1552650068000L, "hathawa");
//        mCompactCalendarView.addEvent(event);
//        Event event1 = new Event(Color.YELLOW, 1552736468000L, "lalala");
//        mCompactCalendarView.addEvent(event1);
//        mCompactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
//            @Override
//            public void onDayClick(Date dateClicked) {
//                List<Event> events = mCompactCalendarView.getEvents(dateClicked);
//                Toast.makeText(Calendar2Activity.this, "事件 ", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onMonthScroll(Date firstDayOfNewMonth) {
//                Toast.makeText(Calendar2Activity.this, "滑动"+firstDayOfNewMonth, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_calendar_two;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ll_left_back)
    public void onViewClicked() {

    }
}

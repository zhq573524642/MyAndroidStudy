package com.zhq.exclusivememory.widget;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhq.exclusivememory.R;


/**
 * Created by Huiqiang.Zhang on 2017/3/28.
 */

public class BottomPopupOption {
    //上下文对象
    private Context mContext;
    //Title文字
    private String mTitle;
    //PopupWindow对象
    private PopupWindow mPopupWindow;
    //选项的文字
    private String[] options;
    //选项的颜色
    private int[] Colors;
    //点击事件
    private OnPopupWindowItemClickListener itemClickListener;

    /**
     * 一个参数的构造方法，用于弹出无标题的PopupWindow
     *
     * @param context
     */
    public BottomPopupOption(Context context) {
        this.mContext = context;
    }

    /**
     * 2个参数的构造方法，用于弹出有标题的PopupWindow
     *
     * @param context
     * @param title   标题
     */
    public BottomPopupOption(Context context, String title) {
        this.mContext = context;
        this.mTitle = title;
    }

    /**
     * 设置选项的点击事件
     *
     * @param itemClickListener
     */
    public void setItemClickListener(OnPopupWindowItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    /**
     * 设置选项文字
     */
    public void setItemText(String... items) {
        options = items;
    }

    /**
     * 设置选项文字颜色，必须要和选项的文字对应
     */
    public void setColors(int... color) {
        Colors = color;
    }


    /**
     * 添加子View
     * @param rootView 根布局容器
     * @param contentLayout 根布局中填充子条目的容器id
     * @param itemLayout 子条目的布局
     */
    private void addView(View rootView, int contentLayout, int itemLayout) {
        LinearLayout lin_layout = (LinearLayout) rootView.findViewById(contentLayout);
        LinearLayout popupWindowTitle = (LinearLayout) rootView.findViewById(R.id.ll_popup_window_title);
        TextView tvPopupWindowTitle = (TextView) rootView.findViewById(R.id.tv_popup_window_title);
        //取消按钮
        View btnCancel = rootView.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        if (options != null && options.length > 0) {
            if(!TextUtils.isEmpty(mTitle)){
                 popupWindowTitle.setVisibility(View.VISIBLE);
                 tvPopupWindowTitle.setText(mTitle);
                for (int i = 0; i < options.length; i++) {
                    View item = LayoutInflater.from(mContext).inflate(itemLayout, null);
                    TextView itemText = (TextView) item.findViewById(R.id.add);
                    itemText.setText(options[i]);
                    if (Colors != null && Colors.length == options.length) {
                        itemText.setTextColor(Colors[i]);
                    }
                    final int finalI = i;
                    itemText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (itemClickListener != null) {
                                itemClickListener.onItemClick(finalI);
                            }
                        }
                    });
                    lin_layout.addView(item);
                }
            }else {
                popupWindowTitle.setVisibility(View.GONE);
                for (int i = 0; i < options.length; i++) {
                    View item = LayoutInflater.from(mContext).inflate(itemLayout, null);
                    TextView itemText = (TextView) item.findViewById(R.id.add);
                    itemText.setText(options[i]);
                    if (Colors != null && Colors.length == options.length) {
                        itemText.setTextColor(Colors[i]);
                    }
                    final int finalI = i;
                    itemText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (itemClickListener != null) {
                                itemClickListener.onItemClick(finalI);
                            }
                        }
                    });
                    lin_layout.addView(item);
                }
            }

        }
    }

    /**
     * 弹出Popupwindow
     */
    public void showPopupWindow(int contentLayout, int itemLayout) {
        View popupWindow_view = LayoutInflater.from(mContext).inflate(R.layout.popwindows_container_layout, null);
        //添加子View
        addView(popupWindow_view, contentLayout, itemLayout);
        mPopupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setAnimationStyle(R.style.popwindow_anim_style);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpa(false);
            }
        });
        show(popupWindow_view);
    }


    /**
     * 显示PopupWindow
     */
    private void show(View v) {
        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);

        }
        setWindowAlpa(true);
    }


    /**
     * 消失PopupWindow
     */
    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    /**
     * 动态设置Activity背景透明度
     *
     * @param isopen
     */
    public void setWindowAlpa(boolean isopen) {
        if (android.os.Build.VERSION.SDK_INT < 11) {
            return;
        }
        final Window window = ((Activity) mContext).getWindow();
        final WindowManager.LayoutParams lp = window.getAttributes();
        window.setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ValueAnimator animator;
        if (isopen) {
            animator = ValueAnimator.ofFloat(1.0f, 0.5f);
        } else {
            animator = ValueAnimator.ofFloat(0.5f, 1.0f);
        }
        animator.setDuration(400);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                lp.alpha = alpha;
                window.setAttributes(lp);
            }
        });
        animator.start();
    }


    /**
     * 点击事件选择回调
     */
    public interface OnPopupWindowItemClickListener {
        void onItemClick(int position);
    }
}

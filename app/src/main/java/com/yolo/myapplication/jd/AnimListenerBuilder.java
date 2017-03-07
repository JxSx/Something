package com.yolo.myapplication.jd;

import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author: jiaxin
 * @date: 2016-12-22 15:47
 */

public class AnimListenerBuilder {

    private int newState;
    private boolean isAnimFinish = false;

    private ViewPropertyAnimatorListener listener;

    public AnimListenerBuilder() {
        listener = new ViewPropertyAnimatorListener() {
            @Override
            public void onAnimationStart(View view) {
                isAnimFinish = false;
            }

            @Override
            public void onAnimationEnd(View view) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //IDLE状态下隐藏，其余状态保持View显示
                    AnimUtils.hide(view);
                }
                isAnimFinish = true;
            }

            @Override
            public void onAnimationCancel(View view) {

            }
        };
    }

    public ViewPropertyAnimatorListener build() {
        return listener;
    }

    public void setNewState(int newState) {
        this.newState = newState;
    }

    public boolean isAnimFinish() {
        return isAnimFinish;
    }

}

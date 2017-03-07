package com.yolo.myapplication.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * @author: jiaxin
 * @date: 2017-01-16 15:23
 */

public class ExpandableView extends LinearLayout {

    private static final String TAG = ExpandableView.class.getSimpleName();

    public ExpandableView(Context context) {
        super(context);
    }

    public ExpandableView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        Log.i(TAG, getHeight()+"");
    }
}

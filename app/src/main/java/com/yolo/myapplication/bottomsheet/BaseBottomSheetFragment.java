package com.yolo.myapplication.bottomsheet;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;

/**
 * @author: jiaxin
 * @date: 2017-01-16 17:25
 */

public abstract class BaseBottomSheetFragment extends BottomSheetDialogFragment {

    protected Context context;

    protected BottomSheetDialog dialog;
    protected BottomSheetBehavior mBehavior;

    protected View rootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        if (rootView == null) {
            rootView = View.inflate(context, getLayoutResId(), null);
            initView();
        }
        resetView();
        dialog.setContentView(rootView);
        mBehavior = BottomSheetBehavior.from((View) rootView.getParent());
        mBehavior.setHideable(true);

        /*((View) rootView.getParent()).setBackgroundColor(Color.TRANSPARENT);
        rootView.post(new Runnable() {
            @Override
            public void run() {
                *//**
                 * PeekHeight默认高度为256dp，会在该高度上悬浮
                 * 设置等于view的高，就不会卡住
                 *//*
                mBehavior.setPeekHeight(rootView.getHeight());
            }
        });*/


        return dialog;
    }

    /**
     * 重置View和数据，子类可以选择实现
     * <p>
     * 为避免多次inflate，父类缓存了rootView
     * 所以不会每次打开都调用initView方法，但是每次都会调用该方法
     */
    protected void resetView() {

    }

    protected abstract int getLayoutResId();

    /**
     * 初始化View和设置数据等操作
     */
    protected abstract void initView();


    @Override
    public void onStart() {
        super.onStart();
    }

    public boolean isShowing(){
        return dialog != null && dialog.isShowing();
    }

}

package com.yolo.myapplication.viewpager;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.yolo.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends LazyFragment {

    private static final String TAG = "BlankFragment";

    public static BlankFragment getInstance(int index) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//
//        Log.i(TAG, "setUserVisibleHint" + isVisibleToUser + ">>" + this);
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate" + getArguments().getInt("index"));
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.i(TAG, "onActivityCreated" + getArguments().getInt("index"));
//        TextView textview = (TextView) getView().findViewById(R.id.text);
//        textview.setText("页码："+getArguments().getInt("index"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy" + getArguments().getInt("index"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView" + getArguments().getInt("index"));
    }

    @Override
    protected int getResId() {
        return R.layout.fragment_blank;
    }

    @Override
    protected void onRealViewLoaded(View view) {

        Log.i(TAG, "onRealViewLoaded" + getArguments().getInt("index"));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach" + getArguments().getInt("index"));
    }
}

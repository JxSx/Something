package com.yolo.myapplication.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.r0adkll.slidr.Slidr;
import com.yolo.myapplication.R;

public class ViewDemoActivity extends Activity {

    private static final String TAG = "Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_demo);

        Slidr.attach(this, getResources().getColor(R.color.colorPrimary),  getResources().getColor(R.color.colorPrimaryDark));
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "dispatchTouchEvent>>DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "dispatchTouchEvent>>MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "dispatchTouchEvent>>UP");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "onTouchEvent>>>>DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "onTouchEvent>>>>MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "onTouchEvent>>>>UP");
                break;
        }
        return false;
    }
}

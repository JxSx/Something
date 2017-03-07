package com.yolo.myapplication.hook;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

/**
 * @author: jiaxin
 * @date: 2017-03-07 17:01
 */

public class OtherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textView = new TextView(this);
        textView.setText("OtherActivity");
        textView.setTextColor(Color.RED);

        setContentView(textView);
    }
}

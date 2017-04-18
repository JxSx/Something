package com.yolo.myapplication.hook;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;

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

        handler.sendMessageDelayed(Message.obtain(), 3000);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            System.out.print("aaaaaa");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler=null;
    }
}

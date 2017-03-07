package com.yolo.myapplication.hook;

import android.app.Activity;
import android.os.Bundle;

/**
 * 通过此Activity能够使得未在清单中配置的Activity通过程序检测，启动这些Activity
 */
public class ProxyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * 1.启动一个Activity，通过跟踪源码发现，最终启动方法是在Instrumentation中调用了
         * ActivityManagerNative.getDefault().startActivities（ActivityManagerNative是IActivityManager的实现类）
         *
         * 2.通过反射获取getDefault()对象
         */

    }


}

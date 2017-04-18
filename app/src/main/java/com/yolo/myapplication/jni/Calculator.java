package com.yolo.myapplication.jni;

/**
 * @author: jiaxin
 * @date: 2017-03-22 17:49
 */

public class Calculator {

    static {
        System.loadLibrary("calculator");
    }

    private native int add(int i, int j);
}

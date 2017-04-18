package com.yolo.myapplication.proxy;

/**
 * @author: jiaxin
 * @date: 2017-03-28 14:37
 */

public class ShoppingImpl implements IShopping {

    private static final String TAG = ShoppingImpl.class.getSimpleName();

    @Override
    public void buy(String name) {
        System.out.println(TAG+">>>"+name);
    }

    @Override
    public void pay(int amount) {
        System.out.println(TAG+">>>"+amount);
    }
}

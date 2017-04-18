package com.yolo.myapplication.proxy;

/**
 * @author: jiaxin
 * @date: 2017-03-28 14:39
 */

public class ShoppingProxy implements IShopping {

    private IShopping instance;

    public ShoppingProxy(IShopping instance) {
        this.instance = instance;
    }

    @Override
    public void buy(String name) {
        instance.buy(name);
    }

    @Override
    public void pay(int amount) {
        instance.pay(amount);
    }
}

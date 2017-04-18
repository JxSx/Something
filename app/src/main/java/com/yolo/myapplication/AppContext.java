package com.yolo.myapplication;

import android.app.Application;

import com.cy.lib.upgrade.LibUpgradeInitializer;
import com.yolo.myapplication.hook.HookUtil;
import com.yolo.myapplication.hook.ProxyActivity;

/**
 * @author: jiaxin
 * @date: 2016-12-23 10:20
 */

public class AppContext extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        LibUpgradeInitializer.init(this);


        HookUtil hookUtil = new HookUtil(this, ProxyActivity.class);
        try {
            hookUtil.hookAms();

            hookUtil.hookActivityThreadHandler();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.yolo.myapplication.update;

import android.app.Activity;
import android.os.Bundle;

import com.cy.lib.upgrade.Updater;
import com.cy.lib.upgrade.UpdaterConfiguration;
import com.cy.lib.upgrade.callback.UpdateCheckCallback;
import com.cy.lib.upgrade.model.UpdateInfo;
import com.cy.upgrade.interfacedef.UpdateChecker;
import com.yolo.myapplication.R;

public class UpdateActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        final UpdaterConfiguration config = new UpdaterConfiguration();
        config.updateChecker(new UpdateChecker() {
            @Override
            public void check(UpdateCheckCallback callback) {
                UpdateInfo updateInfo = new UpdateInfo();
                updateInfo.setVersionCode(2);
                updateInfo.setVersionName("v1.2");
                updateInfo.setUpdateTime("2016/10/28");
                updateInfo.setUpdateSize(1024);
                updateInfo.setUpdateInfo("更新日志:\n1.新增万能更新库，实现更新功能只要几行代码。");
                //使用全量更新信息
                updateInfo.setUpdateType(UpdateInfo.UpdateType.TOTAL_UPDATE);
                UpdateInfo.TotalUpdateInfo totalUpdateInfo = new UpdateInfo.TotalUpdateInfo();
                totalUpdateInfo.setApkUrl("http://pkg-mt1.fir.im/ff323a310a6311e59d1635431778f0c84ce79990.apk?attname=Ziyun.apk_1.0.apk");
                updateInfo.setTotalUpdateInfo(totalUpdateInfo);
                if (updateInfo != null) {
                    //设置更新信息，这样各模块就可以通过config.getUpdateInfo()共享这个数据了,注意这个方法一定要调用且要在UpdateCheckCallback.onCheckSuccess之前调用
                    config.updateInfo(updateInfo);
                    callback.onCheckSuccess();
                } else {
                    callback.onCheckFail("");
                }

            }
        });
        Updater.getInstance().init(config);
        Updater.getInstance().check(this);
    }
}

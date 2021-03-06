package com.yolo.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.yolo.myapplication.bottomsheet.BottomSheetDemoActivity;
import com.yolo.myapplication.databinding.DataBindingActivity;
import com.yolo.myapplication.hook.OtherActivity;
import com.yolo.myapplication.jd.JDPageCountActivity;
import com.yolo.myapplication.rx.RxActivity;
import com.yolo.myapplication.update.UpdateActivity;
import com.yolo.myapplication.view.ViewDemoActivity;
import com.yolo.myapplication.viewpager.ViewPagerActivity;
import com.yolo.myapplication.waveview.WaveViewActivity;
import com.yolo.myapplication.widget.BottomSheetDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnJDPageCount = (Button) findViewById(R.id.btn_jd_page_count);
        final Button btnBottomSheet = (Button) findViewById(R.id.btn_bottom_sheet);
        Button btnWaveView = (Button) findViewById(R.id.btn_wave_view);
        Button btnCheckUpdate = (Button) findViewById(R.id.btn_check_update);
        Button btnDataBinding = (Button) findViewById(R.id.btn_data_binding);
        Button btnHook = (Button) findViewById(R.id.btn_hook);
        Button btnRx = (Button) findViewById(R.id.btn_Rx);
        Button btnView = (Button) findViewById(R.id.btn_view);
        Button btn_viewpager = (Button) findViewById(R.id.btn_viewpager);
        Button btn_bottom_dialog = (Button) findViewById(R.id.btn_bottom_dialog);

        btnJDPageCount.setOnClickListener(this);
        btnBottomSheet.setOnClickListener(this);
        btnWaveView.setOnClickListener(this);
        btnCheckUpdate.setOnClickListener(this);
        btnDataBinding.setOnClickListener(this);
        btnHook.setOnClickListener(this);
        btnView.setOnClickListener(this);
        btn_viewpager.setOnClickListener(this);
        btn_bottom_dialog.setOnClickListener(this);


        final FloatingActionButton button = (FloatingActionButton) findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //查看styles的属性配置(进入是渐变动画，退出时爆炸动画)
                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this).toBundle();
                ActivityCompat.startActivity(MainActivity.this, new Intent(MainActivity.this, ScrollingActivity.class), bundle);


//                int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
//                if(mode == Configuration.UI_MODE_NIGHT_YES) {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                } else if(mode == Configuration.UI_MODE_NIGHT_NO) {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                } else {
//                    // blah blah
//                }
//
//
//                startActivity(new Intent(MainActivity.this, PullToRefreshActivity.class));
            }
        });

//        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, new Pair<View, String>(null, null));
//        ActivityCompat.startActivity(this, new Intent(), options.toBundle());


    }

    protected void initView() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_jd_page_count:
                openActivity(JDPageCountActivity.class);
                break;
            case R.id.btn_bottom_sheet:
                openActivity(BottomSheetDemoActivity.class);
                break;
            case R.id.btn_wave_view:
                openActivity(WaveViewActivity.class);
                break;
            case R.id.btn_check_update:
                openActivity(UpdateActivity.class);
                break;
            case R.id.btn_data_binding:
                openActivity(DataBindingActivity.class);
                break;
            case R.id.btn_hook:
                openActivity(OtherActivity.class);
                break;
            case R.id.btn_Rx:
                openActivity(RxActivity.class);
                break;
            case R.id.btn_view:
                openActivity(ViewDemoActivity.class);
                break;
            case R.id.btn_viewpager:
                openActivity(ViewPagerActivity.class);
                break;
            case R.id.btn_bottom_dialog:
                FragmentTransaction mFragTransaction = getSupportFragmentManager().beginTransaction();
                Fragment fragment =  getSupportFragmentManager().findFragmentByTag("dialogFragment");
                if(fragment!=null){
                    //为了不重复显示dialog，在显示对话框之前移除正在显示的对话框
                    mFragTransaction.remove(fragment);
                }
                BottomSheetDialog dialogFragment = BottomSheetDialog.newInstance();
                dialogFragment.show(mFragTransaction, "dialogFragment");
                break;
        }
    }

    private void openActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
    }
}

package com.yolo.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.yolo.myapplication.bottomsheet.BottomSheetDemoActivity;
import com.yolo.myapplication.jd.JDPageCountActivity;
import com.yolo.myapplication.update.UpdateActivity;
import com.yolo.myapplication.waveview.WaveViewActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnJDPageCount = (Button) findViewById(R.id.btn_jd_page_count);
        final Button btnBottomSheet = (Button) findViewById(R.id.btn_bottom_sheet);
        Button btnWaveView = (Button) findViewById(R.id.btn_wave_view);
        Button btnCheckUpdate = (Button) findViewById(R.id.btn_check_update);

        btnJDPageCount.setOnClickListener(this);
        btnBottomSheet.setOnClickListener(this);
        btnWaveView.setOnClickListener(this);
        btnCheckUpdate.setOnClickListener(this);


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
                openActvity(JDPageCountActivity.class);
                break;
            case R.id.btn_bottom_sheet:
                openActvity(BottomSheetDemoActivity.class);
                break;
            case R.id.btn_wave_view:
                openActvity(WaveViewActivity.class);
                break;
            case R.id.btn_check_update:
                openActvity(UpdateActivity.class);
                break;
        }
    }

    private void openActvity(Class clazz) {
        startActivity(new Intent(this, clazz));
    }
}

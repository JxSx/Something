package com.yolo.myapplication.bottomsheet;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.yolo.myapplication.R;

public class BottomSheetDemoActivity extends AppCompatActivity {


    private static final int PAGE_PER = 20;//每页显示数量
    private static final int COUNT = 100;//总数量
    private BottomSheetBehavior behavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet_demo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        behavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);//默认隐藏状态

        Button btnLayout = (Button) findViewById(R.id.btn_layout);
        Button btnLayout2 = (Button) findViewById(R.id.btn_layout2);
        btnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
        });
        btnLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecycleViewFragment fragment = new RecycleViewFragment();
                fragment.show(getSupportFragmentManager(), RecycleViewFragment.TAG);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecycleViewFragment fragment = new RecycleViewFragment();
                fragment.show(getSupportFragmentManager(), "dialog");
            }
        });
    }
}

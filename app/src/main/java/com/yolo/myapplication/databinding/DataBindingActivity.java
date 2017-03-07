package com.yolo.myapplication.databinding;

import android.app.Activity;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yolo.myapplication.R;

/**
 * 需要在gradle文件中配置
 * <code>
 * dataBinding {
 *  enabled = true
 * }
 * </code>
 */
public class DataBindingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_binding);

        com.yolo.myapplication.databinding.ContentBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
        User user = new User();
        user.setName("我是名字");
        binding.setUserBean(user);

        binding.setImgUrl("http://image.uczzd.cn/14094444771339725257.png?id=0");

        binding.setHandle(this);
    }


    @BindingAdapter("image")
    public static void showImage(ImageView iv, String url) {
        Glide.with(iv.getContext()).load(url).into(iv);
    }

    //参数View必须有，必须是public，参数View不能改成对应的控件，只能是View，否则编译不通过
    public void login(View view) {
        Toast.makeText(this, "登录", Toast.LENGTH_LONG).show();
    }

}

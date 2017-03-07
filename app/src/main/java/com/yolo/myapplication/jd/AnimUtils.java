package com.yolo.myapplication.jd;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.animation.Interpolator;

/**
 * @author: jiaxin
 * @date: 2016-12-20 17:49
 */

public class AnimUtils {
    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();

    public static void hide(final View view) {
        ViewCompat.animate(view)
                .scaleX(0.0f)
                .scaleY(0.0f)
                .setInterpolator(INTERPOLATOR)
                .setDuration(300)
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                        
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        view.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(View view) {

                    }
                })
                .start();
    }

    public static void show(final View view, ViewPropertyAnimatorListener listener) {
        ViewCompat.animate(view)
                .scaleX(1.0f)
                .scaleY(1.0f)
                .setInterpolator(INTERPOLATOR)
                .setDuration(300)
                .setListener(listener)
                .start();

        view.setVisibility(View.VISIBLE);

    }
}

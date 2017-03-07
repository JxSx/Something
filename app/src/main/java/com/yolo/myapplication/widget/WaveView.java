package com.yolo.myapplication.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;

/**
 * @author: jiaxin
 * @date: 2016-12-27 15:15
 */

public class WaveView extends View {


    private ValueAnimator animator;
    private int centerY;

    public WaveView(Context context) {
        super(context);
        init(context);
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private Paint paint;
    private Path path;
    WindowManager manager;

    int measuredWidth;
    int measuredHeight;
    /**
     * 偏移量（X轴方向）
     */
    int mOffset;
    /**
     * 振幅
     */
    private static final int AMPLITUDE = 50;

    private boolean start = false;

    private void init(Context context) {
        manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAlpha(50);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        path = new Path();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (start) {
                    if (animator != null) {
                        animator.cancel();
                    }
                } else {
                    animator = ValueAnimator.ofInt(0, measuredWidth);
                    animator.setDuration(1000);
                    animator.setRepeatCount(ValueAnimator.INFINITE);
                    animator.setInterpolator(new LinearInterpolator());
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mOffset = (int) animation.getAnimatedValue();
                            postInvalidate();
                        }
                    });
                    animator.start();
                }
                start = !start;
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measuredWidth = manager.getDefaultDisplay().getWidth();
        measuredHeight = manager.getDefaultDisplay().getHeight();
        setMeasuredDimension(measuredWidth, measuredHeight);
        //计算Y轴中心点
        centerY = measuredHeight / 2;
    }


    /**
     * 本案例将屏幕的宽作为一个波长， 50是振幅
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset();
        path.moveTo(-measuredWidth + mOffset, centerY);
        //绘制屏幕左侧的一个周期的正弦波
        path.quadTo(-measuredWidth * 3 / 4 + mOffset, centerY + AMPLITUDE, -measuredWidth / 2 + mOffset, centerY);
        path.quadTo(-measuredWidth * 1 / 4 + mOffset, centerY - AMPLITUDE, 0 + mOffset, centerY);
        //绘制屏幕中的一个周期的正弦波
        path.quadTo(measuredWidth / 4 + mOffset, centerY + AMPLITUDE, measuredWidth / 2 + mOffset, centerY);
        path.quadTo(measuredWidth * 3 / 4 + mOffset, centerY - AMPLITUDE, measuredWidth + mOffset, centerY);
        path.lineTo(measuredWidth, measuredHeight);
        path.lineTo(-measuredWidth, measuredHeight);
        path.close();
        canvas.drawPath(path, paint);
    }
}

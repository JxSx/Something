package com.yolo.myapplication.waveview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.yolo.myapplication.R;


// y=Asin(ωx+φ)+k
class Wave extends View {
    private static final int LARGE = 1;
    private static final int MIDDLE = 2;
    private static final int LITTLE = 3;

    private final int WAVE_HEIGHT_LARGE = 16;
    private final int WAVE_HEIGHT_MIDDLE = 8;
    private final int WAVE_HEIGHT_LITTLE = 5;

    private final float WAVE_LENGTH_MULTIPLE_LARGE = 1.5f;
    private final float WAVE_LENGTH_MULTIPLE_MIDDLE = 1f;
    private final float WAVE_LENGTH_MULTIPLE_LITTLE = 0.5f;

    private final float WAVE_HZ_FAST = 0.13f;
    private final float WAVE_HZ_NORMAL = 0.09f;
    private final float WAVE_HZ_SLOW = 0.05f;

    public final int DEFAULT_BLOW_WAVE_ALPHA = 30;

    private final float X_SPACE = 20;
    private final double PI2 = 2 * Math.PI;

    private Path mBlowWavePath = new Path();

    private Paint mBlowWavePaint = new Paint();

    private int mBlowWaveColor = 0xFFdddddd;

    private float mWaveMultiple;
    private float mWaveLength;
    private int mWaveHeight;
    private float mMaxRight;
    private float mWaveHz;

    // wave animation
    private float mAboveOffset = 0.0f;
    private float mBlowOffset;

    private RefreshProgressRunnable mRefreshProgressRunnable;

    private int left, right, bottom;
    // ω
    private double omega;

    public Wave(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.waveViewStyle);
        final TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.WaveView, R.attr.waveViewStyle, 0);
        mBlowWaveColor = attributes.getColor(R.styleable.WaveView_blow_wave_color, 0xFFDDDDDD);
        int mWaveHeight = attributes.getInt(R.styleable.WaveView_wave_height, 1);
        int mWaveMultiple = attributes.getInt(R.styleable.WaveView_wave_length, 2);
        int mWaveHz = attributes.getInt(R.styleable.WaveView_wave_hz, 2);
        attributes.recycle();

        initializeWaveSize(mWaveMultiple, mWaveHeight, mWaveHz);
        initializePainters();
    }

    public Wave(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(mBlowWavePath, mBlowWavePaint);
    }

    public void initializeWaveSize(int waveMultiple, int waveHeight, int waveHz) {
        mWaveMultiple = getWaveMultiple(waveMultiple);
        mWaveHeight = getWaveHeight(waveHeight);
        mWaveHz = getWaveHz(waveHz);
        mBlowOffset = mWaveHeight * 0.4f;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                mWaveHeight * 2);
        setLayoutParams(params);
    }

    public void initializePainters() {
        mBlowWavePaint.setColor(mBlowWaveColor);
        mBlowWavePaint.setAlpha(DEFAULT_BLOW_WAVE_ALPHA);
        mBlowWavePaint.setStyle(Paint.Style.FILL);
        mBlowWavePaint.setAntiAlias(true);
    }

    private float getWaveMultiple(int size) {
        switch (size) {
            case LARGE:
                return WAVE_LENGTH_MULTIPLE_LARGE;
            case MIDDLE:
                return WAVE_LENGTH_MULTIPLE_MIDDLE;
            case LITTLE:
                return WAVE_LENGTH_MULTIPLE_LITTLE;
        }
        return 0;
    }

    private int getWaveHeight(int size) {
        switch (size) {
            case LARGE:
                return WAVE_HEIGHT_LARGE;
            case MIDDLE:
                return WAVE_HEIGHT_MIDDLE;
            case LITTLE:
                return WAVE_HEIGHT_LITTLE;
        }
        return 0;
    }

    private float getWaveHz(int size) {
        switch (size) {
            case LARGE:
                return WAVE_HZ_FAST;
            case MIDDLE:
                return WAVE_HZ_NORMAL;
            case LITTLE:
                return WAVE_HZ_SLOW;
        }
        return 0;
    }

    /**
     * calculate wave track
     */
    private void calculatePath() {
        mBlowWavePath.reset();

        getWaveOffset();

        float y;

        mBlowWavePath.moveTo(left, bottom);
        for (float x = 0; x <= mMaxRight; x += X_SPACE) {
            y = (float) (mWaveHeight * Math.sin(omega * x + mBlowOffset) + mWaveHeight);
            mBlowWavePath.lineTo(x, y);
        }
        mBlowWavePath.lineTo(right, bottom);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (View.GONE == visibility) {
            removeCallbacks(mRefreshProgressRunnable);
        } else {
            removeCallbacks(mRefreshProgressRunnable);
            mRefreshProgressRunnable = new RefreshProgressRunnable();
            post(mRefreshProgressRunnable);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            if (mWaveLength == 0) {
                startWave();
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mWaveLength == 0) {
            startWave();
        }
    }

    private void startWave() {
        if (getWidth() != 0) {
            int width = getWidth();
            mWaveLength = width * mWaveMultiple;
            left = getLeft();
            right = getRight();
            bottom = getBottom() + 2;
            mMaxRight = right + X_SPACE;
            omega = PI2 / mWaveLength;
        }
    }

    private void getWaveOffset() {
        if (mBlowOffset > Float.MAX_VALUE - 100) {
            mBlowOffset = 0;
        } else {
            mBlowOffset += mWaveHz;
        }

        if (mAboveOffset > Float.MAX_VALUE - 100) {
            mAboveOffset = 0;
        } else {
            mAboveOffset += mWaveHz;
        }
    }

    private class RefreshProgressRunnable implements Runnable {
        public void run() {
            synchronized (Wave.this) {
                long start = System.currentTimeMillis();

                calculatePath();

                invalidate();

                long gap = 16 - (System.currentTimeMillis() - start);
                postDelayed(this, gap < 0 ? 0 : gap);
            }
        }
    }

}

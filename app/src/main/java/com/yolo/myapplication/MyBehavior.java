package com.yolo.myapplication;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * @author: jiaxin
 * @date: 2016-12-19 16:49
 */

public class MyBehavior extends FloatingActionButton.Behavior {
    private static final String TAG = MyBehavior.class.getSimpleName();
    //我们还可以加一个加速器实现弹射效果
    private FastOutLinearInInterpolator folistener = new FastOutLinearInInterpolator();

    private int slop = 0;

    public MyBehavior(Context context, AttributeSet attr) {
        super();
        slop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    /**
     * @param parent
     * @param child      给定的View，即应用了layout_behavior的View
     * @param dependency 任何与child同级的View
     * @return 如果返回true，那么parent将做两件事：
     * 1.将忽略View的顺序，总是先去布局dependency，之后布局child。
     * 2.当dependency视图的布局或位置发生改变时，调用onDependentViewChanged方法。
     */
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        //判断dependency是否是需要的依赖项，如果是，则返回true
        return false;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        return super.onDependentViewChanged(parent, child, dependency);
    }

    /**
     * @param coordinatorLayout
     * @param child             关联Behavior的CoordinatorLayout的子View
     * @param directTargetChild CoordinatorLayout的子View或包含嵌套滚动操作的View。比如RecycleView外层的RelativeLayout
     * @param target            嵌套滚动的View
     * @param nestedScrollAxes  嵌套滚动的坐标轴。SCROLL_AXIS_HORIZONTAL, SCROLL_AXIS_VERTICAL
     * @return
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        //开始滑监听---当观察recyclerview开始发生滑动的时候回调
        //nestedScrollAxes滑动关联的方向
//        return nestedScrollAxes == ViewGroup.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
        return false;
    }

    //正在滑出来
    boolean isAnimatingOut = false;

    /**
     *
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dxConsumed 水平方向滚动增量，
     * @param dyConsumed 垂直方向滚动增量，如果大于0，手指上滑中；如果小于0,手指下滑中。
     * @param dxUnconsumed 同dyUnconsumed描述
     * @param dyUnconsumed 正常情况下，始终为0，当View处于最顶部或最底部，用户仍然强制下滑或上滑时，dy则不为0
     */
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if (dyConsumed > 0 && dyUnconsumed == 0) {
            System.out.println("上滑中。。。");
        }
        if (dyConsumed == 0 && dyUnconsumed > 0) {
            System.out.println("到边界了还在上滑。。。");
        }
        if (dyConsumed < 0 && dyUnconsumed == 0) {
            System.out.println("下滑中。。。");
        }
        if (dyConsumed == 0 && dyUnconsumed < 0) {
            System.out.println("到边界了，还在下滑。。。");
        }
        Log.i(TAG, "dxConsumed:" + dxConsumed + ">>>dyConsumed:" + dyConsumed + ">>>dxUnconsumed:" + dxUnconsumed + ">>>dyUnconsumed:" + dyUnconsumed + ">>>");
        //不断的调用
        //判断滑动的方向 dyConsumed 某个方向的增量
        if (dyConsumed > 0 && !isAnimatingOut && child.getVisibility() == View.VISIBLE && dyConsumed > slop) {
            //fab划出去
            animateOut(child);
        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE && dyConsumed < -slop) {
            //fab划进来
            animateIn(child);
        }
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }


    /**
     *
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param velocityX
     * @param velocityY
     * @param consumed
     * @return
     */
    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout,
                                 FloatingActionButton child,
                                 View target,
                                 float velocityX,
                                 float velocityY,
                                 boolean consumed) {
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }

    //滑进来
    private void animateIn(FloatingActionButton child) {
        child.setVisibility(View.VISIBLE);
        //属性动画
        ViewCompat.animate(child).translationX(0).setInterpolator(folistener).setListener(null).start();
    }

    //滑出去
    private void animateOut(FloatingActionButton child) {
        //属性动画
        //设置监听判断状态
        ViewCompat.animate(child).translationX(child.getHeight()).setInterpolator(folistener).setListener(new ViewPropertyAnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(View view) {
                isAnimatingOut = true;
                super.onAnimationStart(view);
            }

            @Override
            public void onAnimationCancel(View view) {
                isAnimatingOut = false;
                super.onAnimationCancel(view);
            }

            @Override
            public void onAnimationEnd(View view) {
                view.setVisibility(View.GONE);
                isAnimatingOut = false;
                super.onAnimationEnd(view);
            }
        }).start();
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
    }
}

package com.yolo.myapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.yolo.myapplication", appContext.getPackageName());
    }

    @Test
    public void test() {
        Observable.create(new ObservableOnSubscribe<Drawable>() {
            @Override
            public void subscribe(ObservableEmitter<Drawable> e) throws Exception {
                //根据id获取Drawable对象，回调到观察者中。
                Drawable drawable = InstrumentationRegistry.getTargetContext().getResources().getDrawable(R.drawable.ic_action_name);
                e.onNext(drawable);
                e.onComplete();
            }
        }).subscribe(new Consumer<Drawable>() {
            @Override
            public void accept(@NonNull Drawable drawable) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                System.out.println(throwable.getMessage());
            }
        });
    }
}

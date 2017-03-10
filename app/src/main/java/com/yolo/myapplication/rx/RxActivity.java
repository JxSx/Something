package com.yolo.myapplication.rx;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.yolo.myapplication.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);


        test();
//        demo();

    }

    private void demo() {
        /**
         * 创建一个观察者
         */
        Observer<String> observer = new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        /**
         * 创建一个订阅者
         */
        Subscriber<String> subscriber = new Subscriber<String>() {

            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };
        /**
         * 创建一个Observable对象，并定义事件处理规则。当它被订阅的时候，事件会按顺序依次触发。
         */
        Observable observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("Hello");
                emitter.onNext("Hi");
                emitter.onNext("Aloha");
                emitter.onComplete();
            }
        });

        Observable observable1 = Observable.just("Hello", "Hi", "Aloha");

        Observable observable2 = Observable.fromArray("Hello", "Hi", "Aloha");


        observable.subscribe(observer);
        //如下方法编译报错，没有提供与Subscriber对象关联的方法
        //observable1.subscribe(subscriber);

        Consumer onNextConsumer = new Consumer<String>() {
            @Override
            public void accept(@NonNull String o) throws Exception {

            }
        };

        Consumer onErrorConsumer = new Consumer<String>() {
            @Override
            public void accept(@NonNull String o) throws Exception {

            }
        };

        observable.subscribe(onNextConsumer, onErrorConsumer, new Action() {
            @Override
            public void run() throws Exception {

            }
        }, new Consumer<Disposable>() {
            @Override
            public void accept(@NonNull Disposable disposable) throws Exception {

            }
        });


        //将数据中的名称依次打印出来

    }

    private void testMap(){
        Observable.just("images/logo.png").map(new Function<String, Bitmap>() {
            @Override
            public Bitmap apply(@NonNull String s) throws Exception {
                return null;
            }
        });
    }

    private void test() {
        Observable.create(new ObservableOnSubscribe<Drawable>() {
            @Override
            public void subscribe(ObservableEmitter<Drawable> e) throws Exception {
                //根据id获取Drawable对象，回调到观察者中。
                Drawable drawable = getResources().getDrawable(R.drawable.ic_action_name);
                e.onNext(drawable);
                e.onComplete();
            }
        })
        .subscribeOn(Schedulers.io())//用于指定被观察者执行的线程
        .observeOn(AndroidSchedulers.mainThread())//用于执行观察者执行的线程
        .subscribe(new Consumer<Drawable>() {
            @Override
            public void accept(@NonNull Drawable drawable) throws Exception {
                ImageView imageView = (ImageView) findViewById(R.id.image);
                imageView.setImageDrawable(drawable);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                System.out.println(throwable.getMessage());
            }
        });



    }
}

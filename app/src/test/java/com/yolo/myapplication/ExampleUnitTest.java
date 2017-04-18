package com.yolo.myapplication;

import com.yolo.myapplication.databinding.Course;
import com.yolo.myapplication.databinding.User;
import com.yolo.myapplication.proxy.IShopping;
import com.yolo.myapplication.proxy.ShoppingImpl;
import com.yolo.myapplication.proxy.ShoppingProxy;

import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    private static final String TAG = ExampleUnitTest.class.getSimpleName();

    @Test
    public void testOkHttp() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
                Request request = new Request.Builder()
                        .url("http://www.baidu.com")
                        .build();


                OkHttpClient client = new OkHttpClient();

                try {
                    client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
//            }
//        }).start();
    }


    @Test
    public void testProxy() {
        ShoppingImpl impl = new ShoppingImpl();
        ShoppingProxy proxy = new ShoppingProxy(impl);

        proxy.buy("");


        IShopping iShopping = (IShopping) Proxy.newProxyInstance(ShoppingImpl.class.getClassLoader(),
                impl.getClass().getInterfaces(),
                new ShoppingHandler(impl));
        iShopping.buy("abc");
    }

    class ShoppingHandler implements InvocationHandler {

        IShopping base;

        public ShoppingHandler(IShopping base) {
            this.base = base;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return method.invoke(base, args);
        }
    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);

        byte b = 1;
        byte[] a = new byte[2];
    }

    @Test
    public void testFlatMap() {
        List<User> users = new ArrayList<>();
        List<Course> courses = new ArrayList<>();
        Course course = new Course();
        course.setName("语文");
        courses.add(course);

        final User user = new User();
        user.setAge(10);
        user.setName("abc");
        user.setGirl(true);
        user.setCourses(courses);
        users.add(user);


        Observable.fromArray(users).subscribe(new Consumer<List<User>>() {
            @Override
            public void accept(@NonNull List<User> users) throws Exception {

            }
        });
    }

    @Test
    public void testRx1() {
        Flowable.fromArray("a").subscribe(new Subscriber<String>() {
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
        });


        Observable.fromArray("this", "is", "a", "sentence")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        System.out.println(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        System.out.println(throwable.getMessage());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println("run");
                    }
                }, new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        System.out.println("disposable");
                    }
                });
    }

    @Test
    public void testRx() {
        final String[] names = {"Jason", "Bob", "Coco"};
        Observable.fromArray(names).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                System.out.println("name:" + s);
            }
        });


        Observable.fromArray("this", "is", "a", "sentence")
                .map(new Function<String, String>() {
                    @Override
                    public String apply(@NonNull String s) throws Exception {
                        System.out.println(TAG + ">>>" + s);
                        return s.toUpperCase() + " ";
                    }
                })
                .toList()
                .map(new Function<List<String>, String>() {
                    @Override
                    public String apply(@NonNull List<String> strings) throws Exception {
                        Collections.reverse(strings);
                        return strings.toString();
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        System.out.println(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                });
    }
}
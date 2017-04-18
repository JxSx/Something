package com.yolo.myapplication.hook;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: jiaxin
 * @date: 2017-03-07 16:22
 */

public class HookUtil {

    private static final String TAG = "HookUtil";

    private Context context;
    private Class<?> proxyActivity;
    /**
     * 进程中入口唯一的ActivityThread对象
     */
    private Object activityThreadValue;

    public HookUtil(Context context, Class<?> proxyActivity) {
        this.context = context;
        this.proxyActivity = proxyActivity;
    }

    public void hookAms() throws Exception {
        Class AMN = Class.forName("android.app.ActivityManagerNative");
        Field gDefaultField = AMN.getDeclaredField("gDefault");//获取类中的属性字段
        gDefaultField.setAccessible(true);//关闭安全检查就可以达到提升反射速度的目的
        Object gDefaultValue = gDefaultField.get(null);//获取属性值，静态变量，传递参数为null

        Class singleton = Class.forName("android.util.Singleton");
        Field mInstanceField = singleton.getDeclaredField("mInstance");
        mInstanceField.setAccessible(true);
        Object iActivityManagerObj = mInstanceField.get(gDefaultValue);//gDefaultValue就是Singleton的实例化对象

        //动态代理Proxy.newProxyInstance()
        //将IActivityManger被代理，hook它

        Class iActivityManagerIntercept = Class.forName("android.app.IActivityManager");

        AmsInvocationHandler handler = new AmsInvocationHandler(iActivityManagerObj);

        Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{iActivityManagerIntercept}, handler);

        mInstanceField.set(gDefaultValue, proxy);

        ValueAnimator.ofInt().addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
            }
        });
    }

    class AmsInvocationHandler implements InvocationHandler {
        /**
         * 被代理的对象
         */
        Object iActivityManagerObj;

        public AmsInvocationHandler(Object iActivityManagerObj) {
            this.iActivityManagerObj = iActivityManagerObj;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Log.i(TAG, "methodName:" + method.getName());
            if ("startActivity".contains(method.getName())) {
                Intent intent = null;
                int index = 0;

                for (int i = 0; i < args.length; i++) {
                    if (args[i] instanceof Intent) {
                        intent = (Intent)args[i];
                        index = i;
                        break;
                    }
                }

                Intent proxyIntent = new Intent(context, proxyActivity);
                proxyIntent.putExtra("oldIntent", intent);
                args[index] = proxyIntent;
                return method.invoke(iActivityManagerObj, args);
            }
            return method.invoke(iActivityManagerObj, args);
        }
    }


    public void hookActivityThreadHandler() throws Exception {
        Class activityThread = Class.forName("android.app.ActivityThread");
        Field currentActivityThreadField = activityThread.getDeclaredField("sCurrentActivityThread");//获取类中的属性字段
        currentActivityThreadField.setAccessible(true);//关闭安全检查就可以达到提升反射速度的目的
        //获取属性值，静态变量，传递参数为null
        activityThreadValue = currentActivityThreadField.get(null);

        Field handlerField = activityThread.getDeclaredField("mH");
        handlerField.setAccessible(true);
        Handler handlerValue = (Handler) handlerField.get(activityThreadValue);//mH的变量值

        Field callbackField = Handler.class.getDeclaredField("mCallback");
        callbackField.setAccessible(true);
        callbackField.set(handlerValue, new MyCallback(handlerValue));

    }

    class MyCallback implements Handler.Callback{

        Handler handler;

        public MyCallback(Handler handler) {
            this.handler = handler;
        }

        @Override
        public boolean handleMessage(Message msg) {
            Log.i(TAG, "Mycallback");

            if(msg.what == 100){
                handleLaunchActivity(msg);
            }
            handler.handleMessage(msg);
            return true;
        }

        private void handleLaunchActivity(Message msg) {
            Object obj = msg.obj;//AcitivtyClientRecord;

            try {
                Field intentField = obj.getClass().getDeclaredField("intent");
                intentField.setAccessible(true);

                Intent proxyIntent = (Intent) intentField.get(obj);
                Intent realIntent = proxyIntent.getParcelableExtra("oldIntent");
                if(realIntent != null){
                    proxyIntent.setComponent(realIntent.getComponent());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}

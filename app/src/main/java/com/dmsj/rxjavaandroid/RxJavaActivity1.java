package com.dmsj.rxjavaandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

public class RxJavaActivity1 extends AppCompatActivity {

    public  String TAG = "RxJavaActivity1";
    private Subscriber<String> subscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java1);
    }

    /**
     * 首先理解两个元素Observable 为可观察者或者被观察者
     *  Observer 为观察者/   Subscriber订阅者
     *  创建一个被观察者
     *  创建一个观察者,在被观察中调用三个方法,来通知观察者
     *  首先onNext 可以被多次调用,onCompleted调用成功之后,如果中间异常报错之后,会自动回调onError,
     *      成功和失败只能任选其一,
     * @param v
     */
   public void test(View v){
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                //可以做各种复杂的操作,然后进行回调
                subscriber.onNext("jack : 我是最后的执行结果");
                subscriber.onNext("jack : 我被计算出来了");
                subscriber.onCompleted();
            }
        }).subscribe( //此行为订阅,只有真正的被订阅,整个流程才算生效
                new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG,"onCompleted");
            }
            @Override
            public void onError(Throwable e) {
                Log.d(TAG,e.toString());
            }
            @Override
            public void onNext(String s) {
                Log.d(TAG,s);
            }
        });
    }

    /**
     * Observable中有很多操作符,下面进行列举了两个方法
     *
     * Observable.from() 传入数组,集合
     * Observable.just() 传入多个对象,泛型,可以传入多个或者一个
     * 还有一些
     * map 转化对象
     * flatmap 平铺对象
     * filter 过滤
     * distinct 去重复
     * take 从开始取出固定个数
     * doOnNext 输出元素之前的额外操作
     * toList 打包对象为集合
     *
     *
     * 最后都会调用onNext方法进行输出
     * 这就是所谓的RxJava变形第一步
     * @param v
     */
    public void test1(View v){

        Observable.just("我是jack","我是jacktang").subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG,"onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,e.toString());
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG,s);
            }
        });


        List<String>  lists =new ArrayList<>();
        lists.add("from ==jack");
        lists.add("from ==jack1");
        lists.add("from ==jack2");
        Observable.from(lists).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG,"onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,"Throwable");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG,s);
            }
        });

    }

    /**
     * 再来了解几个简单的回调,拿just为例
     * 倘若我们只要获取单个结果,选择回调某个方法,
     * 如果什么方法都不要的话,subscribe()相当于内部自动创建一个默认的ActionSubscriber
     * subscribe()
     * subscribe(final Action1<? super T> onNext)
     * subscribe(final Action1<? super T> onNext, final Action1<Throwable> onError)
     * subscribe(final Action1<? super T> onNext, final Action1<Throwable> onError, final Action0 onCompleted)
     *
     * @param v
     */
    public void test2(View v){
        //相当于 调用了 onNext, onError, onCompleted
        Observable.just("我是jack","我是jacktang").subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        });

        //相当于 调用了 onNext, onError
        Observable.just("我是jack","我是jacktang").subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });

        //相当于 调用了 onNext, onError,onCompleted
        Observable.just("我是jack","我是jacktang").subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        }, new Action0() {
            @Override
            public void call() {

            }
        });

    }

    /**
     *  Observer 为观察者/   Subscriber订阅者
     *
     *  Subscriber实现了Observer,增加了
     *  onStart()一般用于在事件处理前调用
     *  unsubscribe(),取消订阅,一般在activity的onstop() 或者onDestory()中调用,常用写法
     *        if(subscriber!=null && !subscriber.isUnsubscribed())
                subscriber.unsubscribe();
     *
     * isUnsubscribed() 判断是否已经取消订阅
     *
     *
     * @param view
     */
    public void test3(View view){
        subscriber = new Subscriber<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.d(TAG, "onStart");
            }

            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "Throwable");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, s);
            }
        };

        Observable.just("我是jack","我是jacktang").subscribe();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(subscriber!=null && !subscriber.isUnsubscribed())
            subscriber.unsubscribe();
    }





}

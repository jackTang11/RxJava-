package com.dmsj.rxjavaandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ScheduleActivity extends AppCompatActivity {

    /**
     * 倘若我们要进行一些数据的耗时操作,而且线程切换自由
     * 在RxJava 中，Scheduler ——调度器，相当于线程控制器，
     *   RxJava 通过它来指定每一段代码应该运行在什么样的线程。RxJava 已经内置了几个 Scheduler ，它们已经适合大多数的使用场景：
     Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。
     Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。
     Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。行为模式和 newThread() 差不多，区别在于 io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io() 中，可以避免创建不必要的线程。
     Schedulers.computation(): 计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，例如图形的计算。这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。
     另外， Android 还有一个专用的 AndroidSchedulers.mainThread()，它指定的操作将在 Android 主线程运行。
     有了这几个 Scheduler ，就可以使用 subscribeOn() 和 observeOn() 两个方法来对线程进行控制了。
     * subscribeOn(): 指定 subscribe() 所发生的线程，即 Observable.OnSubscribe 被激活时所处的线程。或者叫做事件产生的线程。
     * observeOn(): 指定 Subscriber 所运行在的线程。或者叫做事件消费的线程。
     * @param v
     */

    List<Student> list = new ArrayList<>();
    private JSONArray array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        array = new JSONArray();
        try {
            for (int i = 0; i < 200; i++) {
                JSONObject object = new JSONObject();
                object.put("name", "jack" + i);
                array.put(object);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public void action(View v) {
        Observable.just(array)
                .map(new Func1<JSONArray, List<Student>>() {
            @Override
            public List<Student> call(JSONArray array) {
                Log.d("我在子线程解析(map)  当前线程",Thread.currentThread().getName());
                List list =new ArrayList();
                for (int i = 0; i <array.length() ; i++) {
                   Student st = Student.parseTo(array.optJSONObject(i));
                    list.add(st);
                }
                return list;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Student>>() {
            @Override
            public void call(List<Student> students) {
                Log.d("我是主线程更新UI  当前线程",Thread.currentThread().getName());
            }
        });
    }



}

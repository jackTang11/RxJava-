package com.dmsj.rxjavaandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 操作符
 * 操作符在基本概念中也讲到过一些,现在具体的说说操作符之间的变换
 */
public class RxJavaOperRater extends AppCompatActivity {

    List<Student> students =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_oper_rater);
       Student student = new Student("tom","17",65);
        students.add(new Student("jack","15",80));
        students.add(new Student("jim","13",100));
        students.add(new Student("lucy","16",80));
        students.add(new Student("tom","17",65));
        students.add(new Student("tom","17",65));
        students.add(new Student("jim","13",100));
        students.add(new Student("lucy","19",80));
    }


    /**
     * 简单的变换
     * json 类型转换为 Student
     * 在开发中我们可以将 网络请求的json 数据转换为Bean操作
     */
    public void test(View v) throws JSONException {
        JSONObject json =new JSONObject();
        json.put("name","jack");
        json.put("age","18");
        json.put("scro",80);

        Observable.just(json).
                map(new Func1<JSONObject, Student>() {//参数1 表示入参(变化前)  参数2 表示出参(变化后)
            @Override
            public Student call(JSONObject json) {
               Student student =   Student.parseTo(json);
                return student;
            }
        }).subscribe(new Action1<Student>() {
            @Override
            public void call(Student s) {
                Log.d("RxJavaOperRater",s.toString());
            }
        });
    }


    /**
     *  map 转化对象
     * flatmap 平铺对象
     * filter 过滤
     * distinct 去重复
     * take 从开始取出固定个数
     * doOnNext 输出元素之前的额外操作
     * toList 打包对象为集合
     */

    /**
     * 下面就慢慢的结和操作符 复杂起来
     * 下面操作要求
     *          1.过滤所有name为lucy的学生
     *          2.可以指定去重复条件
     *          3.取出最后2个
     * @param v
     */
    public void test1(View v){
        Observable.from(students).
                filter(new Func1<Student, Boolean>() {//filter过滤条件为 name =tom
            @Override
            public Boolean call(Student student) {
                return !student.name.equals("lucy");
            }
        })
        .distinct()//选择重复条件 ,distinct() 默认情况为去掉对象重复的,
                .takeLast(2).//取出两个
                subscribe(new Action1<Student>() {
            @Override
            public void call(Student student) {
                Log.d("RxJavaOperRater",student.toString());
            }
        });
    }
}

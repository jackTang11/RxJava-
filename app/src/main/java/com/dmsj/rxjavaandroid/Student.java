package com.dmsj.rxjavaandroid;

import org.json.JSONObject;

/**
 * Created by jack_tang on 16/5/14.
 */
public class Student {

    public String name;
    public String age;
    public int score;

    public Student(String name, String age, int score) {
        this.name = name;
        this.age = age;
        this.score = score;
    }

    public static Student parseTo(JSONObject json) {
        Student student =new Student(json.optString("name"),
                json.optString("age"),
                json.optInt("scro")
                );

        return student;
    }

    @Override
    public String toString() {
        return "name =" +name+",age ="+age+",score ="+score;
    }
}

package com.dmsj.rxjavaandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void action1(View v){
        startActivity(new Intent(this,RxJavaActivity1.class));
    }

    public void action2(View v){
        startActivity(new Intent(this,RxJavaOperRater.class));
    }

    public void action3(View v){
        startActivity(new Intent(this,ScheduleActivity.class));
    }





}

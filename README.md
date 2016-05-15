# RxJava-demo
RxJava 是什么?解决什么问题

我们回想一下,在android 中通常做异步耗时的操作,然后去通知UI线程进行更新.无非涉及到两个点,回调 or Handler.
例如:
    Handler handler =new Handler(){
        public void handMessge(Message msg){
            //更新UI
        }
    }

    new Thread(){
        void run(){
            //耗时请求
            handler.sendMessage();
        }
    }.start();

 简单来说RxJava是一个可以运行在Java VM上的库,是一个可观测的异步操作,基于事件的程序,响应式编程
        可观测: 大话来讲,就是一目了然.
     解决的问题
        让复杂的程序逻辑,变得更加客观清晰

首先理解两个元素
Observable 为可观察者或者被观察者
Observer 为观察者/   Subscriber订阅者
在android开发中
Observable 可以是一个网络请求,Subscriber来显示请求结果
Observable 可以是一个数据查询,Subscriber来显示查询结果
Observable 可以是一个按钮的点击事件,Subscriber来响应点击事件
Observable 可以是一个图片文件的加载解析,Subscriber可以用来展示处理之后的图片

RxJava中最牛X的东西就属于 操作符和线程切换

例子和详解请看demo

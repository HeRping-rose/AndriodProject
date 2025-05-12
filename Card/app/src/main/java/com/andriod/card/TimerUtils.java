package com.andriod.card;
//定时器封装类
//属性：
//时间time int  顺序order:CountOrder ---》CountOrder枚举类型 (内部类innerClass：在某一个类在内部声明一个类) ：ASCENDING  DESCENDING
//定时器对象timer：Timer
//数据回调的对象  listener Object

import android.os.Handler;
import android.os.Looper;

import java.util.Timer;
import java.util.TimerTask;

//方法
// constructor(time,order,listener)
// 开始计时start() ，暂停计时stop() ,取消 cancel()
public class TimerUtils {
    private Order mOrder;//顺序
    private int mTime;//时间
    private long mDuration=1000L;//间隔时间
    private Timer mTimer;//定时器对象
    private TimerListener mListener;//事件监听着对象


    public TimerUtils(int mTime, Order mOrder, TimerListener mListener) {
        this.mTime = mTime;
        this.mListener = mListener;
        this.mOrder = mOrder;
    }
    public void setmDuration(Long duration){
        mDuration=duration;
    }
    public void start(){
        mTimer=new Timer();

        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
//                根据顺序修改mTime的值
                if(mOrder==Order.ASCENDING){
                    mTime++;
                }else {
                    mTime--;
                }
//                将当前的值回调给监听者  子线程中执行
//                MainActivity activity=(MainActivity)mListener;

//                new Handler(Looper.getMainLooper()).post(()->{
//                    mListener.valueChanged(mTime);
//                });
                //切换到UI线程
                new Handler(Looper.getMainLooper()).post(()->{
                    mListener.valueChanged(mTime);
                });
//                mListener.valueChanged(mTime);//切换到UI线程
//                activity.runOnUiThread(()->{
//                    activity.timeChanged(mTime);
//                });


            }
        },mDuration,mDuration);
        mListener.TimerStarted();
    }
//    暂停计时器
    public void stop(){
        mTimer.cancel();
        mListener.TimerCanceled();
    }
//    取消计时器
    public void cancel(){
        mTimer.cancel();
        mListener.TimerCanceled();
    }

    int time;
    private void add(){

    }
    public void test(){
        TimerListenerImpl impl =new TimerListenerImpl();//方法一：显示创建实现类

//        创建匿名类对象；；匿名类实现接口  --- 如果需要创建接口的实现类对象 ，而自己又不想创建实现类，这个时候就可以使用匿名类
//        注意，这种只能使用一次 如果要实现多个创建用方法一
        TimerListener timerListener=new TimerListener() {
            @Override
            public void valueChanged(int value) {

            }

            @Override
            public void TimerStarted() {

            }

            @Override
            public void TimerCanceled() {

            }
        };
    }
//    class Inner{
//        public void test(){
//            time=10;
//            add();
//        }
//    }
//    静态内部类不能访问外部成员的成员属性和方法  成员属性和成员方法必须要有通过类名直接访问
//    访问静态内部类是通过类名的属性和方法直接访问
//    没有外部类对象
//    time =2; // 静态内部类不能访问外部成员的成员属性和方法
//    static class StaticInner{
//        public void test(){}
//    }


//    private static final int ASCENDING = 0;
//    private static final int DESCENDING = 1;
//    Order order;//不区分静态or动态
    enum Order{
        ASCENDING,DESCENDING
    }

    //自己定义一套方法、、定义一套规则 用于实现数据回调
//    这些方法不需要自己实现，使用者去实现； ------接口interface是特殊的抽象类
//    只声明不实现 ：使用接口或者是抽象类
    interface TimerListener{
        //将当前的计数值回调出去
        void valueChanged(int value);
//        告诉你开始了
        void TimerStarted();
//        告诉你结束了
        void TimerCanceled();
    }

//    接口不能被实例化
//    1.定义接口
//    2.定义接口实现类
//    3.创建实现类对象  只能实例化接口的实现类   自己显示创建一个接口的实现类

//    第二种 使用匿名类对象

    class TimerListenerImpl implements TimerListener{
        @Override
        public void valueChanged(int value) {

        }

        @Override
        public void TimerStarted() {

        }

        @Override
        public void TimerCanceled() {

        }
    }
}

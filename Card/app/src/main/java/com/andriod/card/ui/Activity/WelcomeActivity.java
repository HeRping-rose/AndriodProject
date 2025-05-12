package com.andriod.card.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.andriod.card.R;
import com.andriod.card.databinding.ActivityMainBinding;
import com.andriod.card.utils.SystemUtils;
import com.andriod.card.utils.TimerUtils;

public class WelcomeActivity extends AppCompatActivity implements TimerUtils.TimerListener{
    //定义一个属性变量，，，类型就是绑定类的类型
    private ActivityMainBinding mBinlding;
    TimerUtils timerUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

//        创建绑定类对象对象  如何手动解析一个layout文件
        mBinlding = ActivityMainBinding.inflate(getLayoutInflater());
//        MainActivity--->AppCompatActivity--->.....--->Context
        LayoutInflater inflater =LayoutInflater.from(this);
        View view =inflater.inflate(R.layout.activity_main,null,false);


//        setContentView(R.layout.activity_main);//绑定界面管理界面显示
//        setContentView(view);
//将绑定类中的root和Activity关联  不再使用资源ID中的getRoot（）；
        setContentView(mBinlding.getRoot());





        mBinlding.startContainer.setOnClickListener(v->{
//            float density = this.getResources().getDisplayMetrics().density;//获取屏幕密度,,this.可以省略
            ViewGroup.LayoutParams layoutParams=mBinlding.startContainer.getLayoutParams();
//            float density = getResources().getDisplayMetrics().density;//获取屏幕密度、、、方法封装起来在SystemUti工具中
//
//            layoutParams.width=(int)(100*density);//改变width值
//            layoutParams.width =mBinlding.startContainer.getLayerType();
            layoutParams.width= SystemUtils.dp2px(200,this);//改变width值
            mBinlding.startContainer.setLayoutParams(layoutParams);//设置回去显示出来
        });

//        使用事件

//        initEvent();


    }

    @Override
    protected void onResume() {
        super.onResume();
        timerUtils =new TimerUtils(2,TimerUtils.Order.DESCENDING,this);
        timerUtils.start();
    }

    //    添加事件
    public void initEvent(){
        mBinlding.startContainer.setOnClickListener(v->{
//            mBinlding.countDown.setText("2");

            timerUtils =new TimerUtils(2,TimerUtils.Order.DESCENDING,this);
            timerUtils.start();
        });





//        定时器测试
//        TimerUtils utils =new TimerUtils();
//        utils.time=10;
//
//        utils.order=TimerUtils.Order.ASCENDING;
//
//        TimerUtils.Inner inner1 =new TimerUtils().new Inner();
//        inner1.test();
//        TimerUtils.Inner inner2 =utils.new Inner();
//
//        TimerUtils.StaticInner staticInner=new  TimerUtils.StaticInner();
//        staticInner.test();
    }
    public void  timeChanged(int time){
        if(time == 0){
            timerUtils.cancel();
        }else {
            mBinlding.countDown.setText(time + "");
        }//将计时器显示在页面
    }

    @Override
    public void valueChanged(int value) {
        if(value==0){
            timerUtils.cancel();
//            实现界面跳转 ：应用内   应用外部跳转action隐式跳转
            Intent intent = new Intent(this, LoginRegisterActivity.class);//字节码 反射注解实现
            startActivity(intent);
//判断版本
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                overridePendingTransition(OVERRIDE_TRANSITION_CLOSE,R.anim.exit_anim,R.anim.exit_anim);//跳转动画效果
//            }else{
            overridePendingTransition(R.anim.enter_anim,R.anim.exit_anim);
//            }
        }else {

            mBinlding.countDown.setText(value+"");
        }

    }

    @Override
    public void TimerStarted() {

    }

    @Override
    public void TimerCanceled() {

    }
}
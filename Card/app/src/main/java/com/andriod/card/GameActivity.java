package com.andriod.card;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.andriod.card.databinding.ActivityGameBinding;
import com.andriod.card.model.CardModel;
import com.andriod.card.model.GameCenter;
import com.andriod.card.utils.AnimatorUtils;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements GameCenter.OnMoneyChangeListener{

    private ActivityGameBinding mbinding;
    private GameCenter gameCenter = new GameCenter();
    private boolean isFirst=true;//判断是否为第一个数的标志
    private float pokerStartX=0f;//纸牌开始的X坐标

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //数据回调
//        gameCenter.setOnMoneyChangeListener(new GameCenter.OnMoneyChangeListener() {
//            @Override
//            public void tableMoneyChanged(int money) {
//                mbinding.tvPot.setText(money+"");
//            }
//        });//方法三 匿名类对象
        gameCenter.setOnMoneyChangeListener(money -> {
            mbinding.tvPot.setText(money+"");


//            将游戏结束时间传递给GameActivity

            
        });//方法四匿名朗母达表达式lambda  前提是这个接口中只有一个方法才可以用

//        gameCenter.setOnMoneyChangeListener(this);//方法二  当前这个类实现接口
//        gameCenter.setOnMoneyChangeListener(new MoneyChangeImpl());//方法一  定义接口实现类
        //关联界面
//        setContentView();

        //监听游戏结束
        gameCenter.setOnGameOverListener(()->{
            AnimatorUtils.fadeOut(mbinding.ActContainer);
            AnimatorUtils.fadeOut(mbinding.betContainer);
            mbinding.btn.setText("PlayAgain");
            AnimatorUtils.fadeIn(mbinding.btn);

            isFirst=false;//游戏结束的时候重置游戏开始的是否为第一次的标志


        });
        mbinding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(mbinding.getRoot());

        ArrayList<View> views = new ArrayList<>();
        views.add(mbinding.player1container.getRoot());
        views.add(mbinding.player2container.getRoot());


        //修改CardModel参数类型 每一层都要修改
        ArrayList<CardModel> cardViews = new ArrayList<>();
        cardViews.add(new CardModel(mbinding.player1card,mbinding.ivWinner1));
        cardViews.add(new CardModel(mbinding.player2card,mbinding.ivWinner2));

        gameCenter.init(views,cardViews);//修改为cardmodel

        initEvent();
    }

    private void initEvent() {
        mbinding.btn.setOnClickListener(v -> {
            //判断是否为第一次
            if (isFirst == false) {
                //当点击再来一次的时候，需要重置游戏状态以及相关参数
                gameCenter.resetGame();
            }else {
                //记录中心扑克牌的位置，方便重置的时候的动画效果与开始一致
                pokerStartX=mbinding.player1card.getX();

                //显示扑克
                AnimatorUtils.fadeIn(mbinding.player1card);
                AnimatorUtils.fadeIn(mbinding.player2card);
                //显示玩家
                AnimatorUtils.fadeIn(mbinding.player1container.getRoot());
                AnimatorUtils.fadeIn(mbinding.player2container.getRoot());
                //显示筹码照片
                AnimatorUtils.fadeIn(mbinding.imageView);

            }

            //属性动画
            AnimatorUtils.fadeOut(mbinding.btn);//按钮淡入淡出效果
            //扑克牌移动动画从中心playcard位置到placeHolder位置
            AnimatorUtils.translateX(mbinding.player1card, pokerStartX, mbinding.player1placeHolder.getX());
            AnimatorUtils.translateX(mbinding.player2card, pokerStartX, mbinding.player2placeHolder.getX());

            AnimatorUtils.fadeIn(mbinding.ActContainer);
            AnimatorUtils.fadeIn(mbinding.betContainer);
            gameCenter.start();

        });

        //弃牌按钮事件
        mbinding.ivGiveUP.setOnClickListener(v->{
            gameCenter.giveUP();
        });
        mbinding.ivAdd.setOnClickListener(v->{
            gameCenter.betting(50);
        });

        mbinding.ivPK.setOnClickListener(v->{
            gameCenter.pk();

        });

//        mbinding.btn.setEnabled(false);
    }

    @Override
    public void tableMoneyChanged(int money) {
        mbinding.tvPot.setText(money+"");
    }

    class MoneyChangeImpl implements GameCenter.OnMoneyChangeListener{
        public void tableMoneyChanged(int money) {
            mbinding.tvPot.setText(money+"");
        }
    }
}




//        mbinding.btn.setOnClickListener(v->{


//            getResources().getAnimation()
//            Animation animation1 =AnimationUtils.loadAnimation(this,R.anim.alpha_exit);
//            AlphaAnimation animation2 = (AlphaAnimation) AnimationUtils.loadAnimation(this, R.anim.alpha_exit);
//            TranslateAnimation animation3 = (TranslateAnimation) AnimationUtils.loadAnimation(this, R.anim.shake);//摇摆动画
//            animation3.setInterpolator(new BounceInterpolator());//回弹效果
//            animation3.setInterpolator(new LinearInterpolator());//匀速效果
//            animation3.setInterpolator(new AccelerateInterpolator());//持续加速效果
//            animation3.setInterpolator(new AccelerateDecelerateInterpolator());//持续加速效果
//            animation3.setInterpolator(new AnticipateInterpolator());//先反向蓄力再冲刺
//            animation3.setInterpolator(new FastOutSlowInInterpolator());//快出慢进
//
//            mbinding.btn.startAnimation(animation3);//给控件添加动画，开启动画

//手动创建动画效果
//            RotateAnimation animation=new RotateAnimation(0,360);
//            animation.setDuration(500);
//            animation.setFillAfter(true);
/// /            animation.setRepeatCount(2);
//            animation.setRepeatCount(Animation.INFINITE);
//            animation.setRepeatMode(Animation.RESTART);
//            mbinding.btn.startAnimation(animation);
//        });

/**
 * 难点1 类与对象抽离
 * 1.找参与任务的对象
 * 玩家123
 * 玩家管理器
 * <p>
 * 扑克牌
 * 扑克牌管理器
 * <p>
 * 玩家中心
 * <p>
 * 2.确定属性和方法
 * 2.1 玩家属性:姓名     头像      筹码    状态      扑克
 * tvName ivHeader tvMoney ivArrow breathView(呼吸灯)
 * 玩家方法:弃牌 看牌 下注 PK
 * <p>
 * 2.2 玩家管理器
 * 玩家管理器属性:玩家数组 记录当前玩家正在操作的玩家编号
 * 玩家管理器方法:获取当前玩家 获取下一个玩家
 * <p>
 * 2.3 扑克牌
 * 扑克牌属性: 花色 点数
 * <p>
 * 扑克牌管理器: 扑克牌数组
 * 方法:生成一副扑克牌 随机获取一张扑克牌
 * <p>
 * 2.5 游戏中心
 * 属性: 当前底注金额 每局底注金额
 * <p>
 * 方法: 开始游戏 结束游戏
 * <p>
 * 难点2 确定玩家 信息以及状态就是那个玩家呢?
 * 玩家 界面复用
 */
package com.andriod.card.model;

import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.andriod.card.R;
import com.andriod.card.utils.AnimatorUtils;

public class Player {
    private int headResID; //头像资源id
    private String name; //姓名
    private int money; //筹码
    private Poker poker;//玩家的牌
    private View mView;//玩家的信息视图
    private View breathView;//呼吸灯视图
    private ImageView headView;//玩家的信息视图
    private ImageView arrowView;//玩家的信息视图
    private TextView moneyView;//玩家的信息视图
    private TextView nameView;//玩家的信息视图

    private ImageView cardView;//玩家的扑克视图
    private ImageView winnerView;//玩家的扑克视图

    private PlayerState state = PlayerState.IDLE;  //默认为空闲状态

    public Player(int headResID, String name, int money) {
        this.headResID = headResID;
        this.name = name;
        this.money = money;
    }

    //修改玩家状态
    public void changePlayerState(PlayerState state){
        this.state=state;
        bindDate();//重新刷新界面
    }

    //扣除底注金额
    public int deductMoney(int count) {
        int temp=0;
        if(money>=count){
            money -= count;
            temp=count;
        }else {
            temp=money;
            money=0;
        }
        moneyView.setText(money + "");
        return temp;
    }

    //win之后加钱
    public void addMoney(int totalMoney) {
        this.money += totalMoney;
        moneyView.setText(money+"");
        AnimatorUtils.fadeIn(winnerView);//修改显示赢家WinnerView
    }
    //视图绑定  将玩家视图转换为cardmodel
    public void bindView(View view) {
        //传递-->解析-->找到子视图(通过id)-->bindDate()绑定数据(根据状态  枚举PlayerState)
        this.mView = view;
        breathView = mView.findViewById(R.id.breathView);
        headView = mView.findViewById(R.id.headView);
        arrowView = mView.findViewById(R.id.arrowView);
        moneyView = mView.findViewById(R.id.moneyView);
        nameView = mView.findViewById(R.id.nameView);

        bindDate();
    }

    //绑定数据
    private void bindDate() {
        if (state == PlayerState.IDLE || state == PlayerState.GIVE_UP) {
            breathView.setBackgroundResource(R.drawable.head_normal_circle_shape);
            arrowView.setBackgroundResource(R.drawable.arrow_gray);
            closeBreathAnimation();

        } else {
            breathView.setBackgroundResource(R.drawable.head_active_circle_shape);
            arrowView.setBackgroundResource(R.drawable.arrow_green);
            //呼吸灯动画效果
            AnimatorUtils.scaleInOut(headView,1.0f,0.95f);
        }
        headView.setImageResource(headResID);
        nameView.setText(name);
        moneyView.setText(money + "");
    }

    //关闭呼吸灯动画事件处理
    public void closeBreathAnimation() {
        ScaleAnimation scaleAnimation=(ScaleAnimation) headView.getAnimation();
        if (scaleAnimation != null) {
            scaleAnimation.cancel();
        }
    }

    //Card视图绑定
    public void bindCardView(CardModel cardModel) {
        cardView = cardModel.cardView;
        winnerView = cardModel.winnerView;
        //视图绑定
        cardView.setOnClickListener(v -> {
            //点击纸牌事件实现翻转效果
            poker.changeSate();
            //翻转动画
            flipCard();
        });

    }

    public void dealCard(Poker poker) {
        this.poker = poker;
        this.cardView.setImageResource(this.poker.getPokerRes());
    }

    //显示扑克正面
    public void showPoker() {
        poker.changeToFrontState();//将扑克翻转到正面
        flipCard();//切换动画
    }
    //翻转动画
    public void flipCard() {
        AnimatorUtils.rotateY(cardView, 0f, 90f);
        this.cardView.setImageResource(this.poker.getPokerRes());
        AnimatorUtils.rotateY(cardView, 270f, 360f);
    }

    //是否为弃牌状态
    public boolean isGiveUp(){
        return state==PlayerState.GIVE_UP;
    }
    //是否为激活状态
    public boolean isActive(){
        return state!=PlayerState.GIVE_UP;
    }


    //重置扑克状态
    public void resetPokerState() {
        AnimatorUtils.fadeOut(winnerView);
        if (poker != null) {
            poker.changeSate();
            flipCard();
        }
    }

    public int pk(Player otherPlayer) {
        return poker.compareTo(otherPlayer.poker);  //返回1和-1,胜利/失败
    }

    public enum PlayerState {
        IDLE,//空闲状态
        ACTIVE,//激活状态
        GIVE_UP//弃牌状态
    }
}

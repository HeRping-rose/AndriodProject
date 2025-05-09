package com.andriod.card.model;

//管理一张牌
//花色  点数
//让花色与点数 与 图片资源建立联系 使用二维数组

import androidx.annotation.NonNull;

import com.andriod.card.R;
import com.andriod.card.utils.Constains;

public class Poker {
    private int suit = 0;//花色 0-3
    private int point = 0;//点数 0-12

    private int pokerRes = 0;

    private int pokerBackRes = R.drawable.shirt_blue;
    private PokerState pokerState = PokerState.BACK;

    public Poker(int suit, int point) {
        this.suit = suit;
        this.point = point;
        this.pokerRes = Constains.pokerResArray[suit][point];
    }

    //获取纸牌资源
    public int getPokerRes() {
//        return pokerRes;
        return pokerState == PokerState.BACK ? pokerBackRes : pokerRes;
    }

    @NonNull
    @Override
    public String toString() {
//        return super.toString();
        return (point + 1) + getSuit();
    }

    //获取花色和王
    private String getSuit() {
        switch (suit) {
            case 0:
                return "♠";
            case 1:
                return "♥";
            case 2:
                return "♣";
            case 3:
                return "♦";
            default:
                return "王";
        }
    }

    //改变纸牌正反面
    public void changeSate() {

        if (this.pokerState == PokerState.BACK){
            this.pokerState = PokerState.FRONT;
        }else{
            this.pokerState = PokerState.BACK;
        }


//        this.pokerState = this.pokerState == PokerState.BACK ? PokerState.FRONT : PokerState.BACK;
    }

    //显示正面方法
    public void changeToFrontState() {
        this.pokerState=PokerState.FRONT;
    }

    public int compareTo(Poker poker) {
        //先判断是否有王
        if (suit == 4 || poker.suit == 4) {
            if (suit != 4) {
                return -1;//另一个是王
            } else if (poker.suit!=4) {
                return 1;//我是王
            }
        }

        //适用于没有王
        if (point > poker.point) {
            return 1;
        } else if (point < poker.point) {
            return -1;
        }else {
            //注意花色定义的索引值黑红梅方 0,1,2,3递增   实际花色大小相反
            if (suit > poker.suit) {
                return -1;
            }else {
                return 1;
            }
        }
    }

    public enum PokerState {
        BACK,
        FRONT
    }
}

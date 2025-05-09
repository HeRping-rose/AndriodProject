package com.andriod.card.model;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;

import com.andriod.card.R;
import com.andriod.card.utils.PokerManager;

import java.util.List;

//游戏中心
public class GameCenter {
    private PlayerManager playerManager = new PlayerManager();
    private PokerManager pokerManager = new PokerManager();
    private int tableMoney=100;
    private int totalMoney=0;
    private OnMoneyChangeListener mListener = null;//数据回调的监听者
    private OnGameOverListener mGameOverListener = null;//数据回调的监听者

    private int betAmount=0;//上一个玩家下注金额

    //监听玩家数量发生改变的事件
    public GameCenter(){
        playerManager.setOnActivePlayerChangeListener((count -> {
            if(count==1)
                gameOver();//游戏结束
            //将游戏结束的事件传递给外部
            if (mGameOverListener != null) {
                mGameOverListener.gameOver();
            }
        }));

        //监听pk完毕的事件
        playerManager.setOnPkFinishedListener(()->{
            gameOver();
            if (mGameOverListener != null) {
                mGameOverListener.gameOver();
            }
        });
    }

    //游戏结束方法
    private void gameOver() {
        playerManager.awardWinner(totalMoney);
    }

//    修改cardmodel参数
    public void init(List<View> playerViews, List<CardModel> cardViews) {
        playerManager.addPlayer(R.drawable.musk, "马斯克", 1000, playerViews.get(0), cardViews.get(0));
        playerManager.addPlayer(R.drawable.curry, "库里", 1000, playerViews.get(1), cardViews.get(1));
    }

    //开始游戏
    public void start() {
        //扣除牌桌钱
        totalMoney = playerManager.deductMoney(tableMoney);

        if (mListener != null) {
            mListener.tableMoneyChanged(totalMoney);
        }
        declare();//发牌
        playerManager.changeOrder();//开始游戏之后第一个下柱的人
    }

    public void setOnMoneyChangeListener(OnMoneyChangeListener listener) {
        this.mListener=listener;
    }
    public void setOnGameOverListener(OnGameOverListener listener) {
        this.mGameOverListener=listener;
    }

    //弃牌
    public void giveUP() {
        playerManager.giveUp();//将弃牌事件传递给playerManager
        playerManager.changeOrder();//当前玩家弃牌切换到下一个玩家
    }

    //加注下注
    public void betting(int bet) {
        //
        int count = playerManager.betting(bet);
        totalMoney+=count;
        betAmount=count;//记录当前玩家下注金额

        //总金额回调

        if(mListener!=null) {
            mListener.tableMoneyChanged(totalMoney);
        }
        playerManager.changeOrder();//

    }
    //PK
    public void pk() {

        int money = playerManager.pk(betAmount);

        totalMoney+=money;
        //回调
        if (mListener != null) {
            mListener.tableMoneyChanged(totalMoney);
        }
    }

    //发牌
    private void declare() {
        //获取玩家
        Player player = playerManager.nextPlayer();
        while (player != null) {
            Poker poker = pokerManager.getRandomPoker();
            player.dealCard(poker);
            player = playerManager.nextPlayer();
        }
        playerManager.showInfo();
    }

    //重置玩家信息
    public void resetGame() {
        totalMoney=0;

        playerManager.resetPlayer();
        pokerManager.generatePokers();

    }

    public interface OnMoneyChangeListener{
        void tableMoneyChanged(int money);
    }

    //游戏结束的接口。。。
    public interface OnGameOverListener{
        void gameOver();
    }
}

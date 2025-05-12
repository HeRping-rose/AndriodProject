package com.andriod.card.model;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class PlayerManager {
    //记录所有玩家对象
    private ArrayList<Player> players = new ArrayList<>();

    //设置并 初始化玩家索引-1
    private int playerIndex = -1;

    //记录上一次操作的玩家对象
    private Player lastActivePlayer = null;
    //记录监听者对象
    private OnActivePlayerChangeListener mListener=null;
    private OnPkFinishedListener mPkListener=null;


    //giveup方法实现
    public void giveUp() {
        Player player = players.get(playerIndex);     //获取玩家对象
        player.changePlayerState(Player.PlayerState.GIVE_UP);   //修改玩家为弃牌状态
        int activePlayerCount = currentActivePlayerCount();   //计算当前剩余玩家数量
        //回调玩家数量改变的事件
        if (mListener != null) {
            mListener.activePlayerChanged(activePlayerCount);
        }
    }

    //定义扣除底注的方法
    public int deductMoney(int tableMoney) {
        for (Player player : players) {
            player.deductMoney(tableMoney);
        }
        return tableMoney * players.size();
    }

    //奖励玩家的方法
    public void awardWinner(int totalMoney) {
        for (Player player : players) {
            if (player.isActive()) {
                player.addMoney(totalMoney);
            }
            player.showPoker();
        }
    }

    //切换下一个玩家操作
    public void changeOrder() {
        //根据索引获取下一个玩家
        playerIndex = (playerIndex + 1) % players.size();
        //可以得到玩家
        Player player = players.get(playerIndex);
        player.changePlayerState(Player.PlayerState.ACTIVE);
        if (lastActivePlayer != null) {
            lastActivePlayer.changePlayerState(Player.PlayerState.IDLE);//如果有上一个玩家则将其状态设置为空闲
            lastActivePlayer.closeBreathAnimation();//关闭上一个玩家动画
        }
        lastActivePlayer = player;
    }


    //提供一个方法添加玩家   修改参数cardmodel参数传递
    public void addPlayer(int headRes, String name, int money, View view, CardModel cardModel) {
        //创建一个玩家对象
        Player player = new Player(headRes, name, money);

        player.bindView(view);
        player.bindCardView(cardModel);

        players.add(player);
    }


    //获取下一个玩家 next Player():Player
    public Player nextPlayer() {
        //获取下一个玩家index
        playerIndex++;
        //判断玩家是否存在
        if (playerIndex >= players.size()) {
            //没有玩家了,玩家不存在,返回null

            playerIndex = -1;//将玩家重置
            return null;
        } else {
            return players.get(playerIndex);//返回玩家对应index的player对象
        }
    }

    //计算当前玩家剩余数量
    private int currentActivePlayerCount() {
        int count = 0;
        for (Player player : players) {
            if (!player.isGiveUp()) {
                count++;
            }
        }
        return count;
    }

    public void setOnActivePlayerChangeListener(OnActivePlayerChangeListener listener) {
        this.mListener=listener;
    }

    public void showInfo() {
        for (Player player : players) {
            Log.i("RON", player.toString());
        }
    }

    //重置玩家信息
    public void resetPlayer() {

        playerIndex=-1;
        lastActivePlayer=null;

        for (Player player : players) {
            player.changePlayerState(Player.PlayerState.IDLE);
            player.resetPokerState();
        }
    }

    //玩家下注
    public int betting(int bet) {
        //获取当前玩家对象

        Player player = players.get(playerIndex);
        return player.deductMoney(bet);
    }
    public  int pk(int betAmount){
        Player player = players.get(playerIndex);

        int count =player.deductMoney(betAmount);


        //找到下一个没有弃牌的玩家PK
        Player otherPlayer=findNextActivePlyer();

        int result=player.pk(otherPlayer);//-1,1

        if (result > 0) {
            //当前玩家牌大
            otherPlayer.changePlayerState(Player.PlayerState.GIVE_UP);
        }else {
            playerIndex = players.indexOf(otherPlayer);//记录大的玩家索引值
            player.changePlayerState(Player.PlayerState.GIVE_UP);
            otherPlayer.changePlayerState(Player.PlayerState.ACTIVE);
        }

        if (mPkListener != null) {
            mPkListener.pkFinished();
        }



        return count;
    }

    private Player findNextActivePlyer() {
        int i;
        while (true) {
            i=(playerIndex+1)%players.size();

            Player player=players.get(i);

            if(!player.isGiveUp()){
                return player;
            }
        }

    }


    //定义接口  用户回调玩家数量改变事件
    public interface OnActivePlayerChangeListener{
        void activePlayerChanged(int count);
    }
    public interface OnPkFinishedListener {
        void pkFinished();
    }

    public void setOnPkFinishedListener(OnPkFinishedListener listener) {
        mPkListener = listener;
    }
    //外部玩家视图
}

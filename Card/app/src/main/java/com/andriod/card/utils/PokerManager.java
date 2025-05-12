package com.andriod.card.utils;

import com.andriod.card.model.Poker;

import java.util.ArrayList;
import java.util.Random;

public class PokerManager {
    //定义数组存储卡牌
    private ArrayList<Poker> pokers = new ArrayList<>();

    //管理一副牌
    public PokerManager() {
        generatePokers();
        shuffle();
    }

    //获取随机纸牌
    public Poker getRandomPoker() {
        Random random = new Random();
        int index = random.nextInt(pokers.size());
        Poker poker = pokers.get(index);
        pokers.remove(index);
        return poker;
    }

    //生成随机纸牌
    public void generatePokers() {
        //清空数组
        pokers.clear();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                Poker poker = new Poker(i, j);
                pokers.add(poker);
            }
        }
        pokers.add(new Poker(4, 0));
        pokers.add(new Poker(4, 1));

        shuffle();
    }

    //实现随机洗牌效果
    private void shuffle() {
        Random random = new Random();
        int targetIndex = -1;
        for (int i = 0; i < pokers.size(); i++) {
            targetIndex = random.nextInt(pokers.size());
            if (i == targetIndex) continue;
            Poker temp = pokers.get(i);
            pokers.set(i, pokers.get(targetIndex));
            pokers.set(targetIndex, temp);
        }
    }
}

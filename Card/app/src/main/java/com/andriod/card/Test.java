package com.andriod.card;

import com.andriod.card.utils.TimerUtils;

public class Test implements TimerUtils.TimerListener{
    public void start(){

        TimerUtils timerUtils =new TimerUtils(1000, TimerUtils.Order.DESCENDING,this);
        timerUtils.start();
    }

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

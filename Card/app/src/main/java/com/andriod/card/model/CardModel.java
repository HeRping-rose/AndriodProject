package com.andriod.card.model;

import android.widget.ImageView;

//封装card and winner  并且重写默认构造函数暴露出去
public class CardModel {
    ImageView cardView;
    ImageView winnerView;

    public CardModel(ImageView cardView, ImageView winnerView) {
        this.cardView = cardView;
        this.winnerView = winnerView;
    }
}

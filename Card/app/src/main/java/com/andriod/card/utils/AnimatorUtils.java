package com.andriod.card.utils;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BaseInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;

public class AnimatorUtils {
    //淡入效果
    public static void fadeIn(View target) {
        target.setVisibility(View.VISIBLE);
        target.setAlpha(0.0f);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(target, "alpha", 1.0f);
        alphaAnimator.setDuration(500);
        alphaAnimator.start();
    }

    //淡出效果
    public static void fadeOut(View target) {
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(target, "alpha", 0.0f);
        alphaAnimator.setDuration(500);
        alphaAnimator.start();
        target.setVisibility(View.INVISIBLE);
    }

    //移动动画
    public static void translateX(View target, float fromX, float toX) {

        ObjectAnimator translateAnimator = ObjectAnimator.ofFloat(target, "x", fromX, toX);
        translateAnimator.setDuration(300);
        translateAnimator.setInterpolator(new BounceInterpolator());
        translateAnimator.start();

    }

    //翻转动画
    public static void rotateY(View target, float... degrees) {
        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(target, "rotationY", degrees);
        rotateAnimator.setDuration(200);
        rotateAnimator.start();
    }

    //缩放动画
    public static void scaleInOut(View target, float from, float to) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(from, to, from, to, Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(300);
        scaleAnimation.setRepeatCount(Animation.INFINITE);//重复无限次
        scaleAnimation.setRepeatMode(Animation.REVERSE);//反转
        target.startAnimation(scaleAnimation);//开启动画
    }

}

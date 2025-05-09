package com.andriod.card;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.andriod.card.databinding.ActivityMenuBinding;

public class MenuActivity extends AppCompatActivity {

    private ActivityMenuBinding mBinding;
    private boolean isMenuShown = true;
    boolean isOpen;
    private float radius;//圆弧半径


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mBinding= ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());




















//        View[] menuItems = {mBinding.menu1, mBinding.menu2, mBinding.menu3, mBinding.menu4, mBinding.menu5, mBinding.menu6};
//
////        float toYValue=-1f;
//        mBinding.menuBtn.setOnClickListener(v->{
//            if (isMenuShown) {
//                showMenu(menuItems[0], 0f, 0f, 0f, -1f, 100);
//                showMenu(menuItems[1], 0f, 0f, 0f, -2f, 200);
//                showMenu(menuItems[2], 0f, 0f, 0f, -3f, 300);
//                showMenu(menuItems[3], 0f, 0f, 0f, -4f, 400);
//                showMenu(menuItems[4], 0f, 0f, 0f, -5f, 500);
//                showMenu(menuItems[5], 0f, 0f, 0f, -6f, 600);
//            } else {
//                hideMenu(menuItems[0], 0f, 0f, -1f, 0f, 100);
//                hideMenu(menuItems[1], 0f, 0f, -2f, 0f, 200);
//                hideMenu(menuItems[2], 0f, 0f, -3f, 0f, 300);
//                hideMenu(menuItems[3], 0f, 0f, -4f, 0f, 400);
//                hideMenu(menuItems[4], 0f, 0f, -5f, 0f, 500);
//                hideMenu(menuItems[5], 0f, 0f, -6f, 0f, 600);
//            }
//            isMenuShown = !isMenuShown;
//        });
//    }
//    private void showMenu(View item, float fromX, float toX, float fromY, float toY, long delay) {
//            item.setVisibility(View.VISIBLE);
//
//            TranslateAnimation anim = new TranslateAnimation(
//                    Animation.RELATIVE_TO_SELF, fromX,
//                    Animation.RELATIVE_TO_SELF, toX,
//                    Animation.RELATIVE_TO_SELF, fromY,
//                    Animation.RELATIVE_TO_SELF, toY);
//
//            anim.setDuration(100);
////            anim.setStartOffset(delay);
//            anim.setFillAfter(true);
////            anim.setRepeatCount(Animation.INFINITE);
////            anim.setRepeatMode(Animation.RESTART);
//
//            item.startAnimation(anim);
//        }
//
//    private void hideMenu(View item, float fromX, float toX, float fromY, float toY, long delay) {
//            item.setVisibility(View.VISIBLE);
//            TranslateAnimation anim = new TranslateAnimation(
//                    Animation.RELATIVE_TO_SELF, fromX,
//                    Animation.RELATIVE_TO_SELF, toX,
//                    Animation.RELATIVE_TO_SELF, fromY,
//                    Animation.RELATIVE_TO_SELF, toY); // 向上移动一个自身高度
//            anim.setDuration(100);
//            anim.setStartOffset(delay);
//            anim.setFillAfter(true);
////            anim.setFillBefore(false);
////            anim.setRepeatCount(Animation.INFINITE);
////            anim.setRepeatMode(Animation.RESTART);
//            anim.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {}
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                    item.setVisibility(View.INVISIBLE);
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {}
//            });
//            item.startAnimation(anim);
    }


    public void arcAnimation(){
        mBinding.menuBtn.setOnClickListener(v->{

            ImageView[] menus = new ImageView[]{
                    mBinding.menu1,
                    mBinding.menu2,
                    mBinding.menu3,
                    mBinding.menu4,
            };
            double averageDegree=-180.0 / (menus.length+1);
            for (int i =0; i<menus.length;i++){
                startArcAnimation((i+1)*averageDegree,menus[i]);
            }
            isOpen=!isOpen;
        });

    }
    private void startArcAnimation(double degree, ImageView menu){

        float fromX=0f;
        float fromY=0f;
        float toX=0f;
        float toY=0f;
        double radians = Math.toRadians(degree);
//        radius = SystemUtils.dp2pxf(150,this);//圆弧半径
//        double degree=180.0 / (1+1);//角度
//        double radians=Math.toRadians(degree);//弧度
        float dy =(float)Math.sin(radians)* radius;
        float dx =(float)Math.sin(radians)* radius;

        if(isOpen){
            fromX = 0;
            fromY = 0;
            toX = dx;
            toY = dy;
        }

        TranslateAnimation ta =new TranslateAnimation(fromX,toX,fromY,toY);
        ta.setDuration(500);
        ta.setFillAfter(true);
        ta.setInterpolator(new BounceInterpolator());
        menu.startAnimation(ta);
    }
}
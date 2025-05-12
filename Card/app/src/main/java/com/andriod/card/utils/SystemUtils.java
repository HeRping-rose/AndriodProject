package com.andriod.card.utils;


//定义工具类管理px和dp的转化
import android.content.Context;

public class SystemUtils {
    public static int dp2px(int dp, Context context){
        float density = context.getResources().getDisplayMetrics().density;//获取屏幕密度
        return (int)(dp * density);
    }
    public static float dp2pxf(int dp, Context context){
        float density = context.getResources().getDisplayMetrics().density;//获取屏幕密度
        return dp * density;
    }
}

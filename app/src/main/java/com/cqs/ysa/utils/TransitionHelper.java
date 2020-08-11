package com.cqs.ysa.utils;

import android.app.Activity;

/**
 * Created by bingo on 2020/8/5 0005.
 * 页面过渡动画帮助类
 */

public class TransitionHelper {

    public static void enterBottom(Activity activity){
        activity.overridePendingTransition(android.R.anim.fade_in,0);
    }
    public static void enterLeft(Activity activity){
        activity.overridePendingTransition(android.R.anim.slide_in_left,0);
    }
//    public static void enterRight(Activity activity){
//        activity.overridePendingTransition(android.R.anim.slide_in_right,0);
//    }
//    public static void exitLeft(Activity activity){
//        activity.overridePendingTransition(0,android.R.anim.slide_out_left);
//    }
    public static void exitRight(Activity activity){
        activity.overridePendingTransition(0,android.R.anim.slide_out_right);
    }
}

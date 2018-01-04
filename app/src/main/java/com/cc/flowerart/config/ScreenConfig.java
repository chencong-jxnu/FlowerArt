package com.cc.flowerart.config;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;


/**
 * Created by Administrator on 2017/8/13.
 */

public class ScreenConfig {
    public static boolean isInit = false;
    public static int width_dp;//屏幕宽度(dp)
    public static int hight_dp;//屏幕高度(dp)
    public static float density;//屏幕密度（0.75 / 1.0 / 1.5）
    public static int width_pix;// 屏幕宽度（像素）
    public static int height_pix;// 屏幕高度（像素）
    public static int densityDpi;//屏幕密度dpi（120 / 160 / 240）

    public static void init(Context context){
        Log.w("test<<<<<08","ScreenConfig.init");
        if(!isInit){
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            width_pix = dm.widthPixels;
            height_pix= dm.heightPixels;
            density = dm.density;
            densityDpi = dm.densityDpi;
            //屏幕宽度算法:屏幕宽度（像素）/屏幕密度
            width_dp = (int) (width_pix/density);
            hight_dp = (int)(height_pix/density);
            isInit = true;
        }
    }
}

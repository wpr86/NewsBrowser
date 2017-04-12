package com.tj.newsbrowser.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DensityUtil {

    public static final boolean IMMERSIVE = true;

    private static int sStatusBarHeight = -1;

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取屏幕高度（像素）
     */
    public static int getScreenHeight(Context context) {
        try {
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(dm);
            return dm.heightPixels;
        } catch (Exception e) {
            //
        }
        return 0;
    }

    public static int getScreenWidth(Context context) {
        try {
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(dm);
            return dm.widthPixels;
        } catch (Exception e) {
            //
        }
        return 0;
    }

    public static float getScreenDensity(Context context) {
        try {
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(dm);
            return dm.density;
        } catch (Exception e) {
            //
        }
        return 2;
    }

    /**
     * 如果开启沉浸式，则获取statusBar高度
     */
    public static int getStatusBarHeight() {
        if (sStatusBarHeight != -1) {
            return sStatusBarHeight;
        }

        try {
            sStatusBarHeight = Resources.getSystem().getDimensionPixelSize(Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android"));
        } catch (Throwable e) {
            sStatusBarHeight = dip2px(25);
        }

        if (sStatusBarHeight <= 0) {
            sStatusBarHeight = dip2px(25);
        }

        return sStatusBarHeight;
    }

    public static int dip2px(float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}

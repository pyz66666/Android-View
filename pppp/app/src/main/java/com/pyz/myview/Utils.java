package com.pyz.myview;

import android.content.Context;
import android.content.res.Resources;

public class Utils {

    // 根据手机的分辨率从 dp 的单位 转成为 px(像素)
    public static int dip2px(float dpValue) {
        // 获取当前手机的像素密度（1个dp对应几个px）
        float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f); // 四舍五入取整
    }

    // 根据手机的分辨率从 px(像素) 的单位 转成为 dp
    public static int px2dip(float pxValue) {
        // 获取当前手机的像素密度（1个dp对应几个px）
        float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f); // 四舍五入取整
    }

}

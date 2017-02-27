package com.m.car2.mvvm.bindingadapter;

import android.content.Context;

/**
 * Created by zhenyu on 16/12/21.
 */

public class ViewHelper {

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}

package com.m.car2.mvvm.bindingadapter;

import android.content.res.ColorStateList;
import android.databinding.BindingConversion;
import android.support.annotation.ColorRes;

import com.m.car2.CarApp;


/**
 * Created by zhenyu on 16/12/15.
 */

public class BindingConverter {

    @BindingConversion
    public static  ColorStateList resourceToColorStateList(@ColorRes int resId) {
        return CarApp.getApp().getResources().getColorStateList(resId);
    }
}

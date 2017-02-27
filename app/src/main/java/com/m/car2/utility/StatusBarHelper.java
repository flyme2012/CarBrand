package com.m.car2.utility;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by zhenyu on 16/12/16.
 */

public class StatusBarHelper {

    private Activity activity;
    private View statusBarView;

    public StatusBarHelper(Activity context) {
        this.activity = context;
    }


    public StatusBarHelper setStatusBarVisible(boolean visible) {
        getStatusBarView(activity).setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }


    public StatusBarHelper setStatusBarBackground(Drawable drawable) {
        getStatusBarView(activity).setBackground(drawable);
        return this;
    }


    private View getStatusBarView(Activity context) {
        if (statusBarView == null) {
            statusBarView = new View(context);
            statusBarView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIHelper.getInsetStatusBarHeight(context)));
            FrameLayout decorView = (FrameLayout) context.getWindow().getDecorView();
            decorView.addView(statusBarView);
        }
        return statusBarView;
    }

}

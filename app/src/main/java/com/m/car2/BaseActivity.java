package com.m.car2;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.m.car2.utility.StatusBarHelper;
import com.m.car2.utility.UIHelper;

/**
 * Created by hubing on 2017/2/18.
 */

public class BaseActivity extends AppCompatActivity {

    private StatusBarHelper mStatusBarHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (UIHelper.getInsetStatusBarHeight(this) > 0 && showTintStatusBar()) {
            mStatusBarHelper = new StatusBarHelper(this);
            mStatusBarHelper.setStatusBarVisible(true).setStatusBarBackground(getResources().getDrawable(R.drawable.branding_bg));
        }
    }

    public boolean showTintStatusBar() {
        return true;
    }


}

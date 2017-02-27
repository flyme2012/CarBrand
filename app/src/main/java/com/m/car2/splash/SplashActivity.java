package com.m.car2.splash;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.m.car2.BaseActivity;
import com.m.car2.BuildConfig;
import com.m.car2.R;
import com.m.car2.databinding.ActivitySplashBinding;
import com.m.car2.home.HomeActivity;
import com.m.car2.utility.SPConstant;
import com.m.car2.utility.SPHelper;

/**
 * Created by hubing on 2017/2/18.
 */

public class SplashActivity extends BaseActivity {

    private Handler uiHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashBinding mLayoutBinding = DataBindingUtil.setContentView(SplashActivity.this, R.layout.activity_splash);
        uiHandler = new Handler();
        SPHelper.getInstance().setLong(SPConstant.SPLASH_SHOW_COUNT,SPHelper.getInstance().getLong(SPConstant.SPLASH_SHOW_COUNT) + 1);

        uiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                finish();
            }
        },1000);

        if (SPHelper.getInstance().getLong(SPConstant.SPLASH_SHOW_COUNT) % 2 == 1 ){
            mLayoutBinding.splashBg.setImageResource(R.drawable.splash_bg_exchange);
        }

        mLayoutBinding.setVersion(BuildConfig.VERSION_NAME);

    }

}

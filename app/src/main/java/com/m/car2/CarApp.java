package com.m.car2;

import android.app.Application;

import com.m.car2.utility.SPHelper;

/**
 * Created by hubing on 2017/2/18.
 */

public class CarApp extends Application {

    private static CarApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        SPHelper.getInstance();
    }


    public static CarApp getApp(){
        return instance;
    }
}

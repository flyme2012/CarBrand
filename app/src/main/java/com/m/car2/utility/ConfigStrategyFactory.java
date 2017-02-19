package com.m.car2.utility;

import android.content.Context;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhenyu on 16/1/28.
 */
public class ConfigStrategyFactory {


    public static SPHelper.ConfigStrategy create(Context context) {
        SPHelper.ConfigStrategy configStrategy = null;
        configStrategy = new DefaultConfig();
        return configStrategy;
    }


    /**
     * 默认配置
     */
    public static class DefaultConfig implements SPHelper.ConfigStrategy {

        @Override
        public void initConfig(Map<String, Object> map) {
            map.put(SPConstant.SPLASH_SHOW_COUNT,0L);
        }
    }

}

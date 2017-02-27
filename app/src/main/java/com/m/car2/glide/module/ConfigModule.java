package com.m.car2.glide.module;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by zhenyu on 16/12/14.
 */

public class ConfigModule implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //全局使用ARGB_8888来解码，保证图片质量
        builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565);
        //磁盘缓存放在外部缓存目录
        DiskCache.Factory factory = new ExternalCacheDiskCacheFactory(context);
        builder.setDiskCache(factory);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}

package com.m.car2.glide.decoder;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.m.car2.CarApp;

import java.io.IOException;

/**
 * Created by zhenyu on 17/2/16.
 */

public class PackageIconDecoder implements ResourceDecoder<ApplicationInfo, Bitmap> {


    private PackageManager packageManager;

    public PackageIconDecoder(Context context) {
        packageManager = context.getPackageManager();
    }

    @Override
    public Resource decode(ApplicationInfo source, int width, int height) throws IOException {
        if (source == null) {
            return null;
        }
        Bitmap bitmap = null;
        Drawable drawable = source.loadIcon(packageManager);
        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        } else {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.draw(canvas);
        }
        return BitmapResource.obtain(bitmap, Glide.get(CarApp.getApp()).getBitmapPool());
    }

    @Override
    public String getId() {
        return PackageIconDecoder.class.getName();
    }


}

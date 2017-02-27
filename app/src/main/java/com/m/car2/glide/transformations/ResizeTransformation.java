package com.m.car2.glide.transformations;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

/**
 * Created by zhenyu on 16/12/20.
 */

public class ResizeTransformation implements Transformation<Bitmap> {

    private BitmapPool mBitmapPool;
    private int mResizeWidth;
    private int mResizeHeight;

    public ResizeTransformation(Context context) {
        this(Glide.get(context).getBitmapPool());
    }

    public ResizeTransformation(Context context, int width, int height) {
        this(Glide.get(context).getBitmapPool());
        this.mResizeWidth = width;
        this.mResizeHeight = height;
    }

    public ResizeTransformation(Context context, int size) {
        this(Glide.get(context).getBitmapPool());
        this.mResizeWidth = size;
        this.mResizeHeight = size;
    }


    public ResizeTransformation(BitmapPool pool) {
        this.mBitmapPool = pool;
    }

    public ResizeTransformation(BitmapPool pool, int width, int height) {
        this.mBitmapPool = pool;
        this.mResizeWidth = width;
        this.mResizeHeight = height;
    }

    public ResizeTransformation(BitmapPool pool, int size) {
        this.mBitmapPool = pool;
        this.mResizeWidth = size;
        this.mResizeHeight = size;
    }


    @Override
    public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
        Bitmap source = resource.get();
        Bitmap.Config config =
                source.getConfig() != null ? source.getConfig() : Bitmap.Config.ARGB_8888;
        Log.d("hb","ResizeTransformation(resizeWidth=" + mResizeWidth + ", resizeHeight=" + mResizeHeight + ")");
        Bitmap bitmap = mBitmapPool.get(mResizeWidth, mResizeHeight, config);
        if (bitmap == null) {
            bitmap = Bitmap.createScaledBitmap(source, mResizeWidth, mResizeHeight, true);
        }
        return BitmapResource.obtain(bitmap, mBitmapPool);
    }

    @Override
    public String getId() {
        return "ResizeTransformation(resizeWidth=" + mResizeWidth + ", resizeHeight=" + mResizeHeight + ")";
    }

}

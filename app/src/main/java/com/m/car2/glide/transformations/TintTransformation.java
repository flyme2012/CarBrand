package com.m.car2.glide.transformations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

/**
 * Created by zhenyu on 16/12/20.
 */

public class TintTransformation implements Transformation<Bitmap> {


    private BitmapPool mBitmapPool;
    private int mTintColor;

    public TintTransformation(Context context) {
        this(Glide.get(context).getBitmapPool());
    }

    public TintTransformation(Context context, int color) {
        this(Glide.get(context).getBitmapPool());
        this.mTintColor = color;
    }


    public TintTransformation(BitmapPool pool) {
        this.mBitmapPool = pool;
    }

    public TintTransformation(BitmapPool pool, int color) {
        this.mBitmapPool = pool;
        this.mTintColor = color;
    }


    @Override
    public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
        Bitmap source = resource.get();
        int width = source.getWidth();
        int height = source.getHeight();
        Bitmap.Config config =
                source.getConfig() != null ? source.getConfig() : Bitmap.Config.ARGB_8888;
        Bitmap bitmap = mBitmapPool.get(width, height, config);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(width, height, config);
        }
        Canvas canvas = new Canvas(bitmap);
        Rect dest = new Rect(0, 0, width, height);
        canvas.drawBitmap(source, null, dest, null);
        ColorDrawable colorDrawable = new ColorDrawable(mTintColor);
        colorDrawable.setBounds(dest);
        colorDrawable.draw(canvas);
        //LBELog.i_t("fzy", "TintTransformation-->transform() color:%s", mTintColor);
        return BitmapResource.obtain(bitmap, mBitmapPool);
    }

    @Override
    public String getId() {
        return "TintTransformation(TintColor=" + mTintColor + ")";
    }


}

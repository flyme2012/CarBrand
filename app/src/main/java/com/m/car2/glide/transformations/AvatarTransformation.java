package com.m.car2.glide.transformations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.RectF;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

/**
 * Created by zhenyu on 16/12/30.
 */

public class AvatarTransformation implements Transformation<Bitmap> {


    private BitmapPool mBitmapPool;
    private int mOutlineWidth;
    private int mOutlineColor;

    public AvatarTransformation(Context context) {
        this(Glide.get(context).getBitmapPool());
    }

    public AvatarTransformation(Context context, int outlineWidth, int outlineColor) {
        this(Glide.get(context).getBitmapPool());
        this.mOutlineWidth = outlineWidth;
        this.mOutlineColor = outlineColor;
    }


    public AvatarTransformation(BitmapPool pool) {
        this.mBitmapPool = pool;
    }

    public AvatarTransformation(BitmapPool pool, int outlineWidth, int outlineColor) {
        this.mBitmapPool = pool;
        this.mOutlineWidth = outlineWidth;
        this.mOutlineColor = outlineColor;
    }


    @Override
    public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
        Bitmap source = resource.get();
        int size = Math.min(source.getWidth(), source.getHeight());


        int offsetX = (source.getWidth() - size) / 2;
        int offsetY = (source.getHeight() - size) / 2;

        Bitmap bitmap = mBitmapPool.get(size, size, Bitmap.Config.ARGB_8888);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmap);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.FILTER_BITMAP_FLAG | Paint.ANTI_ALIAS_FLAG));
        Paint paint = new Paint();
        paint.setStrokeWidth(mOutlineWidth);
        paint.setColor(mOutlineColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        float r = size / 2f;
        Rect bmpDest = new Rect(mOutlineWidth, mOutlineWidth, size - mOutlineWidth, size - mOutlineWidth);
       // canvas.drawColor(Color.parseColor("#4000FF00"));
        //canvas.drawCircle(offsetX + r, offsetY + r, r, paint);
        canvas.drawBitmap(source, null, bmpDest, null);
        float halfOutlineWidth = mOutlineWidth / 2;
        RectF arcDest = new RectF(halfOutlineWidth, halfOutlineWidth, size - mOutlineWidth + halfOutlineWidth, size - mOutlineWidth + halfOutlineWidth);
        canvas.drawArc(arcDest, 0, 360, true, paint);

        return BitmapResource.obtain(bitmap, mBitmapPool);
    }

    @Override
    public String getId() {
        return "AvatarTransformation(mOutlineWidth=" + mOutlineWidth + ", mOutlineColor=" + mOutlineColor + ")";
    }

}

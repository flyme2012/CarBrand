package com.m.car2.glide.transformations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

/**
 * Created by zhenyu on 17/1/23.
 */

public class FavoriteTransformation implements Transformation<Bitmap> {

    private BitmapPool mBitmapPool;
    private int iconSize;
    private int maskColor;

    public FavoriteTransformation(Context context) {
        this(Glide.get(context).getBitmapPool());
    }

    public FavoriteTransformation(Context context, int iconSize, int maskColor) {
        this(Glide.get(context).getBitmapPool());
        this.iconSize = iconSize;
        this.maskColor = maskColor;
    }


    public FavoriteTransformation(BitmapPool pool) {
        this.mBitmapPool = pool;
    }

    public FavoriteTransformation(BitmapPool pool, int iconSize, int maskColor) {
        this.mBitmapPool = pool;
        this.iconSize = iconSize;
        this.maskColor = maskColor;
    }


    @Override
    public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
        Bitmap source = resource.get();
        int size = Math.min(source.getWidth(), source.getHeight());
        Bitmap bitmap = mBitmapPool.get(size, size, Bitmap.Config.ARGB_8888);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmap);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.FILTER_BITMAP_FLAG | Paint.ANTI_ALIAS_FLAG));
        Paint paint = new Paint();

        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        float r = size / 2f;
       // Rect bmpDest = new Rect(mOutlineWidth, mOutlineWidth, size - mOutlineWidth, size - mOutlineWidth);
        // canvas.drawColor(Color.parseColor("#4000FF00"));
        //canvas.drawCircle(offsetX + r, offsetY + r, r, paint);
        //canvas.drawBitmap(source, null, bmpDest, null);
        //float halfOutlineWidth = mOutlineWidth / 2;
        //RectF arcDest = new RectF(halfOutlineWidth, halfOutlineWidth, size - mOutlineWidth + halfOutlineWidth, size - mOutlineWidth + halfOutlineWidth);
        //canvas.drawArc(arcDest, 0, 360, true, paint);
return null;
        //LBELog.i_t("fzy", "transform() size:%s outline:%s r:%s  bmpDest:%s arcDest:%s", size, mOutlineWidth, r, bmpDest, arcDest);
    }


    @Override
    public String getId() {
        return "FavoriteTransformation(iconSize=" + iconSize + ")";
    }


}

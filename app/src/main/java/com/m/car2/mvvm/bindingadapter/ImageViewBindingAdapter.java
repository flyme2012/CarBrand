package com.m.car2.mvvm.bindingadapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;
import com.bumptech.glide.load.resource.bitmap.Downsampler;
import com.bumptech.glide.load.resource.bitmap.StreamBitmapDecoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.m.car2.CarApp;
import com.m.car2.glide.decoder.PackageIconDecoder;
import com.m.car2.glide.loader.PackageIconLoader;
import com.m.car2.utility.SystemInfo;

/**
 * Created by zhenyu on 16/12/15.
 */

public class ImageViewBindingAdapter {

    @BindingAdapter({"imageUrl", "placeHolder", "transformations"})
    public static void loadImage(ImageView imageView, String imageUrl, Drawable placeHolder, Transformation<Bitmap>[] transformations) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .override(SystemInfo.dip2pxInt(imageView.getContext(), 56), SystemInfo.dip2pxInt(imageView.getContext(), 56))
                .placeholder(placeHolder)
                .into(imageView);
    }

    @BindingAdapter({"resourceId", "placeHolder"})
    public static void loadImage(ImageView imageView, int resourceId, Drawable placeHolder) {
        Glide.with(imageView.getContext())
                .load(resourceId)
                .override(SystemInfo.dip2pxInt(imageView.getContext(), 56), SystemInfo.dip2pxInt(imageView.getContext(), 56))
                .placeholder(placeHolder)
                .into(imageView);
    }

    @BindingAdapter({"imageUrl", "placeHolder"})
    public static void loadImage(ImageView imageView, String imageUrl, Drawable placeHolder) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .override(SystemInfo.dip2pxInt(imageView.getContext(), 56), SystemInfo.dip2pxInt(imageView.getContext(), 56))
                .placeholder(placeHolder)
                .into(imageView);
    }


//    @BindingAdapter({"imageUrl", "placeHolder", "transformations"})
//    public static void loadImage(ImageView imageView, final String imageUrl, Drawable placeHolder, Transformation<Bitmap>[] transformations) {
//        loadImageInternal(imageView, imageUrl, placeHolder, transformations);
//    }
//
//    @BindingAdapter({"imageUrl", "placeHolder"})
//    public static void loadImage(ImageView imageView, String imageUrl, Drawable placeHolder) {
//        loadImageInternal(imageView, imageUrl, placeHolder, null);
//    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView imageView, String imageUrl) {
        loadImageInternal(imageView, imageUrl, null, null);
    }

    private static void loadTargetInternal(Context targetContext, Target target, final String imageUrl, Drawable placeHolder, Transformation<Bitmap>[] transformations) {
        BitmapTypeRequest bitmapTypeRequest = Glide.with(targetContext).load(imageUrl).asBitmap();
        bitmapTypeRequest.diskCacheStrategy(DiskCacheStrategy.ALL);
        if (transformations != null && transformations.length > 0) {
            bitmapTypeRequest.transform(transformations);
        }
        if (placeHolder != null) {
            bitmapTypeRequest.placeholder(placeHolder);
        }
        //Log.d("fzy", "loadTargetInternal() target:" + target + "  url:" + imageUrl);
        bitmapTypeRequest.into(target);
    }


    private static void loadImageInternal(ImageView imageView, final String imageUrl, Drawable placeHolder, Transformation<Bitmap>[] transformations) {
        BitmapTypeRequest bitmapTypeRequest = Glide.with(CarApp.getApp()).load(imageUrl).asBitmap();
        bitmapTypeRequest.diskCacheStrategy(DiskCacheStrategy.ALL);
        if (transformations != null && transformations.length > 0) {
            bitmapTypeRequest.transform(transformations);
        }
        if (placeHolder != null) {
            bitmapTypeRequest.placeholder(placeHolder);
        }
        //Log.d("fzy", "loadImageInternal() image:" + imageView + "  url:" + imageUrl);
        bitmapTypeRequest.into(imageView);
    }


    private static String generateUrlsKey(String[] urls) {
        StringBuffer sb = new StringBuffer();
        for (String url : urls) {
            sb.append(url);
        }
        return sb.toString();
    }
}

package com.m.car2.mvvm.bindingadapter;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * Created by zhenyu on 16/12/15.
 */

public class ViewBindingAdapter {

    @BindingAdapter({"layoutWidth"})
    public static void setLayoutWidth(View view, int width) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = width;
    }

    @BindingAdapter("layoutHeight")
    public static void setLayoutHeight(View view, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = height;
    }

    @BindingAdapter({"bgUrl", "bgPlaceHolder", "bgTransformations"})
    public static void setBackground(final View view, String bgUrl, Drawable bgPlaceHolder, Transformation<Bitmap>[] transformations) {
        SimpleTarget simpleTarget = new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(resource);
                view.setBackground(bitmapDrawable);
            }

            @Override
            public void onLoadStarted(Drawable placeholder) {
                super.onLoadStarted(placeholder);
                view.setBackground(placeholder);
            }
        };
        BitmapTypeRequest bitmapTypeRequest = Glide.with(view.getContext()).load(bgUrl).asBitmap();
        bitmapTypeRequest.diskCacheStrategy(DiskCacheStrategy.RESULT);
        if (transformations != null && transformations.length > 0) {
            bitmapTypeRequest.transform(transformations);
        }
        if (bgPlaceHolder != null) {
            bitmapTypeRequest.placeholder(bgPlaceHolder);
        }
        bitmapTypeRequest.into(simpleTarget);
    }

}

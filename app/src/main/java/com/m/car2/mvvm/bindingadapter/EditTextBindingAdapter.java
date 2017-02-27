package com.m.car2.mvvm.bindingadapter;

import android.content.res.ColorStateList;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.EditText;

import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.m.car2.widgets.ResizedDrawable;

/**
 * Created by zhenyu on 16/12/15.
 */

public class EditTextBindingAdapter {


    @BindingAdapter({"drawableUrl", "placeHolder", "position", "transformations"})
    public static void loadDrawable(final EditText textView, String url, Drawable placeHolder, final int position, Transformation<Bitmap>[] transformations) {
        SimpleTarget simpleTarget = new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(textView.getResources(), resource);
                setTextDrawable(textView, bitmapDrawable, position, -1, -1);
            }
        };
        BitmapTypeRequest bitmapTypeRequest = Glide.with(textView.getContext()).load(url).asBitmap();
        bitmapTypeRequest.diskCacheStrategy(DiskCacheStrategy.RESULT);
        if (transformations != null && transformations.length > 0) {
            bitmapTypeRequest.transform(transformations);
        }
        if (placeHolder != null) {
            bitmapTypeRequest.placeholder(placeHolder);
        }
        bitmapTypeRequest.into(simpleTarget);
    }


    @BindingAdapter({"drawableUrl", "placeHolder", "position"})
    public static void loadDrawable(final EditText textView, String url, Drawable placeHolder, final int position) {
        loadDrawable(textView, url, placeHolder, position, null);
    }


    @BindingAdapter({"drawableUrl", "position"})
    public static void loadDrawable(final EditText textView, String url, final int position) {
        loadDrawable(textView, url, null, position, null);
    }


    @BindingAdapter({"drawableTop"})
    public static void setDrawableTop(EditText textView, Drawable drawable) {
        setTextDrawable(textView, drawable, Gravity.TOP, -1, -1);
    }


    @BindingAdapter({"drawableTop", "drawableSize"})
    public static void setDrawableTop(EditText textView, Drawable drawable, int size) {
        setDrawableTop(textView, drawable, size, null);
    }


    @BindingAdapter({"drawableTop", "drawableSize", "drawableTint"})
    public static void setDrawableTop(EditText textView, Drawable drawable, int size, ColorStateList colorStateList) {
        Drawable tintDrawable = null;
        if (colorStateList != null) {
            tintDrawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTintList(tintDrawable, colorStateList);
        }
        setTextDrawable(textView, tintDrawable, Gravity.TOP, size, size);
    }

    @BindingAdapter({"drawableTopResId", "drawableSize"})
    public static void setDrawableTop(EditText textView, int resId, int size) {
        setTextDrawable(textView, textView.getResources().getDrawable(resId), Gravity.TOP, size, size);
    }


    @BindingAdapter({"drawableLeft"})
    public static void setDrawableLeft(EditText textView, Drawable drawable) {
        setTextDrawable(textView, drawable, Gravity.LEFT, -1, -1);
    }

    @BindingAdapter({"drawableLeft", "drawableSize"})
    public static void setDrawableLeft(EditText textView, Drawable drawable, int size) {
        setTextDrawable(textView, drawable, Gravity.LEFT, size, size);
    }


    @BindingAdapter({"drawableLeftResId", "drawableSize"})
    public static void setDrawableLeft(EditText textView, int resId, int size) {
        if (resId > 0) {
            setTextDrawable(textView, textView.getResources().getDrawable(resId), Gravity.LEFT, size, size);
        }
    }

    @BindingAdapter({"drawableRight"})
    public static void setDrawableRight(EditText textView, Drawable drawable) {
        setTextDrawable(textView, drawable, Gravity.RIGHT, -1, -1);
    }

    @BindingAdapter({"drawableRight", "drawableSize"})
    public static void setDrawableRight(EditText textView, Drawable drawable, int size) {
        setTextDrawable(textView, drawable, Gravity.RIGHT, size, size);
    }

    @BindingAdapter({"drawableRightResId", "drawableSize"})
    public static void setDrawableRight(EditText textView, int resId, int size) {
        setTextDrawable(textView, textView.getResources().getDrawable(resId), Gravity.RIGHT, size, size);
    }


    @BindingAdapter({"drawableBottom"})
    public static void setDrawableBottom(EditText textView, Drawable drawable) {
        setTextDrawable(textView, drawable, Gravity.BOTTOM, -1, -1);
    }

    @BindingAdapter({"drawableBottom", "drawableSize"})
    public static void setDrawableBottom(EditText textView, Drawable drawable, int size) {
        setTextDrawable(textView, drawable, Gravity.BOTTOM, size, size);
    }

    @BindingAdapter({"drawableBottomResId", "drawableSize"})
    public static void setDrawableBottom(EditText textView, int resId, int size) {
        setTextDrawable(textView, textView.getResources().getDrawable(resId), Gravity.BOTTOM, size, size);
    }


    @BindingAdapter({"text"})
    public static void setText(EditText textView, int resId) {
        if (resId > 0) {
            textView.setText(textView.getResources().getString(resId));
        }
    }

    @BindingAdapter({"textStr"})
    public static void setText(EditText textView, String text) {
        if (!TextUtils.isEmpty(text)) {
            textView.setText(text);
        }
    }

    @BindingAdapter({"textColor"})
    public static void setTextColor(EditText textView, ColorStateList colorStateList) {
        textView.setTextColor(colorStateList);
    }


    private static void setTextDrawable(EditText textView, Drawable drawable, int gravity, int drawableWidth, int drawableHeight) {
        Drawable resizedDrawable = drawable;
        if (drawableWidth >= 0 && drawableHeight >= 0) {
            resizedDrawable = new ResizedDrawable(drawable, drawableWidth, drawableHeight);
        }
        Drawable[] drawables = textView.getCompoundDrawables();
        switch (gravity) {
            case Gravity.LEFT:
                textView.setCompoundDrawablesWithIntrinsicBounds(resizedDrawable, drawables[1], drawables[2], drawables[3]);
                break;
            case Gravity.TOP:
                textView.setCompoundDrawablesWithIntrinsicBounds(drawables[0], resizedDrawable, drawables[2], drawables[3]);
                break;
            case Gravity.RIGHT:
                textView.setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], resizedDrawable, drawables[3]);
                break;
            case Gravity.BOTTOM:
                textView.setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawables[2], resizedDrawable);
                break;
        }
    }

}

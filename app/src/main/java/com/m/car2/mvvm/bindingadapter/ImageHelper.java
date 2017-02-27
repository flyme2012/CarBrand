package com.m.car2.mvvm.bindingadapter;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.load.Transformation;
import com.m.car2.CarApp;
import com.m.car2.R;
import com.m.car2.glide.transformations.AvatarTransformation;
import com.m.car2.glide.transformations.ResizeTransformation;
import com.m.car2.glide.transformations.TintTransformation;
import com.m.car2.utility.EscapeProguard;
import com.m.car2.utility.SystemInfo;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;

/**
 * Created by zhenyu on 16/12/16.
 */

public class ImageHelper implements EscapeProguard {

    public static Transformation<Bitmap>[] createbrandItemTransformations() {
        Context context = CarApp.getApp();
        CropSquareTransformation cropSquareTransformation = new CropSquareTransformation(context);
        int size = CarApp.getApp().getResources().getDisplayMetrics().widthPixels / 5;
        return new Transformation[]{cropSquareTransformation};
    }

    public static Transformation<Bitmap>[] createCoverTransformations() {
        Context context = CarApp.getApp();
        CropSquareTransformation cropSquareTransformation = new CropSquareTransformation(context);
        return new Transformation[]{cropSquareTransformation};
    }


    public static Transformation<Bitmap>[] createItemCoverTransformations() {
        Context context = CarApp.getApp();
        CropSquareTransformation cropSquareTransformation = new CropSquareTransformation(context);
        return new Transformation[]{cropSquareTransformation};
    }


    public static Transformation<Bitmap>[] createPlaylistCoverItemTransformations() {
        Context context = CarApp.getApp();
        CropSquareTransformation cropSquareTransformation = new CropSquareTransformation(context);
        int size = SystemInfo.getDimensionPixelSize(context, R.dimen.floating_player_shadow_width);
        ResizeTransformation resizeTransformation = new ResizeTransformation(context, size);
        return new Transformation[]{cropSquareTransformation, resizeTransformation};
    }

    public static Transformation<Bitmap>[] createFavoritePlaylistCoverTransformations() {
        Context context = CarApp.getApp();
        CropSquareTransformation cropSquareTransformation = new CropSquareTransformation(context);
        int itemCoverSize = context.getResources().getDimensionPixelSize(R.dimen.item_cover_size);
        ResizeTransformation resizeTransformation = new ResizeTransformation(context, itemCoverSize);
        return new Transformation[]{cropSquareTransformation, resizeTransformation};
    }


    public static Transformation<Bitmap>[] createBlurBgTransformations() {
        BlurTransformation blurTransformation = new BlurTransformation(CarApp.getApp(), 25, 6);
        TintTransformation tintTransformation = new TintTransformation(CarApp.getApp(), CarApp.getApp().getResources().getColor(R.color.black_alpha_1a));
        return new Transformation[]{blurTransformation, tintTransformation};
    }


    public static Transformation<Bitmap>[] createAvatarTransformations() {
        Context context = CarApp.getApp();
        int userAvatarSize = SystemInfo.getDimensionPixelSize(context, R.dimen.user_avatar_size);
        CropCircleTransformation circleTransformation = new CropCircleTransformation(CarApp.getApp());
        ResizeTransformation resizeTransformation = new ResizeTransformation(context, userAvatarSize);
        return new Transformation[]{circleTransformation, resizeTransformation};
    }

    public static Transformation<Bitmap>[] createProfileTransformations() {
        Context context = CarApp.getApp();
        int userProfileSize = SystemInfo.getDimensionPixelSize(context, R.dimen.user_profile_size);
        int outlineWidth = SystemInfo.getDimensionPixelSize(context, R.dimen.list_item_divider_height) * 2;
        int outlineColor = context.getResources().getColor(R.color.black_alpha_1a);
        CropCircleTransformation circleTransformation = new CropCircleTransformation(context);
        AvatarTransformation avatarTransformation = new AvatarTransformation(context, outlineWidth, outlineColor);
        ResizeTransformation resizeTransformation = new ResizeTransformation(context, userProfileSize);
        return new Transformation[]{circleTransformation, resizeTransformation, avatarTransformation};
    }

    public static Transformation<Bitmap>[] createItemCircleTransformations() {
        CropCircleTransformation roundedCornersTransformation = new CropCircleTransformation(CarApp.getApp());
        return new Transformation[]{roundedCornersTransformation};
    }


    public static Transformation<Bitmap>[] createIconTransformations() {
        Context context = CarApp.getApp();
        CropCircleTransformation circleTransformation = new CropCircleTransformation(CarApp.getApp());
        return new Transformation[]{circleTransformation};
    }

    public static int getBigCoverSize() {
        int widthPixels = CarApp.getApp().getResources().getDisplayMetrics().widthPixels;
        int spacing = SystemInfo.getDimensionPixelSize(CarApp.getApp(), R.dimen.activity_horizontal_margin);
        return (widthPixels - 3 * spacing) / 2;
    }

}

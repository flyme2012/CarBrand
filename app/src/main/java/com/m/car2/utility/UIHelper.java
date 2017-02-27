package com.m.car2.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.target.BaseTarget;
import com.m.car2.R;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by zhenyu on 15/11/5.
 */
public class UIHelper implements EscapeProguard {


    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    public static Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }

    public static void forceEnableMenuIcon(PopupMenu popupMenu) {
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void hideSystemKeyBoard(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }


    public static boolean isAnimationSettingEnable(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                // 4.2以下没有 动画程序时长缩放 开关
                float scale = Settings.Global.getFloat(context.getContentResolver(), Settings.Global.ANIMATOR_DURATION_SCALE);
                return scale > 0.001f;
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }


    public static Bitmap loadBitmap(Resources resources, int resId, int width, int height) {
        Bitmap dest = null;
        try {
            Bitmap source = BitmapFactory.decodeResource(resources, resId);
            if (source != null) {
                if (source.getWidth() == width && source.getHeight() == height) {
                    dest = source;
                } else {
                    dest = Bitmap.createScaledBitmap(source, width, height, true);
                }
            }
        } catch (Exception e) {

        } catch (OutOfMemoryError error) {

        }
        return dest;
    }


    public static Bitmap loadBitmap(Resources resources, int resId) {
        Bitmap dest = null;
        try {
            dest = BitmapFactory.decodeResource(resources, resId);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError error) {

        }
        return dest;
    }

    public static Bitmap loadBitmap(Resources resources, int resId, BitmapFactory.Options options) {
        Bitmap dest = null;
        try {
            dest = BitmapFactory.decodeResource(resources, resId, options);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError error) {

        }
        return dest;
    }


    public static void onViewPreDrawCallback(final View target, final Runnable callback) {
        onViewPreDrawCallback(target, true, callback);
    }


    public static void onViewPreDrawCallback(final View target, final boolean canDraw, final Runnable callback) {
        if (target == null) {
            return;
        }
        target.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                target.getViewTreeObserver().removeOnPreDrawListener(this);
                if (callback != null) {
                    callback.run();
                }
                return canDraw;
            }
        });
    }


    public static void onViewGlobalLayoutCallback(final View target, final Runnable callback) {
        onViewGlobalLayoutCallback(target, true, callback);
    }


    public static void onViewGlobalLayoutCallback(final View target, final boolean oneTime, final Runnable callback) {
        target.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (oneTime) {
                    if (Build.VERSION.SDK_INT < 16) {
                        target.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        target.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
                if (callback != null) {
                    callback.run();
                }
            }
        });
    }


    public static void onViewGlobalLayoutCallback(final View target, final int callCount, final Runnable callback) {
        final AtomicInteger calledCount = new AtomicInteger(0);
        target.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (calledCount.addAndGet(1) == callCount) {
                    if (Build.VERSION.SDK_INT < 16) {
                        target.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        target.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
                if (callback != null) {
                    callback.run();
                }
            }
        });
    }

    public static void onViewGlobalLayoutCallback(final View target, final LayoutCallback callback) {
        target.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (callback.onViewLayoutChanged(target)) {
                    if (Build.VERSION.SDK_INT < 16) {
                        target.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        target.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            }
        });
    }

    public static interface LayoutCallback {
        public boolean onViewLayoutChanged(View target);
    }

    public static Bitmap buildViewDrawCache(View target) {
        return buildViewDrawCache(target, true);
    }


    public static Bitmap buildViewDrawCache(View target, boolean isTransparent) {
        Bitmap bitmap = null;
        if (target == null) {
            return null;
        }
        try {
            target.destroyDrawingCache();
            if (isTransparent) {
                target.setDrawingCacheBackgroundColor(0);
            } else {
                target.setDrawingCacheBackgroundColor(Color.BLACK);
            }
            boolean isEnable = target.isDrawingCacheEnabled();
            if (!isEnable) {
                target.setDrawingCacheEnabled(true);
            }
            bitmap = target.getDrawingCache();
        } catch (Throwable error) {
        }
        return bitmap;
    }


    public static Bitmap createAndCopyBitmap(Bitmap bitmap) {
        return createAndCopyBitmap(bitmap, bitmap != null ? bitmap.getConfig() : null);
    }


    public static Bitmap createAndCopyBitmap(Bitmap bitmap, Bitmap.Config config) {
        if (bitmap == null) {
            return null;
        }
        Bitmap dest = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), config != null ? config : Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(dest);
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return dest;
    }

    public static boolean isRTL(Context context) {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && context.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL);
    }


    /**
     * 在InsetDecor{<item name="android:windowTranslucentStatus">true</item>}主题下,插入状态栏的高度
     * Android 仅在4.4及以上支持此模式
     *
     * @param context
     * @return
     */
    public static int getInsetStatusBarHeight(Context context) {
        if (Build.VERSION.SDK_INT >= 19) {
            return getStatusBarHeight(context);
        } else {
            return 0;
        }
    }


    public static int getStatusBarHeight(Context context) {
        int result = 0;
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }
        if (result == 0) {
            result = context.getResources().getDimensionPixelSize(R.dimen.statusbar_height);
        }
        return result;
    }


    public static int getToolbarHeight(Context context) {
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        } else {
            return SystemInfo.getDimensionPixelSize(context, R.dimen.toolbar_height);
        }
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHalfWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels / 2;
    }

    public static Drawable resizeDrawable(Drawable drawable, int width, int height) {
        drawable.setBounds(0, 0, width, height);
        return drawable;
    }

    public static Drawable resizeDrawable(Drawable drawable, int size) {
        drawable.setBounds(0, 0, size, size);
        return drawable;
    }

    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }


    public static void performSendMailAction(Context context, String mail, String title) {
        Uri uri = Uri.parse("mailto:" + mail);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, "");
        context.startActivity(Intent.createChooser(intent, title));
    }


    public static void fillViewRect(View view, Rect rect) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        rect.set(location[0], location[1], location[0] + view.getWidth(), location[1] + view.getHeight());
    }


    public static Bitmap clipCircleBitmap(Bitmap source) {
       /* final int width = source.getWidth();
        final int height = source.getHeight();
        final Bitmap outputBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(outputBitmap);
        BitmapShader shader = new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint p = new Paint();
        p.setShader(shader);
        canvas.drawCircle(width / 2, height / 2, Math.min(width, height) / 2, p);*/
        if (source == null) {
            return null;
        }
        Bitmap output = Bitmap.createBitmap(source.getWidth(),
                source.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        int width = source.getWidth();
        int height = source.getHeight();
        final Rect rect = new Rect(0, 0, width, height);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(width / 2, height / 2,
                Math.min(width, height) / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, rect, rect, paint);
        return output;
    }


    public static int getFabFullPadding(Context context) {
        if (Build.VERSION.SDK_INT >= 21) {
            return 0;
        } else {
            return SystemInfo.dip2pxInt(context, 16);
        }
    }


    public static void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field f = null;
        Object obj_get = null;
        for (int i = 0; i < arr.length; i++) {
            String param = arr[i];
            try {
                f = imm.getClass().getDeclaredField(param);
                if (!f.isAccessible()) {
                    f.setAccessible(true);
                }
                obj_get = f.get(imm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == destContext) {
                        f.set(imm, null);
                    } else {
                        break;
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    public static float round(float value) {
        return (float) (Math.round(value * 10)) / 10;
    }


    public static int getSmartBarHeight(Context context) {
        boolean hasSmartBar = false;
        try {
            // 新型号可用反射调用Build.hasSmartBar()
            Method method = Class.forName("android.os.Build").getMethod("hasSmartBar");
            hasSmartBar = ((Boolean) method.invoke(null)).booleanValue();
        } catch (Exception e) {
        }
        if (hasSmartBar) {
            return SystemInfo.dip2pxInt(context, 48);
        } else {
            return 0;
        }
    }

    public static Rect getViewDisplayFrame(View view) {
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        return rect;
    }


    public static void gotoWebPlayStoreByPackageName(Context context, String appPackageName) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
    }

    public static void goToWebGpPlayStore(Context context, Uri uri) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    public static Bitmap clipSquareBitmap(Bitmap source) {
        if (source == null) {
            return null;
        }
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();
        if (sourceHeight == sourceWidth) {
            return source;
        }
        if (sourceWidth > sourceHeight) {
            int x = (sourceWidth - sourceHeight) / 2;
            return Bitmap.createBitmap(source, x, 0, sourceHeight, sourceHeight);
        } else {
            int y = (sourceHeight - sourceHeight) / 2;
            return Bitmap.createBitmap(source, 0, y, sourceWidth, sourceWidth);
        }
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        if (bmp == null) {
            return null;
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int options = 100;
        bmp.compress(Bitmap.CompressFormat.PNG, options, output);
        while (output.toByteArray().length / 1024 > 1024) {//大于1M继续压缩
            output.reset();//重置baos即清空baos
            options -= 20;//每次都减少20
            bmp.compress(Bitmap.CompressFormat.PNG, options, output);//这里压缩options%，把压缩后的数据存放到baos中
        }
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // Crops a square from the center of the original image.
    public static Bitmap getSquareBitmap(Bitmap bitmap, boolean recycle) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width == height) return bitmap;
        int size = Math.min(width, height);

        Bitmap target = Bitmap.createBitmap(size, size, getConfig(bitmap));
        Canvas canvas = new Canvas(target);
        canvas.translate((size - width) / 2, (size - height) / 2);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        if (recycle) bitmap.recycle();
        return target;
    }

    private static Bitmap.Config getConfig(Bitmap bitmap) {
        Bitmap.Config config = bitmap.getConfig();
        if (config == null) {
            config = Bitmap.Config.ARGB_8888;
        }
        return config;
    }


    /**
     * 获取虚拟功能键高度
     */
    public static int getVirtualBarHeigh(Context context) {
        int vh = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - windowManager.getDefaultDisplay().getHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    /**
     * 检查是否存在虚拟按键栏
     *
     * @param context
     * @return
     */
    public static boolean hasNavBar(Context context) {
        //Emulator
        if (Build.FINGERPRINT.startsWith("generic"))
            return true;

        int id = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        return id > 0 && context.getResources().getBoolean(id);
    }

    public static final String GP_APK_URL = "https://play.google.com/store/apps/details?id=com.lbe.free.music";

    public static void shareMusicApp(Context context, String subjectStr, String contentStr) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, contentStr + "\n" + GP_APK_URL);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, subjectStr);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }


    public static void loadIcon(Context context, String bgUrl, BaseTarget baseTarget, BitmapTransformation... transformations) {
        BitmapTypeRequest bitmapTypeRequest = Glide.with(context).load(bgUrl).asBitmap();
        bitmapTypeRequest.diskCacheStrategy(DiskCacheStrategy.RESULT);
        bitmapTypeRequest.transform(transformations);
        bitmapTypeRequest.into(baseTarget);
    }

    public static void loadIcon(Context context, String bgUrl, BaseTarget baseTarget) {
        BitmapTypeRequest bitmapTypeRequest = Glide.with(context).load(bgUrl).asBitmap();
        bitmapTypeRequest.diskCacheStrategy(DiskCacheStrategy.RESULT);
        bitmapTypeRequest.into(baseTarget);
    }

    /**
     * 获取actionbar的像素高度，默认使用android官方兼容包做actionbar兼容
     *
     * @return
     */
    public static int getActionBarHeight(Activity activity) {

        int actionBarHeight = ((AppCompatActivity) activity).getSupportActionBar().getHeight();
        if (actionBarHeight != 0) {
            return actionBarHeight;
        }

        final TypedValue tv = new TypedValue();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (((AppCompatActivity) activity).getTheme()
                    .resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(
                        tv.data, activity.getResources().getDisplayMetrics());
            }
        } else {
            // 使用android.support.v7.appcompat包做actionbar兼容的情况
            if (((AppCompatActivity) activity).getTheme()
                    .resolveAttribute(
                            android.support.v7.appcompat.R.attr.actionBarSize,
                            tv, true)) {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(
                        tv.data, activity.getResources().getDisplayMetrics());
            }

        }
        // else if
        // (getTheme().resolveAttribute(com.actionbarsherlock.R.attr.actionBarSize,
        // tv, true))
        // {
        // //使用actionbarsherlock的actionbar做兼容的情况
        // actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,
        // getResources().getDisplayMetrics());
        // }

        return actionBarHeight;
    }


}

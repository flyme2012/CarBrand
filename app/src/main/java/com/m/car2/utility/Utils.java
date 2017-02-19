package com.m.car2.utility;

import android.app.Dialog;
import android.content.pm.ApplicationInfo;
import android.os.Process;
import android.view.Window;
import android.view.WindowManager;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by leroy on 15-10-19.
 */
public class Utils {


    public static String fillEmptyIfNull(String text) {
        if (text != null) {
            return text;
        } else {
            return "";
        }
    }

    public static boolean isSystemApp(ApplicationInfo info) {
        return info.uid < Process.FIRST_APPLICATION_UID;
    }

    public static void fixDialogFullScreen(Dialog dialog) {
        if (null != dialog) {
            Window win = dialog.getWindow();
            if (null != win) {
                if (null != win.getDecorView()) {
                    win.getDecorView().setPadding(0, 0, 0, 0);
                }
                WindowManager.LayoutParams lp = win.getAttributes();
                if (null != lp) {
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                    win.setAttributes(lp);
                }
            }
        }
    }

    /**
     *
     * @return 获取当天24点的毫秒值
     */
    public static long getNightTime(){
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        return calendar.getTimeInMillis();
    }

    /**
     * 通过VolleyError判断是否是服务器错误
     */
    public static  boolean isServerError(Exception error) {
        return !(error == null ||
                error.toString().contains("NoConnectionError") ||
                error.toString().contains("TimeoutException"));
    }
}

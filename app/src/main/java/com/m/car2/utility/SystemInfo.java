package com.m.car2.utility;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.TypedValue;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.m.car2.CarApp;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SystemInfo {

    /**
     * 先获取android_id，若其为空，则返回sha-1的imei，再有问题就返回"UNKNOWN"
     *
     * @param context
     * @return
     */
    public static String getAndroidIdNotNull(final Context context) {
        String androidId = getAndroidId(context);
        if (androidId == null) {
            androidId = getSha1Imei(context);
        }
        return androidId;
    }

    public static String getAndroidId(final Context context) {
        String androidId = null;
        try {
            androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
        }
        if (TextUtils.isEmpty(androidId)) {
            try {
                androidId = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
            } catch (Exception ignore) {
            }
        }
        if ((androidId == null || androidId.length() == 0)) {
            try {
                Class<Build> cls = Build.class;
                Field field = cls.getDeclaredField("SERIAL");
                field.setAccessible(true);
                androidId = (String) field.get(null);
            } catch (Exception ignore) {
            }
        }
        return androidId;
    }

    public static String getSha1Imei(final Context context) {
        String imei = getImei(context);
        if (imei == null) {
            return "UNKNOWN";
        }
        try {
            return HexUtils.byteArrayToHexString(MessageDigest.getInstance("SHA-1").digest(imei.getBytes())).toUpperCase();
        } catch (Exception e) {
            return "UNKNOWN";
        }
    }

    public static String getImei(final Context context) {
        String imei = null;
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            imei = tm.getDeviceId();
        } catch (Exception e) {
        }
        return imei;
    }

    public static boolean checkConnectivity(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        if (networkinfo != null && networkinfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public static boolean isWiFiAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        if (networkinfo != null && networkinfo.getType() == ConnectivityManager.TYPE_WIFI && networkinfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isMobileAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        if (networkinfo != null && networkinfo.getType() == ConnectivityManager.TYPE_MOBILE && networkinfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }


    public static float dip2px(Context context, float dip) {
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, r.getDisplayMetrics());
        return px;
    }

    public static int dip2pxInt(Context context, int dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static float sp2px(Context context, float sp) {
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, r.getDisplayMetrics());
        return px;
    }

    public static float px2sp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (pxValue / scale + 0.5f);
    }


    public static CharSequence getLabel(Context context, String packageName, String className, String defaultValue) {
        try {
            final PackageManager pm = context.getPackageManager();
            CharSequence label = null;
            ComponentName componentName = new ComponentName(packageName, className);
            label = pm.getActivityInfo(componentName, 0).loadLabel(pm);
            if (label == null) {
                label = packageName;
            }
            return label;
        } catch (Exception e) {
        }
        return defaultValue;
    }

    public static int getDimensionPixelSize(Context context, int dimenResId) {
        if (context != null) {
            return context.getResources().getDimensionPixelSize(dimenResId);
        } else {
            return 0;
        }
    }


    private static String SELF_SIGNATURE_MD5 = "";

    public static String getSelfSignatureMD5(Context context) {
        if (!TextUtils.isEmpty(SELF_SIGNATURE_MD5)) {
            return SELF_SIGNATURE_MD5;
        }
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            if (pi != null && pi.signatures != null && pi.signatures.length > 0) {
                SELF_SIGNATURE_MD5 = MessageDigestUtil.getMD5String(pi.signatures[0].toByteArray());
            }
        } catch (Exception e) {
        }
        return SELF_SIGNATURE_MD5;
    }

    private static String SELF_FILE_MD5 = "";

    public static String getSelfFileMD5(PackageInfo packageInfo) {
        if (!TextUtils.isEmpty(SELF_FILE_MD5)) {
            return SELF_FILE_MD5;
        }
        SELF_FILE_MD5 = MessageDigestUtil.getFileMD5String(new File(packageInfo.applicationInfo.sourceDir));
        return SELF_FILE_MD5;
    }


    public static long getSDcardAvailableSpace(Context context) {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return -1;
        }
        File sdcardDir = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(sdcardDir.getPath());
        long blockSize = sf.getBlockSize();
        long availCount = sf.getAvailableBlocks();
        return blockSize * availCount;
    }


    public static boolean isMIUI(Context ctx) {
        boolean isMIUI = false;
        try {
            ctx.getPackageManager().getPackageInfo("miui", 0);
            isMIUI = true;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return isMIUI;
    }


    public static boolean isEmui() {
        String emui = SystemProperties.get("ro.build.version.emui");
        return !TextUtils.isEmpty(emui) && emui.contains("EmotionUI") || Build.DISPLAY.startsWith("EMUI");
    }

    public static boolean isMIUI() {
        return SystemProperties.getInt("ro.miui.ui.version.code", 0) > 0;
    }

    public static boolean isMeiZu() {
        String display = Build.DISPLAY;
        return !TextUtils.isEmpty(display) && display.toLowerCase().contains("flyme");
    }

    public static boolean is360UI() {
        String uiversion = SystemProperties.get("ro.build.uiversion");
        return !TextUtils.isEmpty(uiversion) && uiversion.toUpperCase().contains("360UI");
    }

    public static int convertVersion(String version) {
        int ver = 0;
        if ("unknown".equals(version))
            return ver;
        String[] vers = version.split("\\.");
        if (vers.length == 3) {
            if (vers[0].length() < 4 && vers[1].length() < 3 && vers[2].length() < 5)
                ver = (1000000 * Integer.parseInt(vers[0])) + (10000 * Integer.parseInt(vers[1])) + Integer.parseInt(vers[2]);
        }
        return ver;
    }


    private static volatile String advertisingId = null;

    public static String getAdvertisingId(final Context context) {
        if (!TextUtils.isEmpty(advertisingId)) {
            return advertisingId;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //AdvertisingIdClient.AdInfo info = AdvertisingIdClient.getAdvertisingIdInfo(context);
                    //advertisingId = info.getId();
                } catch (Exception ignore) {
                }
            }
        }).start();

        return advertisingId;
    }


    /**
     * Returns MAC address of the given interface name.
     *
     * @param interfaceName eth0, wlan0 or NULL=use first interface
     * @return mac address or empty string
     */
    public static String getMACAddress(String interfaceName) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (interfaceName != null) {
                    if (!intf.getName().equalsIgnoreCase(interfaceName)) continue;
                }
                byte[] mac = intf.getHardwareAddress();
                if (mac == null) return "";
                StringBuilder buf = new StringBuilder();
                for (int idx = 0; idx < mac.length; idx++)
                    buf.append(String.format("%02X:", mac[idx]));
                if (buf.length() > 0) buf.deleteCharAt(buf.length() - 1);
                return buf.toString();
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return "";
        /*try {
            // this is so Linux hack
            return loadFileAsString("/sys/class/net/" +interfaceName + "/address").toUpperCase().trim();
        } catch (IOException ex) {
            return null;
        }*/
    }

    /**
     * Get IP address from first non-localhost interface
     *
     * @return address or empty string
     */
    public static String getIPAddress() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        return Formatter.formatIpAddress(addr.hashCode());
                    }
                }
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return "";
    }


    private static String userAgent;

    public static String getUserAgent(final Context context) {
        if (!TextUtils.isEmpty(userAgent)) {
            return userAgent;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(context);
            } catch (Exception e) {
                userAgent = "malformed";
            }
        } else {
            try {
                final Class<?> aClass = Class.forName("android.webkit.WebSettingsClassic");
                final Constructor<?> constructor = aClass.getDeclaredConstructor(Context.class, Class.forName("android.webkit.WebViewClassic"));
                constructor.setAccessible(true);
                final Method method = aClass.getMethod("getUserAgentString");
                return (String) method.invoke(constructor.newInstance(context, null));
            } catch (Exception e) {
                if (Thread.currentThread().getName().equalsIgnoreCase("main")) {
                    WebView webview = new WebView(context);
                    userAgent = webview.getSettings().getUserAgentString();
                } else {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            userAgent = new WebView(context).getSettings().getUserAgentString();
                        }
                    });
                }
            }
        }
        return userAgent;
    }


    public static boolean hasExternalPackage(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException ignore) {
        }
        return false;
    }


    public static boolean launchExternalPackage(Context context, String packageName) {
        try {
            PackageManager pm = context.getPackageManager();
            Intent launchIntentForPackage = pm.getLaunchIntentForPackage(packageName);
            context.startActivity(launchIntentForPackage);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public static void launchInnerPackage() {

    }

    private static Pattern urlPattern = Pattern.compile("(?<=packageName\\=)[^\\&]*");

    public static String resolvePackageNameByUrl(String downloadUrl) {
        if (TextUtils.isEmpty(downloadUrl)) {
            return null;
        }
        Matcher matcher = urlPattern.matcher(downloadUrl);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }


    @SuppressWarnings("deprecation")
    public static String getGlobalDeviceId(final Context appContext) {
        String number = null;
        try {
            TelephonyManager tm = (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE);
            number = tm.getDeviceId();
        } catch (Exception e) {
        }

        if (number == null || number.length() == 0) {
            try {
                number = Settings.System.getString(appContext.getContentResolver(), Settings.System.ANDROID_ID);
            } catch (Exception e) {
            }
        }

        if ((number == null || number.length() == 0) && Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
            try {
                Class<Build> cls = Build.class;
                Field field = cls.getDeclaredField("SERIAL");
                field.setAccessible(true);
                number = (String) field.get(null);
            } catch (Exception e) {
            }
        }

        if (number == null || number.length() == 0)
            return "UNKNOWN";
        else
            return number;
    }

    public static boolean isWapNetwork(Context context) {
        try {
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connMgr.getActiveNetworkInfo();
            if (info.getType() == ConnectivityManager.TYPE_WIFI || info.getType() == 9) {
                return false;
            }
            String currentAPN = info.getExtraInfo();
            if (currentAPN == null)
                return false;
            return currentAPN.equalsIgnoreCase("cmwap") || currentAPN.equalsIgnoreCase("ctwap") || currentAPN.equalsIgnoreCase("3gwap") || currentAPN.equalsIgnoreCase("uniwap");
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isRTL() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == View.LAYOUT_DIRECTION_RTL;
        } else {
            return false;
        }
    }


    /*
   * 启动一个app
   */
    public static void openApp(Context context, String appPackageName) {
        try {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(appPackageName);
            context.startActivity(intent);
        } catch (Exception e) {
        }
    }

    public static int getScreenWidth() {
        return CarApp.getApp().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return CarApp.getApp().getResources().getDisplayMetrics().heightPixels;
    }


    public static String getBTMac(Context context) {
        try {
            return Settings.Secure.getString(context.getContentResolver(), "bluetooth_address");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;
        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pi;
    }

    public static String getCurrentProcessName(Context context) {
        try {
            int pid = android.os.Process.myPid();
            ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfoList = mActivityManager.getRunningAppProcesses();
            if (runningAppProcessInfoList != null && runningAppProcessInfoList.size() > 0) {
                for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
                    if (appProcess.pid == pid) {
                        return appProcess.processName;
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }


    //获得当前位置的国际ＩＳＯ代码
    private static String getCurrentLocalCountry(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String simCountryIso = telephonyManager.getSimCountryIso();
        if (TextUtils.isEmpty(simCountryIso)) {
            return null;
        }
        return telephonyManager.getNetworkCountryIso();
    }

    public static boolean isLargeMemoryDevice(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        return activityManager.getMemoryClass() > 96;
    }


    public static boolean is64bitDevice() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return Build.SUPPORTED_64_BIT_ABIS.length > 0;
        } else {
            return false;
        }

    }


    public static String getConfigLanguage(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return locale.toString();
    }

    public static String getDeviceCountry(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        if (locale == null) {
            locale = Locale.getDefault();
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceCountry = telephonyManager.getSimCountryIso();
        if (TextUtils.isEmpty(deviceCountry)) {
            deviceCountry = locale.getCountry();
        } else {
            deviceCountry = deviceCountry.toUpperCase();
        }
        return deviceCountry;
    }

    public static String getFbAid(Context context) {
        /*AttributionIdentifiers ai = AttributionIdentifiers.getAttributionIdentifiers(context);
        if (ai != null) {
            return ai.getAttributionId();
        }*/
        return null;
    }

    public static boolean isVpnConnected() {
        List<String> networkList = new ArrayList<>();
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (networkInterface.isUp())
                    networkList.add(networkInterface.getName());
            }
        } catch (Exception ex) {
        }
        return networkList.contains("tun0") || networkList.contains("ppp0");
    }

    public static String getCountryCode() {
        return CarApp.getApp().getResources().getConfiguration().locale.getCountry(); //获取国际代码
    }

    public static int getUser100Dimen(Context context) {
        String androidId = SystemInfo.getAndroidIdNotNull(context);
        if (TextUtils.isEmpty(androidId)) {
            androidId = Settings.System.getString(context.getContentResolver(), "com.lbe.youtunes.random_id");
            if (TextUtils.isEmpty(androidId)) {
                androidId = new BigInteger(130, new SecureRandom()).toString(32);
                Settings.System.putString(context.getContentResolver(), "com.lbe.youtunes.random_id", androidId);
            }
        }
        int hashCode = Math.abs(androidId.hashCode());
        return hashCode % 100;
    }

    public static String getApplicationName(Context context) {
        ApplicationInfo applicationInfo = getPackageInfo(context).applicationInfo;
        return (String) context.getPackageManager().getApplicationLabel(applicationInfo);
    }
}
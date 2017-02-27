package com.m.car2.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.m.car2.CarApp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhenyu on 15/8/18.
 */
public class SPHelper implements SharedPreferences {


    public interface ConfigurationChangeListener {
        void onConfigurationChange(ConfigurationItem<?> item);
    }

    private static class Listener implements OnSharedPreferenceChangeListener {

        private ConfigurationChangeListener configListener;

        public Listener(ConfigurationChangeListener listener) {
            this.configListener = listener;
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            configListener.onConfigurationChange(new ConfigurationItem<Object>(key));
        }
    }

    public static class ConfigurationItem<T> {
        private String name;
        private T defaultValue;

        public ConfigurationItem(String name) {
            this.name = name;
        }

        public ConfigurationItem(String name, T defaultValue) {
            this.name = name;
            this.defaultValue = defaultValue;
        }


        public String getName() {
            return name;
        }


        public boolean is(String name) {
            return this.name.equals(name);
        }

    }

    private static SPHelper instance;

    public static synchronized SPHelper getInstance() {
        if (instance == null) {
            instance = new SPHelper(CarApp.getApp());
        }
        return instance;
    }

    private SPHelper(Context context) {
        this.sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        ConfigStrategy strategy = ConfigStrategyFactory.create(context);
        strategy.initConfig(configMap);
    }

    private SharedPreferences sharedPreferences;
    private static final String SP_NAME = "configuration";
     /* End of configuration  definition */


    private HashMap<String, Object> configMap = new HashMap<String, Object>();

    public void dumpConfigMap() {
        StringBuffer log = new StringBuffer("Configuration-----------------> START DUMP:\n");
        Iterator<Map.Entry<String, Object>> iterator = configMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            Object value = entry.getValue();
            log.append("key:").append(entry.getKey()).append("    value:").append(value != null ? value.toString() : null).append("\n");
        }
        log.append("Configuration-----------------> END DUMP:\n");
    }


    private HashMap<ConfigurationChangeListener, OnSharedPreferenceChangeListener> listeners = new HashMap<ConfigurationChangeListener, OnSharedPreferenceChangeListener>();


    public void registerListener(ConfigurationChangeListener listener) {
        Listener l = new Listener(listener);
        synchronized (listeners) {
            listeners.put(listener, l);
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(l);
    }

    public void unregisterListener(ConfigurationChangeListener listener) {
        synchronized (listeners) {
            OnSharedPreferenceChangeListener l = listeners.remove(listener);
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(l);
        }
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, (Boolean) configMap.get(key));
    }

    @Override
    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }

    @Override
    public Editor edit() {
        return sharedPreferences.edit();
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }

    public int getInt(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }


    public int getInt(String key) {
        return sharedPreferences.getInt(key, (Integer) configMap.get(key));
    }

    @Override
    public Map<String, ?> getAll() {
        return sharedPreferences.getAll();
    }

    public String getString(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, (String) configMap.get(key));
    }


    public Set<String> getStringSet(String key, Set<String> defValue) {
        return sharedPreferences.getStringSet(key, defValue);
    }

    public Set<String> getStringSet(String key) {
        return sharedPreferences.getStringSet(key, (Set<String>) configMap.get(key));
    }


    public long getLong(String key, long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }

    public long getLong(String key) {
        return sharedPreferences.getLong(key, (Long) configMap.get(key));
    }


    public float getFloat(String key, float defValue) {
        return sharedPreferences.getFloat(key, defValue);
    }


    public float getFloat(String key) {
        return sharedPreferences.getFloat(key, (Float) configMap.get(key));
    }


    public boolean setBoolean(String key, boolean value) {
        return sharedPreferences.edit().putBoolean(key, value).commit();
    }

    /**
     * commits its changes to the in-memory immediately but starts an asynchronous commit to disk and you won't be notified of any failures.
     */
    public void setBooleanAsync(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }


    public boolean setInt(String key, int value) {
        return sharedPreferences.edit().putInt(key, value).commit();
    }

    /**
     * commits its changes to the in-memory immediately but starts an asynchronous commit to disk
     * and you won't be notified of any failures.
     */
    public void setIntAsync(String key, int value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public boolean setString(String key, String value) {
        return sharedPreferences.edit().putString(key, value).commit();
    }

    /**
     * commits its changes to the in-memory immediately but starts an asynchronous commit to disk
     * and you won't be notified of any failures.
     */
    public void setStringAsync(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public boolean setStringSet(String key, Set<String> value) {
        return sharedPreferences.edit().putStringSet(key, value).commit();
    }

    /**
     * commits its changes to the in-memory immediately but starts an asynchronous commit to disk
     * and you won't be notified of any failures.
     */
    public void setStringSetAsync(String key, Set<String> value) {
        sharedPreferences.edit().putStringSet(key, value).apply();
    }

    public boolean setLong(String key, long value) {
        return sharedPreferences.edit().putLong(key, value).commit();
    }

    /**
     * commits its changes to the in-memory immediately but starts an asynchronous commit to disk
     * and you won't be notified of any failures.
     */
    public void setLongAsync(String key, long value) {
        sharedPreferences.edit().putLong(key, value).apply();
    }

    public boolean setFloat(String key, float value) {
        return sharedPreferences.edit().putFloat(key, value).commit();
    }

    /**
     * commits its changes to the in-memory immediately but starts an asynchronous commit to disk
     * and you won't be notified of any failures.
     */
    public void setFloatAsync(String key, float value) {
        sharedPreferences.edit().putFloat(key, value).apply();
    }

    public boolean remove(String key) {
        return sharedPreferences.edit().remove(key).commit();
    }


    /**
     * commits its changes to the in-memory immediately but starts an asynchronous commit to disk
     * and you won't be notified of any failures.
     */
    public void removeAsync(String key) {
        sharedPreferences.edit().remove(key).apply();
    }

    private static SharedPreferences getSharePreferences(Context context) {
        return context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 在SPHelper未初始化时,可直接调用此方法获取SharedPreferences里面的值.
     **/
    public static boolean staticGetBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences sp = getSharePreferences(context);
        return sp.getBoolean(key, defaultValue);
    }

    /**
     * 在SPHelper未初始化时,可直接调用此方法获取SharedPreferences里面的值.
     **/
    public static int directGetInt(Context context, String key, int defValue) {
        SharedPreferences sp = getSharePreferences(context);
        return sp.getInt(key, defValue);
    }

    /**
     * 在SPHelper未初始化时,可直接调用此方法获取SharedPreferences里面的值.
     **/
    public static String directGetString(Context context, String key, String defValue) {
        SharedPreferences sp = getSharePreferences(context);
        return sp.getString(key, defValue);
    }

    /**
     * 在SPHelper未初始化时,可直接调用此方法获取SharedPreferences里面的值.
     **/
    public static Set<String> directGetStringSet(Context context, String key, Set<String> defValue) {
        SharedPreferences sp = getSharePreferences(context);
        return sp.getStringSet(key, defValue);
    }

    /**
     * 在SPHelper未初始化时,可直接调用此方法获取SharedPreferences里面的值.
     **/
    public static long directGetLong(Context context, String key, long defValue) {
        SharedPreferences sp = getSharePreferences(context);
        return sp.getLong(key, defValue);
    }

    /**
     * 在SPHelper未初始化时,可直接调用此方法获取SharedPreferences里面的值.
     **/
    public static float directGetFloat(Context context, String key, float defValue) {
        SharedPreferences sp = getSharePreferences(context);
        return sp.getFloat(key, defValue);
    }


    public static interface ConfigStrategy {
        public void initConfig(Map<String, Object> map);
    }

}

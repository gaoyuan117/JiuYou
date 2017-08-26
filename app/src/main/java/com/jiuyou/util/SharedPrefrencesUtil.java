package com.jiuyou.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.jiuyou.global.AppConfig;

/**
 * 共享参数工具类
 */
public class SharedPrefrencesUtil {

    //清除数据
    public static void removeData(Context context, String fileName, String... keys) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (String key : keys) {
            editor.remove(key);
        }

        editor.commit();

    }

    //保存数据
    public static void saveData(Context context, String fileName, String key, Object data) {

        String type = data.getClass().getSimpleName();
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) data);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) data);
        } else if ("String".equals(type)) {
            editor.putString(key, (String) data);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) data);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) data);
        }

        editor.commit();
    }

    /**
     * 从文件中读取数据
     */
    public static <T extends Object> T getData(Context context, String fileName, String key, Object defValue) {

        String type = defValue.getClass().getSimpleName();
        SharedPreferences sharedPreferences = context.getSharedPreferences
                (fileName, Context.MODE_PRIVATE);

        //defValue为为默认值，如果当前获取不到数据就返回它
        if ("Integer".equals(type)) {
            return (T) (Integer) sharedPreferences.getInt(key, (Integer) defValue);
        } else if ("Boolean".equals(type)) {
            return (T) (Boolean) sharedPreferences.getBoolean(key, (Boolean) defValue);
        } else if ("String".equals(type)) {
            return (T) sharedPreferences.getString(key, (String) defValue);
        } else if ("Float".equals(type)) {
            return (T) (Float) sharedPreferences.getFloat(key, (Float) defValue);
        } else if ("Long".equals(type)) {
            return (T) (Long) sharedPreferences.getLong(key, (Long) defValue);
        }

        throw new RuntimeException("get SharedPrefrences error!!");
    }
    /**
     * 设置主题
     *
     * @param context
     * @return 0 表示小学主题 1表示初中主题 2标志高中主题
     */
    public static void setTheme(Context context, int which) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConfig.LOGTAG, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt("theme", which).commit();
    }


    /**
     * 得到主题
     *
     * @param context
     * @return
     */
    public static int getTheme(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConfig.LOGTAG, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("theme", 0);
    }
}

package com.jiuyou.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *     desc  : 时间相关工具类
 * </pre>
 */
public class TimeUtils {
    /**
     * 将时间戳转为时间字符串
     * <p>格式为pattern</p>
     *
     * @param millis  毫秒时间戳
     * @param pattern 时间格式
     * @return 时间字符串
     */
    public static String millis2String(long millis, String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(new Date(millis));
    }

}
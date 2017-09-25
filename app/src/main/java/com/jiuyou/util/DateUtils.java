/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014. Ray
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.jiuyou.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtils {

    private static final SimpleDateFormat STANDARD_PATTERN = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    private static final SimpleDateFormat MMdd_FORMAT = new SimpleDateFormat("MM月dd日");

    /**
     * 把 2014-05-28 23:08:19 这样的格式转换为MMdd
     * <p>
     * 你猜我为什么要写这么多防御性代码？
     * 作为服务器，就不能多承担一点防错的工作吗？
     */
    public static String convertDateToMMdd(String date) {
        try {
            Date d = STANDARD_PATTERN.parse(date);
            if (d != null) {
                return MMdd_FORMAT.format(d);
            }
        } catch (Exception whateverIDoNotCare) {
            // 记一次 NullPointerException
            // 你猜我为什么要写这么多防御性代码？
            // 作为服务器，就不能多承担一点防错的工作吗？

        }
        return date;
    }


    public static String GetDate(long millis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        return String.format("%1$d年%2$d月%3$d日", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
    }

    /*
   * 将时间戳转换为时间
   */
    public static String stampToDate(String second){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(second);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /* 时间戳转换成字符窜 */
    public static String getDateToString(long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("MM月dd日");
        return sf.format(d);
    }
    /**
     * 时间戳转日期 年月
     * @param ms
     * @return
     */
    public static String transForDate2(Integer ms){
        if(ms==null){
            ms=0;
        }
        long msl=(long)ms*1000;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date temp=null;
        if(ms!=null){
            String str=sdf.format(msl);
            String str1=str.substring(0,10);
            str=str1.substring(0,4)+"年"+str1.substring(5,7)+"月";
            return str;
        }
        return "";
    }
    /**
     * 时间戳转日期 年月日
     * @param ms
     * @return
     */
    public static String transForDate(Integer ms){
        if(ms==null){
            ms=0;
        }
        long msl=(long)ms*1000;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date temp=null;
        if(ms!=null){
            String str=sdf.format(msl);
            return str.substring(0,10);
        }
        return "";
    }

    /**
     * 时间戳转日期 年月日
     * @param ms
     * @return
     */
    public static String transForDate3(Integer ms){
        if(ms==null){
            ms=0;
        }
        long msl=(long)ms*1000;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        Date temp=null;
        if(ms!=null){
            String str=sdf.format(msl);
            return str.substring(0,10);
        }
        return "";
    }
    /**
     * 时间戳转日期 年月日时分秒
     * @param ms
     * @return
     */
    public static String transForDate1(Integer ms){
        if(ms==null){
            ms=0;
        }
        long msl=(long)ms*1000;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date temp=null;
        if(ms!=null){
            String str=sdf.format(msl);
            return str;
        }
        return "";
    }

    public static String transForDate4(Integer ms){
        if(ms==null){
            ms=0;
        }
        long msl=(long)ms*1000;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date temp=null;
        if(ms!=null){
            String str=sdf.format(msl);
            return str;
        }
        return "";
    }
    public static String getDateByTimestamp(long times) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd")
                    .format(new Date(times));
        } catch (Exception e) {
            return "";
        }

    }

    /**
     * 距今多久，比如42分钟以前
     * 。PS：System.out.println("wisely--"+DateUtils.getTimeIntervalCurrent(new
     * Date(System.currentTimeMillis()-8*12*30*24*60*60*1000)));
     * 这句代码打印的是个负值，Integer的最大值为21亿多
     * ，核算成时间的话，应该不到25天的总毫秒数，所以只要超过25天的时间，都要强转为long类型
     */
    public static String getTimeIntervalCurrent(Date date) {

        long interval = System.currentTimeMillis() - date.getTime();
        long time;
        time = (long) Math.ceil(interval / (1 * 1000));
        if (time <= 0) {
            return "刚刚";
        }
        if (time < 60) {
            return time + "秒前";
        }
        time = (long) Math.ceil(interval / (60 * 1000));
        if (time < 60) {
            return time + "分钟前";
        }
        time = (long) Math.ceil(interval / (60 * 60 * 1000));
        if (time < 24) {
            return time + "小时前";
        }

        time = (long) Math.ceil(interval / (24 * 60 * 60 * 1000));
        if (time < 30) {
            return time + "天前";
        }

        time = (long) Math.ceil(interval / ((long) 30 * 24 * 60 * 60 * 1000));
        if (time < 12) {
            return time + "月前";
        }

        time = (long) Math.ceil(interval / ((long) 12 * 30 * 24 * 60 * 60 * 1000));
        return time + "年前";
    }

    //判断在启动页待的时间是否超过3s，如果超过就直接跳到首页
    public static boolean exceed3Seconds(long oldTime) {
        long time = System.currentTimeMillis() - oldTime;
        return time / 1000 >= 3;
    }
}

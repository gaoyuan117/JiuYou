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

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.jiuyou.core.task.UIThread;
import com.jiuyou.global.BaseApp;


/**
 * Created by ray on 14-5-21.
 */
public class UiUtils {


    public static int getScreenWidth(Context ctx) {
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }


    /**
     * 判断 Fragment 是否存在于视图层中
     */
    public static boolean isFragmentExisted(Fragment fragment) {
        if (fragment == null) {
            return false;
        }

        if (fragment.getActivity() != null && fragment.isAdded()) {
            return true;
        }

        return false;
    }

    public static boolean isActivityExisted(Activity activity) {
        return activity != null;
    }

    public static boolean isActivityUnfinished(Activity activity) {
        if (!isActivityExisted(activity)) {
            return false;
        }

        if (activity.isFinishing()) {
            return false;
        }

        return true;
    }

    /**
     * 计算多少个项目(假设高度一致)能充满列表
     */
    public static int calculateHowManyItemsFillUpListView(int listViewheight, int itemHeight) {
        return (int) Math.ceil((double) listViewheight / (double) itemHeight);
    }

    public static void reportFailure(Fragment fragment, String msg) {
        Toast.makeText(fragment.getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    public static Context getContext() {
        return BaseApp.getContext();
    }

    /**
     * 获取资源
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 获取view
     */
    public static View inflate(int layoutId) {
        return View.inflate(getContext(), layoutId, null);
    }

    /**
     * 获取drawable
     */
    public static Drawable getDrawable(int resId) {
        return getResources().getDrawable(resId);
    }

    /**
     * 获取颜色
     */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 获取颜色选择器
     */
    public static ColorStateList getColorStateList(int resId) {
        return getResources().getColorStateList(resId);
    }

    /**
     * dip转换px
     */
    public static int dip2px(int dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * pxz转换dip
     */
    public static int px2dip(int px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 获取文字数组
     */
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 将一个Runnable post到UI线程
     */
    public static void post(Runnable runnable) {
        post(runnable, 0);
    }

    /**
     * 将一个Runnable对象延迟一定毫秒数，post到UI线程中
     */
    public static void post(Runnable runnable, long delayMillis) {

        UIThread.execute(runnable, delayMillis);
    }

    /**
     * 把自身从父View中移除
     */
    public static void removeSelfFromParent(View view) {
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) parent;
                group.removeView(view);
            }
        }
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

    /**
     * 获取屏幕宽度
     */
    public static int getPhoneWidth() {
        return getDisplayMetrics(getContext()).widthPixels;
    }

    /**
     * 获取屏幕高度
     */
    public static int getPhoneHeight() {
        return getDisplayMetrics(getContext()).heightPixels;
    }


    public static void setListViewHeightOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}

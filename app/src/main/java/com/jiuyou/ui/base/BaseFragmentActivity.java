package com.jiuyou.ui.base;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jiuyou.customctrls.AlertProxy;
import com.jiuyou.util.ActivityAndFragmentHelper;


public class BaseFragmentActivity extends FragmentActivity {

    private com.jiuyou.customctrls.ProgressDialog ldb = null;
    public String TRUE = "true";
    // 弹出框对象
    private AlertProxy alertObj = null;
    private ActivityAndFragmentHelper activityHelper;
    @TargetApi(19)
    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    protected void updateStatsBarColor(String color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        alertObj = new AlertProxy(this);
        activityHelper = new ActivityAndFragmentHelper(this);
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        ldb = new com.jiuyou.customctrls.ProgressDialog(this).createDialog();
    }

    // 加载进度条
    public final com.jiuyou.customctrls.ProgressDialog getLoadingDataBar() {
        return ldb;
    }
    public final void loadingDataBarClose() {
        if (getLoadingDataBar() != null) {
            getLoadingDataBar().cancel();
        }
    }
    public final ActivityAndFragmentHelper getActivityAndFragmentHelper() {
        return activityHelper;
    }
    // 弹框
    public final AlertProxy getAlertDialog() {
        return alertObj;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    /**
     * 跳转到下一个activity中
     */
    public void toNext(Class<?> clazz) {
        activityHelper.startActivity(clazz);
    }


    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config=new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config,res.getDisplayMetrics() );
        return res;
    }
    @Override
    protected void onPause() {
        super.onPause();


    }
    @Override
    public void onDestroy() {
        ldb.cancel();
        ldb = null;
        alertObj = null;
        super.onDestroy();
    }
    public void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    public void exit(View view) {
        finish();
    }





}

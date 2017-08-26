package com.jiuyou.customctrls;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;


import com.jiuyou.R;

import java.io.File;

/**
 * 备注: 此LJWebView继承自Relativielayout,所以会导致丢失一个WebView的属性，如果大家
 * 在项目中需要用到，可是此类中加入，然后调用即可，可参考 public void setClickable(boolean value){
 * mWebView.setClickable(value); } 这个方法的定义和调用
 *
 * @author Administrator
 */
public class LJWebView extends RelativeLayout {

    public static int Circle = 0x01;
    public static int Horizontal = 0x02;

    private Context context;

    private WebView mWebView = null; //
    private ProgressBar progressBar = null; // 水平进度条
    private RelativeLayout progressBar_circle = null; // 包含圆形进度条的布局
    private int barHeight = 8; // 水平进度条的高
    private boolean isAdd = false; // 判断是否已经加入进度条
    private int progressStyle = Horizontal; // 进度条样式,Circle表示为圆形，Horizontal表示为水平

    private boolean isOpenTouchListener = true;

    public void setIsOpenTouchListener(boolean isOpenTouch) {
        if (!isOpenTouch && mWebView != null) mWebView.setOnTouchListener(null);
    }


    public LJWebView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
        init();
    }

    public LJWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        this.context = context;
        init();
    }

    public LJWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        this.context = context;
        init();
    }

    private void init() {
        mWebView = new WebView(context);
        this.addView(mWebView, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
//        mWebView.setOnTouchListener(listener);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebView.removeJavascriptInterface("searchBoxJavaBredge_");
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                super.onProgressChanged(view, newProgress);
                Log.d("tgh", "newProgress===" + newProgress);
                if (newProgress == 100) {

                    mWebView.getSettings().setBlockNetworkImage(false);

                    if (progressStyle == Horizontal) {
                        if (null != progressBar) {
                            progressBar.setVisibility(View.GONE);
                        }

                    } else {
                        if (null != progressBar) {
                            progressBar_circle.setVisibility(View.GONE);
                        }

                    }
                } else {
                    if (!isAdd) {
                        if (progressStyle == Horizontal) {
                            progressBar = (ProgressBar) LayoutInflater.from(context).inflate(R.layout.progress_horizontal, null);
                            progressBar.setMax(100);
                            progressBar.setProgress(0);
                            LJWebView.this.addView(progressBar, LayoutParams.FILL_PARENT, barHeight);
                        } else {
                            progressBar_circle = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.progress_circle, null);
                            LJWebView.this.addView(progressBar_circle, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
                        }
                        isAdd = true;
                    }

                    if (progressStyle == Horizontal) {
                        progressBar.setVisibility(View.VISIBLE);
                        progressBar.setProgress(newProgress);
                    } else {
                        progressBar_circle.setVisibility(View.VISIBLE);
                    }
                }

            }
        });
//        WebViewUtils.setWebViewCache(context,mWebView);
        mWebView.setHorizontalScrollBarEnabled(false);
    }

    public void addJavascriptInterface() {
        mWebView.addJavascriptInterface(context, "App");
    }

    public void addJavascriptInterface(Object object, String name) {
        mWebView.addJavascriptInterface(object, name);
    }

    public WebView getMWebView() {
        return mWebView;
    }

    public void setBarHeight(int height) {
        barHeight = height;
    }

    public void setProgressStyle(int style) {
        progressStyle = style;
    }

    public void setClickable(boolean value) {
        mWebView.setClickable(value);
    }

    public void setUseWideViewPort(boolean value) {
        mWebView.getSettings().setUseWideViewPort(value);
    }

    public void setSupportZoom(boolean value) {
        mWebView.getSettings().setSupportZoom(value);
    }


    public void setBuiltInZoomControls(boolean value) {
        mWebView.getSettings().setBuiltInZoomControls(value);
    }

    public void setJavaScriptEnabled(boolean value) {
        mWebView.getSettings().setJavaScriptEnabled(value);
    }

    public void setCacheMode(int value) {
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        //建议缓存策略为，判断是否有网络，有的话，使用LOAD_DEFAULT，无网络时，使用LOAD_CACHE_ELSE_NETWORK
        //设置缓存模式
        mWebView.getSettings().setCacheMode(value);
        //开启DOM storage API功能
        mWebView.getSettings().setDomStorageEnabled(true);
        //开启database storage API功能
        mWebView.getSettings().setDatabaseEnabled(true);

        File cacheDirPath = new File(Environment.getExternalStorageDirectory(), "/wisely/cache");
        if (!cacheDirPath.exists()) {
            cacheDirPath.mkdirs();
        }
        mWebView.getSettings().setDatabasePath(cacheDirPath.getAbsolutePath());
        //设置Application caches缓存目录
        mWebView.getSettings().setAppCachePath(cacheDirPath.getAbsolutePath());
        //开启Application Cache功能
        mWebView.getSettings().setAppCacheEnabled(true);
    }

    public void setDisplayZoomControls(Boolean flag) {
        mWebView.getSettings().setDisplayZoomControls(flag);
    }

    public void setHorizontalScrollBarEnabled(Boolean flag) {
        mWebView.setHorizontalScrollBarEnabled(flag);
    }

    public void setVerticalScrollBarEnabled(Boolean flag) {
        mWebView.setVerticalScrollBarEnabled(flag);
    }

    public void setWebViewClient(WebViewClient value) {
        mWebView.setWebViewClient(value);
    }

    public void loadUrl(String url) {
        mWebView.loadUrl(url);
    }

    float x, y;
    float dx, dy;

    OnTouchListener listener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = event.getX();
                    y = event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    dx = event.getX() - x;
                    dy = event.getY() - y;

                    break;
            }

            if (Math.abs(dx) > Math.abs(dy) && dx > 30) {
                ((Activity) context).finish();
            }

            return false;
        }
    };

}

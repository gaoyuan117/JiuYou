package com.jiuyou.ui.activity.saomiao;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.jiuyou.R;
import com.jiuyou.customctrls.LJWebView;

/**
 * 扫一扫的扫描结果
 */

public class Sao1SaoResultActivity extends Activity {
    private Button btnBack;
    private LJWebView mLJWebView = null;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sao1sao_result);
        btnBack = (Button) findViewById(R.id.btn_back);
        String result = getIntent().getStringExtra("result");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sao1SaoResultActivity.this.finish();
            }
        });
        mLJWebView = (LJWebView) findViewById(R.id.web);
        mWebView = mLJWebView.getMWebView();
        mLJWebView.setVisibility(View.VISIBLE);
        mLJWebView.setBarHeight(8);
        mLJWebView.setClickable(true);
        mLJWebView.setUseWideViewPort(true);
        mLJWebView.setSupportZoom(true);
        mLJWebView.setBuiltInZoomControls(true);
        mLJWebView.setJavaScriptEnabled(true);
        mLJWebView.setDisplayZoomControls(false);
        mLJWebView.loadUrl(result);
    }

}

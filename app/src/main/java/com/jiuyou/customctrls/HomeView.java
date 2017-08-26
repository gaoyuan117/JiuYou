package com.jiuyou.customctrls;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.jiuyou.ui.base.CommonPage;
import com.jiuyou.ui.base.OnPageEventListener;

/**
 */
public class HomeView extends CommonPage {

    private OnPageEventListener onPageEventListener;

    public HomeView(Context context) {
        super(context);
        init();
    }

    public HomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HomeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void showViewByState(int state) {
        mState = state;
        showView();
    }

    @Override
    public boolean needReset() {

        return false;
    }

    public void setOnPageEventListener(OnPageEventListener onPageEventListener) {

        this.onPageEventListener = onPageEventListener;
        wrapSuccessViewToParent();
    }

    private void wrapSuccessViewToParent()
    {
        mSuccessView = initView();
        FrameLayout.LayoutParams fl = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        addView(mSuccessView, fl);
        mSuccessView.setVisibility(View.INVISIBLE);
    }

    @Override
    public View createSuccessView() {

        return null;
    }

    @Override
    public View initView() {
        return onPageEventListener.initView();
    }

    @Override
    public void loadData() {
        onPageEventListener.loadData();
    }
}

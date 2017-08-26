package com.jiuyou.customctrls;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

public class CustomSwipRefresh extends SwipeRefreshLayout {

    private int mTouchSlop;
    private float mPrevX;

    public CustomSwipRefresh(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPrevX = MotionEvent.obtain(ev).getX();
                break;
            case MotionEvent.ACTION_MOVE:
                final float eventX = ev.getX();
                float xDiff = Math.abs(eventX - mPrevX);

                if (xDiff > mTouchSlop) {
                return false;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }
}

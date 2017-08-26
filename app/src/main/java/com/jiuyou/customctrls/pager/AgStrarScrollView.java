package com.jiuyou.customctrls.pager;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by huangzuoliang on 16/5/12.
 */
public class AgStrarScrollView extends ScrollView {
    Context mContext;
    public static int status = -1;
    public static final int SCROLL_TO_BUTTOM = 1;
    int scrollHeight = 0;
    public static final int SCROLL_TO_TOP = 2;

    public static final int LIST_SCROLL_TO_TOP = 3;

    public AgStrarScrollView(Context context) {
        super(context);
        mContext = context;
    }

    public AgStrarScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public AgStrarScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }
    private OnScrollistener onScrollistener;

    public OnScrollistener getOnScrollistener() {
        return onScrollistener;
    }

    public void setOnScrollistener(OnScrollistener onScrollistener) {
        this.onScrollistener = onScrollistener;
    }


    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX,
                                  boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (scrollY == scrollHeight) {
            Log.i(getClass().getName(), "到达底部");
            status = SCROLL_TO_BUTTOM;
        } else if (scrollY == 0) {
            Log.i(getClass().getName(), "到达顶部");
            status = SCROLL_TO_TOP;
        }
    }


    @Override
    //预处理触摸事件，判断是否处理事件。
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (status) {
            case SCROLL_TO_BUTTOM:
                return false;
            case LIST_SCROLL_TO_TOP:
            case SCROLL_TO_TOP:
                return super.onInterceptTouchEvent(ev);

        }
        return super.onInterceptTouchEvent(ev);

    }

    public int getScrollHeight() {
        return scrollHeight;
    }

    public void setScrollHeight(int scrollHeight) {
        this.scrollHeight = scrollHeight;
    }

    public interface OnScrollistener {
        void onScroll(int startY, int endY);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        onScrollistener.onScroll(oldt, t);
        super.onScrollChanged(l, t, oldl, oldt);
    }

}

package com.jiuyou.customctrls.pager;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


/**
 * Created by zwenkai on 2015/12/19.
 */
public abstract class LoadingLayoutBase extends FrameLayout implements ILoadingLayout {
    protected boolean isOpenRecommandMode = true;
    public SearchbarClickListner searchbarClickListner;
    public void onTextTipReset(){}
    public LoadingLayoutBase(Context context) {
        super(context);
    }

    public LoadingLayoutBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingLayoutBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setSearchbarClickEvent(SearchbarClickListner searchbarClickListner) {
        //ll_search_container
        this.searchbarClickListner = searchbarClickListner;
    }


    public void setOpenRecommandMode(boolean isOpenRecommandMode) {
        this.isOpenRecommandMode = isOpenRecommandMode;
    }

    public boolean isOpenReommandMode() {
        return this.isOpenRecommandMode;
    }

    public int getPulledDataTextCtrlHeight() {
        return 0;
    }

    public void setShowSearchBar(boolean isShowSearchBar) {

    }

    protected HeadResetListener headResetListener;

    public void setHeadResetListener(HeadResetListener headResetListener) {
        this.headResetListener = headResetListener;
    }


    public final void setHeight(int height) {
        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) getLayoutParams();
        lp.height = height;
        requestLayout();
    }

    public final void setWidth(int width) {
        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) getLayoutParams();
        lp.width = width;
        requestLayout();
    }

    public void changeViewType(int mode, int dataSize) {

    }

    public void setLastUpdatedLabel(CharSequence label) {

    }

    public void setLoadingDrawable(Drawable drawable) {

    }

    public void setTextTypeface(Typeface tf) {

    }

    /**
     * get the LoadingLayout height or width
     *
     * @return size
     */
    public abstract int getContentSize();

    /**
     * Call when the widget begins to slide
     */
    public abstract void pullToRefresh();

    /**
     * Call when the LoadingLayout is fully displayed
     */
    public abstract void releaseToRefresh();

    /**
     * Call when the LoadingLayout is sliding
     *
     * @param scaleOfLayout scaleOfLayout
     */
    public abstract void onPull(float scaleOfLayout);

    /**
     * Call when the LoadingLayout is fully displayed and the widget has released.
     * Used to prompt the user loading data
     */
    public abstract void refreshing();

    /**
     * Call when the data has loaded
     */
    public void reset() {

    }

    public void reset(boolean isPulledData, int dataSize) {

    }

    public void hideAllViews() {
        hideAllViews(this);
    }

    public void showInvisibleViews() {
        showAllViews(this);
    }

    private void hideAllViews(View view) {
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                hideAllViews(((ViewGroup) view).getChildAt(i));
            }
        } else {
            if (View.VISIBLE == view.getVisibility()) {
                view.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void showAllViews(View view) {
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                showAllViews(((ViewGroup) view).getChildAt(i));
            }
        } else {
            if (View.INVISIBLE == view.getVisibility()) {
                view.setVisibility(View.VISIBLE);
            }
        }
    }

}

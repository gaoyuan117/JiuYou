/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.jiuyou.customctrls.pager;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.jiuyou.R;


public class RotateLoadingLayout extends LoadingLayout {

    static final int ROTATION_ANIMATION_DURATION = 1200;

    private final Animation mRotateAnimation;
    private final Matrix mHeaderImageMatrix;

    private float mRotationPivotX, mRotationPivotY;

    private final boolean mRotateDrawableWhilePulling;
    private boolean isShowSarchBar = false;

    public void setShowSearchBar(boolean isShowSearchBar) {
        this.isShowSarchBar = isShowSearchBar;
        if (!this.isShowSarchBar) {
            ll_search_container.setVisibility(View.GONE);
        }
    }

    // mode=0是非抓取数量提示    1是抓取数量文本提示  2 隐藏头部

    public void changeViewType(int mode, int dataSize) {
        boolean isPulledData = dataSize > 0;
        if (mode == 0) {
            ll_refresh_and_sarch_container.setVisibility(View.VISIBLE);
            ll_pulled_data_tip.setVisibility(View.GONE);
        } else if (mode == 1) {
            ll_refresh_and_sarch_container.setVisibility(View.GONE);
            ll_pulled_data_tip.setVisibility(View.VISIBLE);

            //head_data_size_text
            if (isPulledData) {
                if (this.isOpenReommandMode()) {
                    tv_pulled_data_tip.setText(String.format(_ctx.getString(R.string.head_data_size_text), dataSize));
                } else {
                    tv_pulled_data_tip.setText(String.format(_ctx.getString(R.string.head_data_size_text_v2), dataSize));
                }
            } else {
                tv_pulled_data_tip.setText(R.string.head_no_data_text);
            }
            ll_pulled_data_tip.setGravity(Gravity.CENTER);

            this.postDelayed(resetTask, 2 * 1000);


        } else if (mode == 2) {
            ll_refresh_and_sarch_container.setVisibility(View.GONE);
            ll_pulled_data_tip.setVisibility(View.VISIBLE);

            //head_data_size_text

            tv_pulled_data_tip.setText(R.string.pulltorefresh_nonet);


            ll_pulled_data_tip.setGravity(Gravity.CENTER);

            this.postDelayed(resetTask, 2 * 1000);


        }
    }

    ResetTask resetTask = new ResetTask();

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public int getPulledDataTextCtrlHeight() {
        Resources r = getResources();


        float h = r.getDimension(R.dimen.pager_head_pulled_data_text_height);
        Log.d("sadasda", "h-" + h);
        return (int) h;
    }

    // 去除文本提示
    public void onTextTipReset() {
        this.removeCallbacks(resetTask);

        onTextTipResetCallBack();
    }

    private void onTextTipResetCallBack() {
        if (RotateLoadingLayout.this.headResetListener != null) {
            headResetListener.onHeadReset();
        }
    }

    class ResetTask implements Runnable {

        public void run() {

//			mInnerLayout.setVisibility(View.GONE);

            onTextTipResetCallBack();

//            changeViewType(0, 0);
        }

    }

    private TextView tv_search;

    public RotateLoadingLayout(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase.Orientation scrollDirection) {
        super(context, mode, scrollDirection);

        mRotateDrawableWhilePulling = true;

        mHeaderImage.setScaleType(ScaleType.MATRIX);
        mHeaderImageMatrix = new Matrix();
        mHeaderImage.setImageMatrix(mHeaderImageMatrix);

        mRotateAnimation = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
        mRotateAnimation.setDuration(ROTATION_ANIMATION_DURATION);
        mRotateAnimation.setRepeatCount(Animation.INFINITE);
        mRotateAnimation.setRepeatMode(Animation.RESTART);


        ll_pulled_data_tip.getLayoutParams().height = getPulledDataTextCtrlHeight();

        initSearchBar();


    }

    private void initSearchBar() {
        tv_search = (TextView) this.findViewById(R.id.tv_search);


        int i = (int) getResources().getDimension(R.dimen.pager_head_inner_search_bar_text_icon_miles);
        Drawable drawable = getResources().getDrawable(R.mipmap.search);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv_search.setCompoundDrawables(drawable, null, null, null);
        tv_search.setCompoundDrawablePadding(i);
    }


    public RotateLoadingLayout(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase.Orientation scrollDirection, TypedArray attrs) {
        super(context, mode, scrollDirection, attrs);

        mRotateDrawableWhilePulling = attrs.getBoolean(R.styleable.PullToRefresh_ptrRotateDrawableWhilePulling, true);

        mHeaderImage.setScaleType(ScaleType.MATRIX);
        mHeaderImageMatrix = new Matrix();
        mHeaderImage.setImageMatrix(mHeaderImageMatrix);

        mRotateAnimation = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
        mRotateAnimation.setDuration(ROTATION_ANIMATION_DURATION);
        mRotateAnimation.setRepeatCount(Animation.INFINITE);
        mRotateAnimation.setRepeatMode(Animation.RESTART);
        initSearchBar();
    }

    public void onLoadingDrawableSet(Drawable imageDrawable) {
        if (null != imageDrawable) {
            mRotationPivotX = Math.round(imageDrawable.getIntrinsicWidth() / 2f);
            mRotationPivotY = Math.round(imageDrawable.getIntrinsicHeight() / 2f);
        }
    }

    protected void onPullImpl(float scaleOfLayout) {
        float angle;
        if (mRotateDrawableWhilePulling) {
            angle = scaleOfLayout * 90f;
        } else {
            angle = Math.max(0f, Math.min(180f, scaleOfLayout * 360f - 180f));
        }

        mHeaderImageMatrix.setRotate(angle, mRotationPivotX, mRotationPivotY);
        mHeaderImage.setImageMatrix(mHeaderImageMatrix);
    }

    @Override
    protected void refreshingImpl() {

        mHeaderImage.startAnimation(mRotateAnimation);
    }

    @Override
    protected void resetImpl() {
        mHeaderImage.clearAnimation();
        resetImageRotation();
    }

    private void resetImageRotation() {
        if (null != mHeaderImageMatrix) {
            mHeaderImageMatrix.reset();
            mHeaderImage.setImageMatrix(mHeaderImageMatrix);
        }
    }

    @Override
    protected void pullToRefreshImpl() {
        // NO-OP
    }

    @Override
    protected void releaseToRefreshImpl() {
        // NO-OP
    }

    @Override
    protected int getDefaultDrawableResId() {
        return R.mipmap.default_ptr_rotate;
    }

}

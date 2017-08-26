package com.jiuyou.customctrls.pager;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.jiuyou.R;
import com.jiuyou.util.NetStatusUtils;


public class ListViewFootLayout extends LoadingLayoutBase {

    private TextView tvMoreTip;
    private ImageView ivRefreshing;
    Matrix mHeaderImageMatrix;
    final Interpolator ANIMATION_INTERPOLATOR = new LinearInterpolator();
    private RotateAnimation mRotateAnimation;
    final int ROTATION_ANIMATION_DURATION = 1200;

    public ListViewFootLayout(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        LayoutInflater.from(context).inflate(R.layout.foot_load_layout, this);
        ivRefreshing = (ImageView) this.findViewById(R.id.ivRefreshing);
        tvMoreTip = (TextView) this.findViewById(R.id.tvMoreTip);

        Drawable imageDrawable = null;
        imageDrawable = context.getResources().getDrawable(R.mipmap.ptr_rotate);
        ivRefreshing.setImageDrawable(imageDrawable);
        this.setVisibility(View.GONE);

        ivRefreshing.setScaleType(ScaleType.MATRIX);
        mHeaderImageMatrix = new Matrix();
        ivRefreshing.setImageMatrix(mHeaderImageMatrix);

        mRotateAnimation = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
        mRotateAnimation.setDuration(ROTATION_ANIMATION_DURATION);
        mRotateAnimation.setRepeatCount(Animation.INFINITE);
        mRotateAnimation.setRepeatMode(Animation.RESTART);

    }

    @Override
    public void setPullLabel(CharSequence pullLabel) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setRefreshingLabel(CharSequence refreshingLabel) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setReleaseLabel(CharSequence releaseLabel) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getContentSize() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void pullToRefresh() {
        // TODO Auto-generated method stub

    }

    @Override
    public void releaseToRefresh() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPull(float scaleOfLayout) {
        // TODO Auto-generated method stub

    }

    @Override
    public void refreshing() {
        // TODO Auto-generated method stub
        ivRefreshing.setVisibility(View.VISIBLE);
        this.setVisibility(View.VISIBLE);
        tvMoreTip.setText(R.string.foot_loading_text);
        ivRefreshing.startAnimation(mRotateAnimation);
    }

    @Override
    public void reset(boolean isPulledData, int dataSize) {
        // TODO Auto-generated method stub
        // this.setVisibility(View.GONE);
        ivRefreshing.clearAnimation();
        ivRefreshing.setVisibility(View.GONE);
        if (!NetStatusUtils.isNetWorkEnableWithoutToast()) {
            tvMoreTip.setText(R.string.pulltorefresh_nonet);
            return;
        }
        // 没有抓到数据
        if (!isPulledData) {
            tvMoreTip.setText(R.string.foot_no_data_text);
            this.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tvMoreTip.setText("");
                }
            }, 1000);
        } else {
            this.setVisibility(View.GONE);
            tvMoreTip.setText(R.string.foot_loading_text);
        }

    }

}

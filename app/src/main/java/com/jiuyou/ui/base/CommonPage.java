package com.jiuyou.ui.base;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.jiuyou.R;
import com.jiuyou.util.NetworkUtil;
import com.jiuyou.util.UiUtils;

/**
 * Created by wisely on 2016/3/14.
 */
public abstract class CommonPage extends FrameLayout implements OnPageEventListener {

    public static final int UNLOADING = 0;
    public static final int LOADING = 1;
    // 断网状态
    public static final int LOADERROR = 2;
    public static final int LOADEMPTY = 3;
    public static final int LOADSUCCESS = 4;
    public static final int SERVERERROR = 5;//服务器报错
    public boolean isOpenSearchMode = false;
    public View mLoadingView;
    private View mNoNetView;
    protected View mSuccessView;
    public View mEmptyView;
    private View mServerError;
    protected int mState;
    protected Context mContext;

    @Override
    protected void dispatchDraw(Canvas canvas) {

        super.dispatchDraw(canvas);


    }

    public CommonPage(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public CommonPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public CommonPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    public void autoRefresh() {
    }


    public void init() {
        createNoNetView();

        createEmptyView();

        createLoadingView();

        createServerErrorView();

        createSuccessView();
    }

    public void showView() {
        if (!NetworkUtil.isConnect(mContext)) {
            mState =LOADERROR;
            showViewByState();
            return;
        }
        
        if (mState == UNLOADING || needReset()) {
            mState = LOADING;
            loadData();
        }
        showViewByState();
    }

    public boolean needReset() {
        if (mState == LOADERROR || mState == SERVERERROR) {
            return true;
        }
        return false;
    }

    public void showViewByState() {
        mLoadingView.setVisibility(mState == LOADING ? VISIBLE : INVISIBLE);

        mNoNetView.setVisibility(mState == LOADERROR ? VISIBLE : INVISIBLE);

        mEmptyView.setVisibility(mState == LOADEMPTY ? VISIBLE : INVISIBLE);

        mServerError.setVisibility(mState == SERVERERROR ? VISIBLE : INVISIBLE);

        if (mSuccessView != null) {
            mSuccessView.setVisibility(mState == LOADSUCCESS ? VISIBLE : INVISIBLE);
        }
    }

    public View createNoNetView() {
        LayoutParams fl = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mNoNetView = UiUtils.inflate(R.layout.error_view);
        mNoNetView.findViewById(R.id.rl_loading_data_error).setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                showView();
            }
        });
        addView(mNoNetView, fl);
        mNoNetView.setVisibility(View.INVISIBLE);
        mNoNetView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mState = UNLOADING;
                showView();
            }
        });
        return mNoNetView;
    }

    public View createLoadingView() {
        LayoutParams fl = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mLoadingView = UiUtils.inflate(R.layout.loading_view);
        mLoadingView.setVisibility(View.INVISIBLE);
        addView(mLoadingView, fl);
        return mLoadingView;
    }

    public View createEmptyView() {
        LayoutParams fl = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mEmptyView = UiUtils.inflate(R.layout.empty_view);
        addView(mEmptyView, fl);
        mEmptyView.setVisibility(View.INVISIBLE);
        mEmptyView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mState = UNLOADING;
                showView();
            }
        });
        return mEmptyView;
    }

    public View createSuccessView() {
        mSuccessView = initView();
        LayoutParams fl = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(mSuccessView, fl);
        mSuccessView.setVisibility(View.INVISIBLE);
        return mSuccessView;
    }

    public View createServerErrorView() {
        LayoutParams fl = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mServerError = UiUtils.inflate(R.layout.server_error);
        mServerError.findViewById(R.id.rl_server_error).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showView();
            }
        });
        addView(mServerError, fl);
        mServerError.setVisibility(View.INVISIBLE);
        mServerError.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mState = UNLOADING;
                showView();
            }
        });
        return mServerError;
    }


    public abstract View initView();

    public abstract void loadData();



    public int getmState() {
        return mState;
    }


}

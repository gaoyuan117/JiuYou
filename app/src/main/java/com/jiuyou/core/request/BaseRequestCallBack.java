package com.jiuyou.core.request;

import android.app.Activity;

import com.jiuyou.customctrls.ProgressDialog;
import com.jiuyou.network.response.AbstractResponse;
import com.jiuyou.ui.base.BaseActivity;
import com.jiuyou.util.ToastUtil;
import com.jiuyou.util.UiUtils;

import java.lang.ref.WeakReference;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public abstract class BaseRequestCallBack<T> implements Callback<T> {

    protected final WeakReference<Activity> mRef;
    ProgressDialog mLoadingDataBar;

    public BaseRequestCallBack(Activity activity) {
        mRef = new WeakReference<Activity>(activity);
    }

    public BaseRequestCallBack(Activity activity, ProgressDialog loadingDataBar) {
        mRef = new WeakReference<Activity>(activity);
        mLoadingDataBar = loadingDataBar;
    }

    public Activity getActivity() {
        return mRef.get();
    }

    @Override
    public void success(T t, Response response) {
        Activity activity = mRef.get();
        if (activity instanceof BaseActivity) {
            ProgressDialog pd = ((BaseActivity) activity)
                    .getLoadingDataBar();
            if (null != pd && pd.isShowing()) {
                pd.cancel();
            }
        }
        if (UiUtils.isActivityUnfinished(activity)) {
            AbstractResponse abstractResponse = (AbstractResponse) t;

            if (abstractResponse.succeed()) {
                handleRespData(t, response);
            } else {
                ToastUtil.show(abstractResponse.getMessage());
            }

        }
        activity = null;
        if (mLoadingDataBar != null) {
            mLoadingDataBar.cancel();
        }
    }

    // 处理服务端返回的数据，
    public abstract void handleRespData(T t, Response response);

    public void handleFailure(RetrofitError error) {

    }

    @Override
    public void failure(RetrofitError error) {
        if (mLoadingDataBar != null) {
            mLoadingDataBar.cancel();
        }

        Activity activity = mRef.get();
        if (activity instanceof BaseActivity) {
            ProgressDialog pd = ((BaseActivity) activity).getLoadingDataBar();
            if (null != pd) {
                pd.cancel();
            }
        }

        if (UiUtils.isActivityUnfinished(activity)) {

            handleFailure(error);


        }
        activity = null;
    }
}
package com.jiuyou.ui.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.WindowManager;

import com.jiuyou.customctrls.AlertProxy;
import com.jiuyou.customctrls.ProgressDialog;
import com.jiuyou.network.response.JZBResponse.UserInfo;
import com.jiuyou.util.ActivityAndFragmentHelper;
import com.jiuyou.util.PrefereUtils;

public class BaseFragment extends Fragment {
    private String mName;
    public static String TRUE = "true";
    // 弹出框对象
    private AlertProxy alertObj = null;


    public final ProgressDialog getLoadingDataBar() {
        if (getActivity() != null)
            return ((BaseFragmentActivity) getActivity()).getLoadingDataBar();
        else return null;
    }

    public final AlertProxy getAlertDialog() {
        if (getActivity() != null) return ((BaseFragmentActivity) getActivity()).getAlertDialog();
        else return null;
    }

    public final ActivityAndFragmentHelper getActivityAndFragmentHelper() {
        if (getActivity() != null)
            return ((BaseFragmentActivity) getActivity()).getActivityAndFragmentHelper();
        else return null;
    }

    public void upDateUserStatus(UserInfo user) {
        PrefereUtils.getInstance().saveUser(user);
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getTag() != null) {
            mName = getTag();
        } else {
            mName = "baseFragment";
        }
    }



    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
    }


    /**
     * 跳转到下一个activity中
     */
    public void toNext(Class<?> clazz) {
        getActivityAndFragmentHelper().startActivity(clazz);
    }




    public final void loadingDataBarClose() {
        if (getLoadingDataBar() != null) {
            getLoadingDataBar().cancel();
        }
    }

    public final void loadingDataBarShow() {
        if (getLoadingDataBar() != null) {
            getLoadingDataBar().show();
        }
    }

    public final String getTokenId() {
        return PrefereUtils.getInstance().getToken();
    }

    public boolean isLogin() {
        return PrefereUtils.getInstance().isLogin();
    }

    public void initWindow() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

}

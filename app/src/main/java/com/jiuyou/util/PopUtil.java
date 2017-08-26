package com.jiuyou.util;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jiuyou.R;


/**
 * Created by huangzuoliang on 16/3/16.
 */
public class PopUtil {
    public static PopupWindow pop;
    public static Activity activity;
    public static ProgressBar mProgressBar;
    public static TextView mText;


    public static void showDialog(Activity context, String title, String content, String cancle, String sure, View.OnClickListener cancelListner, View.OnClickListener confirm_listener) {
        activity = context;
        final View popView = LayoutInflater.from(activity).inflate(R.layout.pop_dialog, null);
        ((TextView) popView.findViewById(R.id.txt_title)).setText(title);
        ((TextView) popView.findViewById(R.id.txt_msg)).setText(content);
        Button cancelBtn = (Button) popView.findViewById(R.id.btn_neg);
        Button okBtn = (Button) popView.findViewById(R.id.btn_pos);
        if (cancelListner != null) {
            cancelBtn.setOnClickListener(cancelListner);
        } else {
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pop.dismiss();
                }
            });
        }
        if (confirm_listener != null) {
            okBtn.setOnClickListener(confirm_listener);
        } else {
            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pop.dismiss();
                }
            });
        }

        cancelBtn.setText(cancle);
        okBtn.setText(sure);
        // 创建PopupWindow对象
        pop = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        // 需要设置一下此参数，点击外边可消失
        pop.setBackgroundDrawable(new ColorDrawable());
        // 设置点击窗口外边窗口消失
        pop.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        pop.setFocusable(true);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                setBgAlpha();
            }
        });
        pop.showAtLocation(popView, Gravity.CENTER, 0, 0);
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.alpha = 0.7f;
        activity.getWindow().setAttributes(params);

    }

    public static void showPhtotUpload(Activity context, String key) {
        View popView;
        activity = context;
        if (key == "Header") {
            popView = LayoutInflater.from(activity).inflate(R.layout.pop_upload_photo, null);
        } else if (key == "Confirm") {
            popView = LayoutInflater.from(activity).inflate(R.layout.pop_upload_confirm, null);
        } else {
            popView = LayoutInflater.from(activity).inflate(R.layout.pop_upload_cover, null);
        }

        // 创建PopupWindow对象
        pop = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        // 需要设置一下此参数，点击外边可消失
        pop.setBackgroundDrawable(new ColorDrawable());
        // 设置点击窗口外边窗口消失
        pop.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        pop.setFocusable(true);
        pop.setAnimationStyle(R.style.shareAnimal);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                setBgAlpha();
            }
        });
        pop.showAtLocation(popView, Gravity.BOTTOM, 0, 0);
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.alpha = 0.7f;
        activity.getWindow().setAttributes(params);


    }

    public static void showTextPop(Activity context) {
        activity = context;
        View popView = LayoutInflater.from(activity).inflate(R.layout.pop_textpup, null);
        Button btn_confirm = (Button) popView.findViewById(R.id.btn_confirm);
        // 创建PopupWindow对象
        pop = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        // 需要设置一下此参数，点击外边可消失
        pop.setBackgroundDrawable(new ColorDrawable());
        // 设置点击窗口外边窗口消失
        pop.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        pop.setFocusable(true);
        pop.setAnimationStyle(R.style.loginAnimal);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                setBgAlpha();
            }
        });
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBgAlpha();
            }
        });
        pop.showAtLocation(popView, Gravity.CENTER, 0, 0);
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.alpha = 0.7f;
        activity.getWindow().setAttributes(params);

    }



    /**
     * 打开软键盘
     */
    private static void delayClosePop(Handler mHandler, int s, final PopupWindow pop) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (activity != null && pop != null && pop.isShowing() && !activity.isFinishing()) {
                    pop.dismiss();
                }
            }
        }, s);
    }


    public static PopupWindow showLoginDialog(Activity context, String title, String content, View.OnClickListener listener, View.OnClickListener confirm_listener, View.OnClickListener cancle_listener) {
        activity = context;
        View popView = LayoutInflater.from(activity).inflate(R.layout.pop_login_dialog, null);
        Button btn_confirm = (Button) popView.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(confirm_listener);
        Button btn_cancle = (Button) popView.findViewById(R.id.btn_cancle);
        btn_cancle.setOnClickListener(cancle_listener);
        // 创建PopupWindow对象
        pop = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        // 需要设置一下此参数，点击外边可消失
        pop.setBackgroundDrawable(new ColorDrawable());
        // 设置点击窗口外边窗口消失
        pop.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        pop.setFocusable(true);
        pop.setAnimationStyle(R.style.loginAnimal);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                setBgAlpha();
            }
        });
        pop.showAtLocation(popView, Gravity.CENTER, 0, 0);
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.alpha = 0.7f;
        activity.getWindow().setAttributes(params);
        return pop;

    }


    /**
     * 更改商品状态的弹窗
     */
    public static PopupWindow showChangeDialog(Activity context, String title, String content, View.OnClickListener listener, View.OnClickListener confirm_listener, View.OnClickListener cancle_listener) {
        activity = context;
        View popView = LayoutInflater.from(activity).inflate(R.layout.pop_login_dialog, null);
        TextView txt_msg = (TextView) popView.findViewById(R.id.txt_msg);
        txt_msg.setText(content);
        Button btn_confirm = (Button) popView.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(confirm_listener);
        Button btn_cancle = (Button) popView.findViewById(R.id.btn_cancle);
        btn_cancle.setOnClickListener(cancle_listener);
        // 创建PopupWindow对象
        pop = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        // 需要设置一下此参数，点击外边可消失
        pop.setBackgroundDrawable(new ColorDrawable());
        // 设置点击窗口外边窗口消失
        pop.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        pop.setFocusable(true);
        pop.setAnimationStyle(R.style.loginAnimal);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                setBgAlpha();
            }
        });
        pop.showAtLocation(popView, Gravity.CENTER, 0, 0);
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.alpha = 0.7f;
        activity.getWindow().setAttributes(params);
        return pop;

    }
    public static void showPopup(Activity context, String content, PopupWindow.OnDismissListener dismissListener) {
        activity = context;
        View popView = LayoutInflater.from(activity).inflate(R.layout.popup_comm, null);
        TextView tv_content = (TextView) popView.findViewById(R.id.tv_content);
        tv_content.setText(content);
        // 创建PopupWindow对象
        pop = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        pop.setOnDismissListener(dismissListener);
        pop.showAtLocation(popView, Gravity.CENTER, 0, 0);
        delayClosePop(new Handler(), 1000, pop);

    }
    public static PopupWindow showPopup(Activity context, String content) {
        activity = context;
        final View popView = LayoutInflater.from(activity).inflate(R.layout.popup_comm, null);
        TextView tv_content = (TextView) popView.findViewById(R.id.tv_content);
        tv_content.setText(content);
        // 创建PopupWindow对象
        pop = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                setBgAlpha();
            }
        });
        pop.showAtLocation(popView, Gravity.CENTER, 0, 0);
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.alpha = 0.7f;
        activity.getWindow().setAttributes(params);
        delayClosePop(new Handler(), 1000, pop);
        return pop;

    }

    public static PopupWindow showProgress(Activity context, boolean isOpenProgressDialog, View.OnClickListener cancle_listener) {
        Log.d("xiaoma", "更新开始");
        activity = context;
        View popView = LayoutInflater.from(activity).inflate(R.layout.update_progress_bar, null);
        mProgressBar = (ProgressBar) popView.findViewById(R.id.myView_ProgressBar);
        mText = (TextView) popView.findViewById(R.id.tvProgress);

        Button btn_cancle = (Button) popView.findViewById(R.id.tv_progress_cancel);
        btn_cancle.setOnClickListener(cancle_listener);

        // 创建PopupWindow对象
        pop = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);

        // 需要设置一下此参数，点击外边可消失
        pop.setBackgroundDrawable(new ColorDrawable());
        // 设置点击窗口外边窗口消失
        pop.setOutsideTouchable(false);
        // 设置此参数获得焦点，否则无法点击
        pop.setFocusable(false);

        pop.setAnimationStyle(R.style.loginAnimal);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                setBgAlpha();
            }
        });
        pop.showAtLocation(popView, Gravity.CENTER, 0, 0);
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.alpha = 0.7f;
        activity.getWindow().setAttributes(params);

        return pop;
    }









    public static void dismissPop() {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
        }
    }

    public static void setBgAlpha() {
        if (pop != null) {
            WindowManager.LayoutParams params = activity.getWindow().getAttributes();
            params.alpha = 1f;
            activity.getWindow().setAttributes(params);
            pop = null;
            activity = null;
        }
    }
}

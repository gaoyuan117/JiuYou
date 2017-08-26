package com.jiuyou.customctrls.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.jiuyou.R;


/**
 * 输入支付密码
 *
 * @author lining
 */
public class PopEnterPassword extends PopupWindow {

    private PasswordView pwdView;

    private View mMenuView;

    private Activity mContext;

    public PopEnterPassword(final Activity context, boolean isFirstPay, String amount, OnPasswordInputFinish listener) {

        super(context);

        this.mContext = context;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mMenuView = inflater.inflate(R.layout.pop_enter_password, null);

        pwdView = (PasswordView) mMenuView.findViewById(R.id.pwd_view);


        //添加密码输入完成的响应
        pwdView.setOnFinishInput(listener);

        // 监听X关闭按钮
        pwdView.getImgCancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // 监听键盘上方的返回
//        pwdView.getRl_cancle().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
        if (isFirstPay) {
            pwdView.getConfirmPay().setText("设置支付密码");
            pwdView.getTextAmount().setVisibility(View.INVISIBLE);
        } else {
            pwdView.getConfirmPay().setText("确认付款");
            String str = amount.substring(3, amount.length());
            pwdView.getTextAmount().setText(str);
            pwdView.getTextAmount().setVisibility(View.VISIBLE);
        }
        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.pop_add_ainm);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x66000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

    }


    public PopEnterPassword(final Activity context, boolean isFirstPay, String amount, String a, OnPasswordInputFinish listener) {

        super(context);

        this.mContext = context;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mMenuView = inflater.inflate(R.layout.pop_enter_password, null);

        pwdView = (PasswordView) mMenuView.findViewById(R.id.pwd_view);


        //添加密码输入完成的响应
        pwdView.setOnFinishInput(listener);

        // 监听X关闭按钮
        pwdView.getImgCancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        if (isFirstPay) {
            pwdView.getConfirmPay().setText("设置支付密码");
            pwdView.getTextAmount().setVisibility(View.INVISIBLE);
        } else {
            pwdView.getConfirmPay().setText("确认付款");
            String str = amount;
            pwdView.getTextAmount().setText(str);
            pwdView.getTextAmount().setVisibility(View.VISIBLE);
        }
        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.pop_add_ainm);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x66000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

    }
}

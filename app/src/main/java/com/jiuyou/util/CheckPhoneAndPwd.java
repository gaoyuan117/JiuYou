package com.jiuyou.util;

import android.text.TextUtils;

import com.jiuyou.util.Status.LoginStatus;

public class CheckPhoneAndPwd {

    //	String phoneRegex = "^[1][358][0-9]{9}$";
    String phoneRegex = "^1[345789]\\d{9}$";
    String pwdRegex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
    String postCodeRegex = "[0-9]\\d{5}(?!\\d)";
    /**
     * 正常的话返回null
     */
    public LoginStatus checkPhoneAndPwd(String phone, String password) {

        LoginStatus status = checkPhone(phone);
        if (status != null)
            return status;
        status = checkPwd(password);
        return status == null ? null : status;
    }


    /**
     * 如果手机号正常的话，返回null
     */
    public Status.LoginStatus checkPhone(String phone) {
        if (TextUtils.isEmpty(phone))
            return Status.LoginStatus.PHONE_IS_NULL;
        return phone.matches(phoneRegex) ? null : Status.LoginStatus.PHONE_FORMAT_WRONG;
    }

    public  boolean checkIsPhone(String phone) {
        if (TextUtils.isEmpty(phone))
            return false;
        return phone.matches(phoneRegex);
    }

    /**
     * 如果密码正常的话，返回null
     */
    public LoginStatus checkPwd(String password) {
        if (TextUtils.isEmpty(password)) {
            return LoginStatus.PWD_IS_NULL;
        }
        return checkPwdFormat(password) ? null : LoginStatus.PWD_FORMAT_WRONG;
    }

    /**
     * 如果邮编正常的话，返回null
     */
    public LoginStatus checkPostCode(String postCode) {
        if (TextUtils.isEmpty(postCode)) {
            return LoginStatus.PWD_IS_NULL;
        }
        return checkPostCodeFormat(postCode) ? null : LoginStatus.PWD_FORMAT_WRONG;
    }

    /**
     * 校验密码格式
     */
    public boolean checkPwdFormat(String password) {
        return password.matches(pwdRegex);
    }

    /**
     * 校验邮编格式
     */
    public boolean checkPostCodeFormat(String postCode) {
        return postCode.matches(postCodeRegex);
    }

    /**
     * 注册时的一些校验 ,返回null代表全都符合要求
     */
    protected LoginStatus compareTwoPasswordAndPhoneAndCode(String phone, String password, String secPwd, String code) {
        LoginStatus status = checkPhoneAndPwd(phone, password);
        if (status != null)
            return status;
        if (TextUtils.isEmpty(secPwd))
            return LoginStatus.SECOND_PASSWORD_IS_NULL;
        if (!secPwd.equals(password))
            return LoginStatus.TWO_PWD_NOT_EQUALS;
        if (TextUtils.isEmpty(code))
            return LoginStatus.CODE_IS_NULL;
        return null;
    }

}

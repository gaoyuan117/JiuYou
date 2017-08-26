package com.jiuyou.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jiuyou.global.BaseApp;
import com.jiuyou.network.response.JZBResponse.UserInfo;

public class PrefereUtils {

    private String SP_NAME = "sheyuanwang";

    private static PrefereUtils mPrefereUtils;
    private SharedPreferences sp;
    private Editor mEditor;

    private PrefereUtils() {
        sp = BaseApp.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        mEditor = sp.edit();
    }

    public static PrefereUtils getInstance() {
        if (mPrefereUtils == null) {
            synchronized (PrefereUtils.class) {
                if (mPrefereUtils == null) {
                    mPrefereUtils = new PrefereUtils();
                }
            }
        }
        return mPrefereUtils;
    }
    // 保存用户
    public void saveUser(UserInfo user) {
        Gson gson = new Gson();
        String userStr = gson.toJson(user);
        mEditor.putString("user", userStr).commit();
    }

    // 获取用户
    public UserInfo getUser() {
        Gson gson = new Gson();
        String userStr = sp.getString("user", "");
        if (TextUtils.isEmpty(userStr)) {
            return null;
        }

        return gson.fromJson(userStr, UserInfo.class);
    }

    // 保存token
    public void saveToken(String token) {
        mEditor.putString("token", token).commit();
    }

    // 获取农红申请状态
    public String getAgStarState() {
        return sp.getString("agstar_state", "-1");
    }

    // 保存农红申请状态
    public void saveAgStarState(String state) {
        mEditor.putString("agstar_state", state).commit();
    }

    // 获取token
    public String getToken() {
        return sp.getString("token", "-1");
    }


    // 保存视频录制token
    public void saveRecordeToken(String token) {
        mEditor.putString("accessToken", token).commit();
    }

    // 获取视频录制token
    public String getRecordeToken() {
        return sp.getString("accessToken", "-1");
    }


    // 保存用户是否自动更新最新版应用的标识
    public void saveIsAutoUpdateVersion(boolean isAutoUpdateVersion) {
        mEditor.putBoolean("is_auto_update_version", isAutoUpdateVersion);
    }



    // 获取用户是否自动更新最新版应用的标识，默认为不自动更新
    public boolean getIsAutoUpdateVersion() {
        return sp.getBoolean("is_auto_update_version", false);
    }

    // 保存用户设置的性别
    public void saveGender(String gender) {
        mEditor.putString("gender", gender).commit();
    }

    // 获取用户已设置的性别
    public String getGender() {
        return sp.getString("gender", "保密");
    }

    // 保存一个备用token，在登出时可以使用
    public void saveBackupToken(String token) {
        mEditor.putString("backupToken", token).commit();
    }

    // 获取一个备用的token
    public String getBackupToken() {
        return sp.getString("backupToken", "");
    }

    // 登出时清除token，不清除备用token
    public void clearToken() {
        mEditor.putString("token", "").commit();
    }

    // 保存channelId
    public void saveChannelId(String channelId) {
        mEditor.putString("channelId", channelId).commit();
    }

    // 获取channelId
    public String getChannelId() {
        return sp.getString("channelId", "");
    }

    // 保存视频进度
    public void savePos(int pos) {
        mEditor.putInt("pos", pos).commit();
    }

    // 获取视频进度
    public int getPos() {
        return sp.getInt("pos", 0);
    }




    public void saveLatitude(String latitude) {
        mEditor.putString("latitude", latitude).commit();
    }

    public String getLatitude() {

        return sp.getString("latitude", "");
    }


    public void saveLongitude(String longitude) {
        mEditor.putString("longitude", longitude).commit();
    }

    public String getLongitude() {

        return sp.getString("longitude", "");
    }


    // 保存密码
    public void savePassword(String password) {
        mEditor.putString("password", password).commit();
    }


    // 获取密码
    public String getPassword() {
        return sp.getString("password", "");
    }

    // 保存昵称
    public void saveNikeName(String nikeName) {
        mEditor.putString("nikeName", nikeName).commit();
    }

    // 获取昵称
    public String getNikeName() {
        return sp.getString("nikeName", getPhone());
    }

    // 保存购物车
    public void saveShoppingCar(String json) {
        mEditor.putString("shoppingCar", json).commit();
    }


    // 获取购物车jsonString
    public String getShoppingCarJson() {
        return sp.getString("shoppingCar", "");
    }

    public void clearLocationShoppingCar() {
        mEditor.putString("shoppingCarList", "").commit();
        mEditor.putString("shoppingCar", "").commit();

    }

    // 保存要闻推送状态
    public void saveReplyPushSwitch(Boolean status) {
        mEditor.putBoolean("push__reply_switch", status).commit();
    }

    // 获取推送状态
    public boolean getReplyPushSwitch() {
        return sp.getBoolean("push__reply_switch", true);
    }

    // 保存要闻推送状态
    public void saveNewsPushSwitch(Boolean status) {
        mEditor.putBoolean("push__news_switch", status).commit();
    }

    // 获取推送状态
    public boolean getNewsPushSwitch() {
        return sp.getBoolean("push__news_switch", true);
    }

    // 保存手机号
    public void savePhone(String phone) {
        mEditor.putString("phone", phone).commit();
    }

    // 获取手机号
    public String getPhone() {
        return sp.getString("phone", "");
    }

    // 存储头像地址
    public void saveHeadImage(String headImage) {
        mEditor.putString("headImage", headImage).commit();
    }

    public String getHeadImage() {
        return sp.getString("headImage", "");
    }

    /**
     * 存储uuid，是在获取验证码时得到的
     */
    public void saveUUID(String uuid) {
        mEditor.putString("uuid", uuid).commit();
    }

    /**
     * 获取uuid，uuid是在获取短信验证码时得到的
     */
    public String getUUID() {
        return sp.getString("uuid", "");
    }

    /**
     * 保存头像的本地路径
     */
    public void saveNativeUserPhoto(String path) {
        mEditor.putString(getToken(), path).commit();
    }

    /**
     * 获取头像的本地路径
     */
    public String getNativeUserPhoto() {
        return sp.getString(getToken(), "");
    }

    /**
     * 是否有头像的本地存储
     */
    public boolean hasNativeUserPhoto() {
        if (TextUtils.isEmpty(getNativeUserPhoto())) return false;
        return true;
    }

    /**
     * 判断是否登录
     */
    public boolean isLogin() {

        return sp.getBoolean("isLogin", false);
    }
    /**
     * 判断是否修改春东号
     */
    public boolean isChangeChunDong() {

        return sp.getBoolean("ischange", false);
    }

    /**
     * 判断是否登录
     */
    public void fixLoginStatus(boolean isLogin) {
        mEditor.putBoolean("isLogin", isLogin).commit();
    }


    /**
     * 判断是否修改春东号
     */
    public void fixChunDongStatus(boolean isChange) {
        mEditor.putBoolean("ischange", isChange).commit();
    }


    public void saveLastPhone(String phone) {
        mEditor.putString("lastPhone", phone).commit();
    }

    public void savePwd(String pwd) {
        mEditor.putString("pwd", pwd).commit();
    }
    public String getPwd() {
        return sp.getString("pwd", "");
    }
    public String getLastPhone() {
        return sp.getString("lastPhone", "");
    }


    public void saveUid(String uid) {
        mEditor.putString("uid", uid).commit();
    }

    public String getUid() {
        return sp.getString("uid", "");
    }

    /**
     * 保存是否显示向导页
     */
    public void saveGuidePagersTag(Boolean isShow) {
        mEditor.putBoolean("ShowGuidePager", isShow).commit();
    }

    /**
     * 获取向导页标识位
     */
    public boolean getGuidePagersTag() {
        return sp.getBoolean("ShowGuidePager", false);
    }

    /**
     * 是不是第一次打开
     */
    public boolean isFirstOpen() {
        return sp.getBoolean("firstOpen", true);
    }

    /**
     * 设置为不是第一次打开
     */
    public void setNotFirstOpen() {
        mEditor.putBoolean("firstOpen", false).commit();
    }

    public void setFirstOpen() {
        mEditor.putBoolean("firstOpen", true).commit();
    }

    /**
     * 是否上传过地理位置到服务器
     */
    public Boolean getIsSend() {
        return sp.getBoolean("isSend", false);
    }

    /**
     * 是否上传过地理位置到服务器
     */
    public void SetHasSend() {
        mEditor.putBoolean("isSend", true).commit();
    }


    /**
     * 从接口获取到version后，存入本地
     */
    public void setVersion(String appVersionName) {
        mEditor.putString("appVersionName", appVersionName).commit();
    }

    /**
     * 获取存入本地的appVersionName
     */
    public String getVersion() {
        return sp.getString("appVersionName", "");
    }

    /**
     * 保存取消升级的当天日期，格式是20160415
     */
    public void saveNotUpdateDay(String date) {
        mEditor.putString("notUpdateDay", date).commit();
    }

    /**
     * @return 获取上次取消升级的保存日期
     */
    public String getNotUpdateDay() {
        return sp.getString("notUpdateDay", "");
    }


    public void saveMsgNumber(int msgNumber) {
        mEditor.putInt("MsgNumber", msgNumber).commit();
    }

    public int getMsgNumber() {
        return sp.getInt("MsgNumber", 0);
    }

    public void saveKeyWord_Search(String keyWord_Search) {
        mEditor.putString("KeyWord_Search", keyWord_Search).commit();
    }

    public String getKeyWord_Search() {
        return sp.getString("KeyWord_Search", "");
    }

    public void saveKeyWord_Search_Tag(String keyWord_Search_History_Tag) {
        mEditor.putString("KeyWord_Search_History_Tag", keyWord_Search_History_Tag).commit();
    }

    public String getKeyWord_Search_Tag() {
        return sp.getString("KeyWord_Search_History_Tag", "");
    }

    public void saveClassName_Search(String className_Search) {
        mEditor.putString("ClassName_Search", className_Search).commit();
    }

    public String getClassName_Search() {
        return sp.getString("ClassName_Search", "");
    }

    public void saveKeyWord_Search_Tag_Category(String keyWord_Search_Tag_Category) {
        mEditor.putString("KeyWord_Search_Tag_Category", keyWord_Search_Tag_Category).commit();
    }

    public String getKeyWord_Search_Tag_Category() {
        return sp.getString("KeyWord_Search_Tag_Category", "");
    }


}

/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014. Ray
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.jiuyou.global;

import com.jiuyou.network.response.JZBResponse.BannerData;

import java.util.List;

/**
 * 全局配置
 */
public final class AppConfig {

    public static List<BannerData> bannerDatas;

    public static final String driver = "android";

    public static final int pageSize = 10;
    public static String currentTAB = "";
    public static String currentOrder_Id = "";
    public static String DisCount = "75";
    public static String erweima = "";
    public static boolean ismap = false;
    public static final String token = "bdaaba2f75c9df39ea6c00e36f3cb696";
    // 开放平台登录https://open.weixin.qq.com的开发者中心获取APPID
    public static final String MXWechatAPPID = "wx3f440ed33ab2e0f2";
    // 开放平台登录https://open.weixin.qq.com的开发者中心获取AppSecret。
    public static final String MXWechatAPPSecret = "0afd25942ba51e29713ce81c4ba8276d";
    // 微信支付商户号
    public static final String MXWechatMCHID = "1447967202";
    // 安全校验码（MD5）密钥，商户平台登录账户和密码登录http://pay.weixin.qq.com
    // 平台设置的“API密钥”，为了安全，请设置为以数字和字母组成的32字符串。
    public static final String MXWechatPartnerKey = "juzhenbaojuzhenbaojuzhenbaojuzhe";


    public static String sdCacheFolder = "";
    // 图片文件夹绝对路径 /sd/sheyuan/picture 注意：不允许二次赋值，此值是在appcation处给值
    public static String PIC_FOLDER_FULL_PATH = "";
    public static String PLUGIN_MODULE_FOLDER_FULL_PATH = "";
    public static final String MODULE_FOLDER_NAME = "/modules";
    public static final int BINDING_SUCCESS = 156;
    public static final int FIX_PHONE_SUCCESS = 157;
    public static final int HOME_LOGIN_RESULT = 22;


    // 调试开关
    public static final boolean DEBUG = false;

    public static String CDNPATH = "";

    // 服务器地址
    // public static final String ENDPOINT = DEBUG ? "http://192.168.1.233:8080"

    //    public static String ENDPOINT = "http://dev.api.digidou.com/dos/classroom/interface";
    //线下
//     public static String ENDPOINT = "http://192.168.1.20/cupboard";
    //线上
    public static String ENDPOINT = "http://jiuks.jzbwlkj.com";
    //线上图片
    public static String ENDPOINTPIC = ENDPOINT + "/Uploads/";


    // public static String ENDPOINT_SYW = "";

    public static final String ALIPAY_CALLBACK = ENDPOINT
            + "/alipay/mobiletify";

    public static String ERROR = "error";

    // 响应缓存的文件夹
    public static final String RESPONSE_CACHE = "rcache";

    // 响应缓存的大小
    public static final long RESPONSE_CACHE_SIZE = 1024 * 1024 * 10;

    // http 连接超时时间 10s
    public static final long HTTP_CONNECT_TIMEOUT = 1000 * 30;

    // http 读取超时时间 15s
    public static final long HTTP_READ_TIMEOUT = 1000 * 30;

    public static final String MM_APP_ID = "wx5f4f11b8e2bee4f9";

    //创建数据库文件夹
    public static final String dirPath = "/data/data/com.sheyuan.findjob/databases";

    public static final String LOGTAG = "CNBLOG";//日志TAG

    public static final String APPTOOKEN = "1";//tooken

    public static final String APPIMEI = "";//imei

    public static final String STAGEPRIMARY = "1";//小学

    public static final int ONEMINUTE = 60000;//一分钟

    public static final String TAG = "tgh";//

    public static String[] ApplyTemplateId = {"1", "2", "3", "4"};

    public static final String REGISTER = "register";

    public static final String PAY = "forgotten";

    public static final String FORGOTTEN = "forgotten";


}

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


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.support.multidex.MultiDexApplication;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.jiuyou.core.AppContext;
import com.jiuyou.ui.Utils.SharedPreference;
import com.jiuyou.util.SharedPrefrencesUtil;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.jpush.android.api.JPushInterface;

public class BaseApp extends MultiDexApplication {
    static URL url = null;
    static String resultData;
    static HttpURLConnection urlConn = null;

    private static BaseApp _app = null;
    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }


    public static BaseApp getApplication() {

        return _app;
    }

    public static Context getAppContext() {
        return BaseApp.mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
        if (_app == null) {
            mContext = _app = this;
        }
        // 对app上下文初始化
        AppContext.getInstance();
//        CrashHandler crashHandler = CrashHandler.getInstance();
//         注册crashHandler
//        crashHandler.init(getApplicationContext());
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush

    }

    //收取广播账号状态变更并保存
    public static Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(getAppContext().getApplicationContext(), "加载中", Toast.LENGTH_SHORT).show();
        }
    };
    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (msg.obj.equals("退出")) {
                    SharedPreferences.Editor editor = getAppContext().getApplicationContext().getSharedPreferences("MyApp", MODE_PRIVATE).edit();
                    editor.clear().commit();
                } else if (msg.obj.equals("登录")) {
                    SharedPreferences.Editor editor = getAppContext().getApplicationContext().getSharedPreferences("MyApp", MODE_PRIVATE).edit();
                    editor.putString("jurisdiction", msg.obj + "");
                    editor.commit();
                }
            }
            if (msg.what == 2) {
                SharedPreferences.Editor editor = getAppContext().getApplicationContext().getSharedPreferences("MyApp", MODE_PRIVATE).edit();
                editor.putString("nickname", msg.obj + "");
                editor.commit();
            }
            if (msg.what == 3) {
                SharedPreferences.Editor editor = getAppContext().getApplicationContext().getSharedPreferences("MyApp", MODE_PRIVATE).edit();
                editor.putString("imageString", msg.obj + "");
                editor.commit();
            }
            if (msg.what == 4) {
                SharedPreferences.Editor editor = getAppContext().getApplicationContext().getSharedPreferences("MyApp", MODE_PRIVATE).edit();
                editor.putString("token", msg.obj + "");
                editor.commit();
            }
            if (msg.what == 5) {
                SharedPreferences.Editor editor = getAppContext().getApplicationContext().getSharedPreferences("MyApp", MODE_PRIVATE).edit();
                editor.putString("imei", msg.obj + "");
                editor.commit();
            }
            if (msg.what == 6) {
                SharedPreferences.Editor editor = getAppContext().getApplicationContext().getSharedPreferences("MyApp", MODE_PRIVATE).edit();
                editor.putString("douid", msg.obj + "");
                editor.commit();
            }
            if (msg.what == 7) {
                {
                    SharedPreferences.Editor editor = getAppContext().getApplicationContext().getSharedPreferences("MyApp", MODE_PRIVATE).edit();
                    editor.putString("grade", msg.obj + "");
                    if ((msg.obj + "").equals("04") || (msg.obj + "").equals("05") || (msg.obj + "").equals("06")) {
                        SharedPrefrencesUtil.setTheme(getContext(), 2);
                    }
                    if ((msg.obj + "").equals("01") || (msg.obj + "").equals("02") || (msg.obj + "").equals("03")) {
                        SharedPrefrencesUtil.setTheme(getContext(), 1);
                    }
                    if ((msg.obj + "").equals("")) {
                        SharedPrefrencesUtil.setTheme(getContext(), 0);
                    }
                    Log.e("接收", msg.obj + "");
                    editor.commit();
                }
            }
        }
    };

    //是否登录状态 1为登录,2为退出或未登录
    public static int getjurisdiction() {
        SharedPreferences preferences = getAppContext().getApplicationContext().getSharedPreferences("MyApp", MODE_PRIVATE);
        String jurisdiction = preferences.getString("jurisdiction", "");
        if (jurisdiction.equals("登录")) {
            return 1;
        }
        return 2;
    }

    public static String token() {
        return new SharedPreference("token").get("token");

    }

    public static String uid() {
        return new SharedPreference("token").get("uid");
    }

    //获取token
    public static String getToken() {
        SharedPreferences preferences = getAppContext().getApplicationContext().getSharedPreferences("MyApp", MODE_PRIVATE);
        String token = preferences.getString("token", "");
        return token;
    }


    public static String getImei() {
        SharedPreferences preferences = getAppContext().getApplicationContext().getSharedPreferences("MyApp", MODE_PRIVATE);
        String imei = preferences.getString("imei", "");
        return imei;
    }

    public static Bitmap getPic() {
        SharedPreferences preferences = getAppContext().getApplicationContext().getSharedPreferences("MyApp", MODE_PRIVATE);
        String imageString = preferences.getString("imageString", "");
        //base64转bitmap
        if (!imageString.equals("")) {
            byte[] byteArray = Base64.decode(imageString, Base64.DEFAULT);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
            Bitmap bitmap = BitmapFactory.decodeStream(byteArrayInputStream);
            return bitmap;
        } else {
            return null;
        }
    }

    //获取nikename
    public static String getNikeName() {
        SharedPreferences preferences = getAppContext().getApplicationContext().getSharedPreferences("MyApp", MODE_PRIVATE);
        String token = preferences.getString("nickname", "");
        return token;
    }

    //获取DouId
    public static String getDouId() {
        SharedPreferences preferences = getAppContext().getApplicationContext().getSharedPreferences("MyApp", MODE_PRIVATE);
        String token = preferences.getString("douid", "");
        return token;
    }

    public static String getGrade() {
        SharedPreferences preferences = getAppContext().getApplicationContext().getSharedPreferences("MyApp", MODE_PRIVATE);
        String grade = preferences.getString("grade", "");
        if (grade.equals("04") || grade.equals("05") || grade.equals("06")) {
            return "高中";
        }
        if (grade.equals("01") || grade.equals("02") || grade.equals("03")) {
            return "初中";
        }
        if (grade.equals("")) {
            return "";
        }
        return "小学";
    }

    public static String ADDRESS = "http://dev.api.digidou.com/";
    //反馈接口
    public static String SENDFEEDBACK = ADDRESS + "dos/classroom/interface/sendFeedBack.php";
    //收藏接口
    public static String GETCOLLECTINFO = ADDRESS + "dos/classroom/interface/getCollectInfo.php";

    public static void HttpURL(final String URL_Post, final Handler Handler, final String content) {
        new Thread() {
            public void run() {
                try {
                    //Post方式
                    url = new URL(URL_Post);
                    urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.setDoOutput(true);
                    urlConn.setDoInput(true);
                    urlConn.setRequestMethod("POST");
                    urlConn.setConnectTimeout(10000);
                    urlConn.setUseCaches(false);
                    urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    urlConn.connect();
                    OutputStream out = urlConn.getOutputStream();
                    out.write(content.getBytes());
                    Log.e("发送数据", content);
                    InputStreamReader in = new InputStreamReader(urlConn.getInputStream());
                    BufferedReader buffer = new BufferedReader(in);
                    String inputLine = null;
                    while (((inputLine = buffer.readLine()) != null)) {
                        resultData = inputLine;
                        Log.e("服务器返回", resultData);
                    }
                    JSONObject json = new JSONObject(resultData);
                    int statuscode = json.getInt("statuscode");
                    if (statuscode == 0) {
                        Message msg = Message.obtain();
                        msg.obj = resultData;
                        msg.what = 0;
                        Handler.sendMessage(msg);
                    } else {
                        Message msg = Message.obtain();
                        msg.obj = resultData;
                        msg.what = 1;
                        Handler.sendMessage(msg);
                    }
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    urlConn.disconnect();
                }
            }
        }.start();
    }

    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2 - 5;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2 - 5;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }
        Bitmap output = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst_left + 2, dst_top + 2, dst_right - 2, dst_bottom - 2);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
//                System.out.println("当前网络是连接的");
                if (info.getState() == NetworkInfo.State.CONNECTED) {
//                    System.out.println("当前所连接的网络可用");
                    return true;
                } else {
//                    Toast.makeText(this, "请检查网络，网络不可用", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
//                    Toast.makeText(this, "请检查网络，网络未连接", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return false;
    }

}

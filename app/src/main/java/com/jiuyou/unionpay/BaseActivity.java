package com.jiuyou.unionpay;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.jiuyou.global.AppConfig;


public abstract class BaseActivity extends Activity implements Callback, Runnable {
  public static final String LOG_TAG = "PayDemo";
  private Context mContext = null;
  private int mGoodsIdx = 0;
  private Handler mHandler = null;
  private ProgressDialog mLoadingDialog = null;
  private String orderId = "";
  private int amount;
  public com.jiuyou.customctrls.ProgressDialog ldb;


  /*****************************************************************
   * mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
   *****************************************************************/
  private final String mMode = "01";
  private String type="1";
  private static final String TN_URL_01 = AppConfig.ENDPOINT+"/api/pay/achieveTN";

  private final View.OnClickListener mClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      Log.e(LOG_TAG, " " + v.getTag());
      mGoodsIdx = (Integer) v.getTag();

      mLoadingDialog = ProgressDialog.show(mContext, // context
          "", // title
          "正在努力的获取tn中,请稍候...", // message
          true); // 进度是否是不确定的，这只和创建进度条有关

      /*************************************************
       * 步骤1：从网络开始,获取交易流水号即TN
       ************************************************/
      new Thread(BaseActivity.this).start();
    }
  };

  protected void getTn(int amount, String orderId,String type){
//    Log.e(LOG_TAG, " " + v.getTag());
//    mGoodsIdx = (Integer) v.getTag();
    this.orderId = orderId;
    this.amount = (int)amount;
    this.type=type;
    mLoadingDialog = ProgressDialog.show(mContext, // context
        "", // title
        "正在努力的获取tn中,请稍候...", // message
        true); // 进度是否是不确定的，这只和创建进度条有关

    /*************************************************
     * 步骤1：从网络开始,获取交易流水号即TN
     ************************************************/
    new Thread(BaseActivity.this).start();
  }

  public abstract void doStartUnionPayPlugin(Activity activity, String tn, String mode);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ldb = new com.jiuyou.customctrls.ProgressDialog(this).createDialog();
    mContext = this;
    mHandler = new Handler(this);

  }
  public void closeProgressBar() {
    if (null != ldb && ldb.isShowing()) {
      ldb.cancel();
    }
  }


  @Override
  public boolean handleMessage(Message msg) {
    Log.e(LOG_TAG, " " + "" + msg.obj);
    if (mLoadingDialog.isShowing()) {
      mLoadingDialog.dismiss();
    }

    String tn = "";
    if (msg.obj == null || ((String) msg.obj).length() == 0) {
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setTitle("错误提示");
      builder.setMessage("网络连接失败,请重试!");
      builder.setNegativeButton("确定",
          new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              dialog.dismiss();
            }
          });
      builder.create().show();
    } else {
      tn = (String) msg.obj;
      /*************************************************
       * 步骤2：通过银联工具类启动支付插件
       ************************************************/
      doStartUnionPayPlugin(this, tn, mMode);
    }

    return false;
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    /*************************************************
     * 步骤3：处理银联手机支付控件返回的支付结果
     ************************************************/
    if (data == null) {
      return;
    }

    String msg = "";
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
    String str = data.getExtras().getString("pay_result");
    if (str.equalsIgnoreCase("success")) {
      // 支付成功后，extra中如果存在result_data，取出校验
      // result_data结构见c）result_data参数说明
      if (data.hasExtra("result_data")) {
        String result = data.getExtras().getString("result_data");
        try {
          JSONObject resultJson = new JSONObject(result);
          String sign = resultJson.getString("sign");
          String dataOrg = resultJson.getString("data");
          // 验签证书同后台验签证书
          // 此处的verify，商户需送去商户后台做验签
          boolean ret = verify(dataOrg, sign, mMode);
          if (ret) {
            // 验证通过后，显示支付结果
            msg = "支付成功！";
          } else {
            // 验证不通过后的处理
            // 建议通过商户后台查询支付结果
            msg = "支付失败！";
          }
        } catch (JSONException e) {
        }
      } else {
        // 未收到签名信息
        // 建议通过商户后台查询支付结果
        msg = "支付成功！";
      }
    } else if (str.equalsIgnoreCase("fail")) {
      msg = "支付失败！";
    } else if (str.equalsIgnoreCase("cancel")) {
      msg = "用户取消了支付";
    }

    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("支付结果通知");
    builder.setMessage(msg);
    builder.setInverseBackgroundForced(true);
    // builder.setCustomTitle();
    builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
      }
    });
    builder.create().show();
  }

  @Override
  protected void onDestroy() {
    ldb.cancel();
    ldb = null;
    super.onDestroy();
  }
  // 加载进度条
  public final com.jiuyou.customctrls.ProgressDialog getLoadingDataBar() {
    return ldb;
  }
  @Override
  public void run() {
    String tn = null;
    InputStream is;
    try {

      String url = TN_URL_01;

      URL myURL = new URL(url);
      HttpURLConnection ucon = (HttpURLConnection) myURL.openConnection();
      ucon.setRequestMethod("POST");
      ucon.setConnectTimeout(120000);
      Log.e("tgh","amount="+amount+"orderid="+orderId+"type="+type);
      String data = "txnAmt=" + amount + "&order_no=" + orderId + "&type=" +type ;
      OutputStream out = ucon.getOutputStream();
      out.write(data.getBytes());
      out.flush();
      out.close();
      is = ucon.getInputStream();
      int i = -1;
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      while ((i = is.read()) != -1) {
        baos.write(i);
      }
      tn = baos.toString();
      is.close();
      baos.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    try {
      JSONObject jsonObject = new JSONObject(tn);
      if (jsonObject.getInt("code") == 200) {
        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
        tn = jsonObject1.getString("TN");
        Log.e("tgh","tn="+tn);
        Message msg = mHandler.obtainMessage();
        msg.obj = tn;
        mHandler.sendMessage(msg);
      }else{
        if (mLoadingDialog.isShowing()) {
          mLoadingDialog.dismiss();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  int startpay(Activity act, String tn, int serverIdentifier) {
    return 0;
  }

  private boolean verify(String msg, String sign64, String mode) {
    // 此处的verify，商户需送去商户后台做验签
    return true;

  }

}

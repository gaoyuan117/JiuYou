package com.jiuyou.alipay;

import android.app.Activity;
import android.os.Handler;

import com.alipay.sdk.app.PayTask;
import com.jiuyou.alipay.util.OrderInfoHelp2_0;
import com.jiuyou.alipay.util.OrderInfoUtil2_0;

import java.util.Map;



public class AliPay {

  private PayTask mPayTask;

  public interface AlipayCallBack {
    void onSuccess();

    void onDeeling();

    void onCancle();

    void onFailure(String msg);
  }

  /**
   * 创建支付对象
   *
   * @param activity activity
   */
  public AliPay(Activity activity) {
    this.mPayTask = new PayTask(activity);
  }

  /**
   * 调起支付
   *
   * @param total_amount   订单总价
   * @param subject        标题
   * @param order_trade_no 订单no
   */
  public void payV2(boolean flag,String total_amount,  String subject,  String
      order_trade_no, final AlipayCallBack callBack) {
    final Handler handler = new Handler();
    boolean rsa2 = (OrderInfoHelp2_0.RSA2_PRIVATE.length() > 0);
    Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap
        (flag,OrderInfoHelp2_0.APPID,total_amount,subject,order_trade_no, rsa2);
    String privateKey = rsa2 ? OrderInfoHelp2_0.RSA2_PRIVATE : OrderInfoHelp2_0.RSA_PRIVATE;
    String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
    String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
    final String orderInfo = orderParam + "&" + sign;
    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        if (mPayTask == null) {
          return;
        }
//        final String result = mPayTask.pay(mParams, true);
        Map<String, String> payResult = mPayTask.payV2(orderInfo, true);
        final PayResult aliPayResult = new PayResult(payResult);
        handler.post(new Runnable() {
          @Override
          public void run() {
            if (callBack == null) {
              return;
            }
            String resultStatus = aliPayResult.getResultStatus();
            if ("9000".equals(resultStatus)) {
              callBack.onSuccess();
            } else if ("8000".equals(resultStatus)) {
              callBack.onDeeling();
            } else if ("4000".equals(resultStatus)) {
              callBack.onFailure("订单支付失败");
            } else if ("6001".equals(resultStatus)) {
              callBack.onCancle();
            } else if ("6002".equals(resultStatus)) {
              callBack.onFailure("网络未连接");
            } else if ("6004".equals(resultStatus)) {
              callBack.onDeeling();
            }
          }
        });
      }
    };
    new Thread(runnable).start();
  }
}

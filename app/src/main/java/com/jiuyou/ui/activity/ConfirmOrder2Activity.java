package com.jiuyou.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiuyou.R;
import com.jiuyou.alipay.AliPay;
import com.jiuyou.core.AppContext;
import com.jiuyou.customctrls.MyListView;
import com.jiuyou.customctrls.widget.OnPasswordInputFinish;
import com.jiuyou.customctrls.widget.PopEnterPassword;
import com.jiuyou.global.AppConfig;
import com.jiuyou.global.BaseApp;
import com.jiuyou.model.OrderInfoBean;
import com.jiuyou.network.interfaces.HomeApi;
import com.jiuyou.network.response.JZBResponse.PayResponse;
import com.jiuyou.network.response.JZBResponse.QuickResponse;
import com.jiuyou.network.response.JZBResponse.UserResponse;
import com.jiuyou.retrofit.BaseObjObserver;
import com.jiuyou.retrofit.HttpResult;
import com.jiuyou.retrofit.RetrofitClient;
import com.jiuyou.retrofit.RxUtils;
import com.jiuyou.ui.Utils.CartUtils;
import com.jiuyou.ui.Utils.UserUtils;
import com.jiuyou.ui.adapter.CountOrder2Adapter;
import com.jiuyou.util.MD5Utils;
import com.jiuyou.util.PopUtil;
import com.jiuyou.util.PrefereUtils;
import com.jiuyou.util.ToastUtil;
import com.jiuyou.wxpay.WxPay;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ConfirmOrder2Activity extends com.jiuyou.ui.base.BaseActivity implements View.OnClickListener {

    private ImageView title_bar_operate_1;
    private MyListView mylistview;
    private TextView tv_pronum, tv_proprice, tv_zongji, tv_tijiao;
    private RelativeLayout rl_lingqian, rl_weixin, rl_zhifubao, rl_yinlian;
    private CountOrder2Adapter adapter;
    private List<OrderInfoBean.DetailInfoBean> mList;
    private String totalPrice;
    private String orderId;
    private String channelPay = "zhifubao";
    private ImageButton ib_lingqian, ib_weixin, ib_zhifubao, ib_yinlian;
    private TextView tv1;
    private int position;
    private List<String> timeList;//配送时间集合
    private String psType, send_type;//取货方式 1 自取  2 配送
    private OrderInfoBean orderInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmorder2);
        ButterKnife.bind(this);
        orderId = getIntent().getStringExtra("order_no");
        position = getIntent().getIntExtra("position", -1);
        BaseApp.positon = position;
        Log.e("gy", "order_no：" + orderId);
        initView();
        getOrderInfo();
    }

    private void initView() {
        mList = new ArrayList<>();
        tv1 = (TextView) findViewById(R.id.tv1);
        ib_lingqian = (ImageButton) findViewById(R.id.ib_lingqian);
        ib_lingqian.setOnClickListener(this);
        ib_weixin = (ImageButton) findViewById(R.id.ib_weixin);
        ib_weixin.setOnClickListener(this);
        ib_zhifubao = (ImageButton) findViewById(R.id.ib_zhifubao);
        ib_zhifubao.setOnClickListener(this);
        ib_yinlian = (ImageButton) findViewById(R.id.ib_yinlian);
        ib_yinlian.setOnClickListener(this);
        title_bar_operate_1 = (ImageView) findViewById(R.id.title_bar_operate_1);
        title_bar_operate_1.setOnClickListener(this);
        mylistview = (MyListView) findViewById(R.id.mylistview);
        tv_pronum = (TextView) findViewById(R.id.tv_pronum);
        tv_proprice = (TextView) findViewById(R.id.tv_proprice);
        tv_zongji = (TextView) findViewById(R.id.tv_zongji);
        tv_tijiao = (TextView) findViewById(R.id.tv_tijiao);
        tv_tijiao.setOnClickListener(this);
        rl_lingqian = (RelativeLayout) findViewById(R.id.rl_lingqian);
        rl_lingqian.setOnClickListener(this);
        rl_weixin = (RelativeLayout) findViewById(R.id.rl_weixin);
        rl_weixin.setOnClickListener(this);
        rl_zhifubao = (RelativeLayout) findViewById(R.id.rl_zhifubao);
        rl_zhifubao.setOnClickListener(this);
        rl_yinlian = (RelativeLayout) findViewById(R.id.rl_yinlian);
        rl_yinlian.setOnClickListener(this);
        adapter = new CountOrder2Adapter(ConfirmOrder2Activity.this, mList);
        mylistview.setAdapter(adapter);

    }

    private void setData() {
        tv_pronum.setText("共" + orderInfoBean.getDetail_Info().size() + "件商品");
        totalPrice = orderInfoBean.getTotal_price();
        tv_proprice.setText(totalPrice);
        tv_zongji.setText("总计:" + totalPrice);
        tv1.setText("余额支付");
    }

    /**
     * 获取订单详情
     */
    private void getOrderInfo() {
        RetrofitClient.getInstance().createApi().orderInfo(BaseApp.token(), orderId)
                .compose(RxUtils.<HttpResult<OrderInfoBean>>io_main())
                .subscribe(new BaseObjObserver<OrderInfoBean>(this, "获取中") {

                    @Override
                    protected void onHandleSuccess(OrderInfoBean bean) {
                        if (bean == null) {
                            return;
                        }
                        orderInfoBean = bean;
                        mList.clear();
                        mList.addAll(bean.getDetail_Info());

                        if (bean.getPickup_mode().equals("自取")) {
                            psType = "1";
                        } else {
                            psType = "2";
                        }
                        Log.e("gy", "pstype：" + bean.getPickup_mode());
                        setData();
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_operate_1:
                ConfirmOrder2Activity.this.finish();
                break;

            case R.id.rl_lingqian:
                channelPay = "lingqian";
                changeIB(R.id.rl_lingqian);
                break;
            case R.id.ib_lingqian:
                channelPay = "lingqian";
                changeIB(R.id.rl_lingqian);
                break;
            case R.id.rl_weixin:
                ToastUtil.show("微信支付功能尚未完善，请先选择其他支付方式支付");
//                channelPay = "weixin";
//                changeIB(R.id.rl_weixin);
                break;
            case R.id.ib_weixin:
                ToastUtil.show("微信支付功能尚未完善，请先选择其他支付方式支付");
//                channelPay = "weixin";
//                changeIB(R.id.rl_weixin);
                break;
            case R.id.rl_zhifubao:
                channelPay = "zhifubao";
                changeIB(R.id.rl_zhifubao);
                break;
            case R.id.ib_zhifubao:
                channelPay = "zhifubao";
                changeIB(R.id.rl_zhifubao);
                break;
            case R.id.rl_yinlian:
                channelPay = "yinlian";
                changeIB(R.id.rl_yinlian);
                break;
            case R.id.ib_yinlian:
                channelPay = "yinlian";
                changeIB(R.id.rl_yinlian);
                break;
            case R.id.tv_tijiao://提交订单
                pay();
                break;
        }
    }

    //支付
    private void pay() {
        if (channelPay.equals("lingqian")) {
            pay_1();
        } else if (channelPay.equals("weixin")) {
            pay_2();
        } else if (channelPay.equals("zhifubao")) {
            pay_3();
        } else if (channelPay.equals("yinlian")) {
            pay_4();
        }
    }

    private void pay_2() {
        String currentMoney = totalPrice.substring(1);
        WxPay.getWxPay().pay(ConfirmOrder2Activity.this, orderId, "购买", currentMoney, "http://cupboard.jzbwlkj.com/index.php/api/Notify/prodWx", new WxPay.WxCallBack() {
            @Override
            public void payResponse(int code) {
                if (code == 0) {
                    AppContext.createRequestApi(HomeApi.class).order_info(PrefereUtils.getInstance().getToken(), orderId, new Callback<QuickResponse>() {
                        @Override
                        public void success(QuickResponse quickResponse, Response response) {
                            Log.e("tgh", "token=" + PrefereUtils.getInstance().getToken() + " orderid=" + orderId);
                            if (quickResponse.getCode() == 200 && quickResponse.getData().getDetail_infos().size() > 0) {
                                Intent intent = null;
                                if (psType.equals("1")) {
                                    intent = new Intent(ConfirmOrder2Activity.this, PaySuccessActivity.class);
                                } else {
                                    intent = new Intent(ConfirmOrder2Activity.this, PaySuccess2Activity.class);
                                }
                                Intent intent1 = new Intent();
                                intent1.putExtra("position", position);
                                intent1.putExtra("status", "1");
                                setResult(RESULT_OK, intent1);

                                Bundle bundle = new Bundle();
                                bundle.putSerializable("detail_Info", quickResponse.getData().getDetail_infos());
                                bundle.putString("pkcode", quickResponse.getData().getPkcode());
                                bundle.putString("qrcode", quickResponse.getData().getQrcode());
                                bundle.putString("orderId", orderId);
                                bundle.putString("position", "1");
                                intent.putExtras(bundle);
                                ConfirmOrder2Activity.this.startActivity(intent);
                                ConfirmOrder2Activity.this.finish();
                            } else {
                                AppConfig.currentTAB = MainActivity.TAB_ACCOUNT;
                                ConfirmOrder2Activity.this.finish();
                            }
                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {

                        }
                    });
//                    ToastUtil.show("缴费成功");
                } else {
                    ToastUtil.show("购买失败");
                }
            }
        });
    }

    private void pay_3() {

        AliPay aliPay = new AliPay(ConfirmOrder2Activity.this);
        String currentMoney = totalPrice.substring(1);
        aliPay.payV2(true, currentMoney, "购买", orderId, new AliPay.AlipayCallBack() {
            @Override
            public void onSuccess() {
                AppContext.createRequestApi(HomeApi.class).order_info(PrefereUtils.getInstance().getToken(), orderId, new Callback<QuickResponse>() {
                    @Override
                    public void success(QuickResponse quickResponse, Response response) {
                        Log.e("tgh", "token=" + PrefereUtils.getInstance().getToken() + " orderid=" + orderId + "    " + quickResponse.getCode());
                        if (quickResponse.getCode() == 200 && quickResponse.getData().getDetail_infos().size() > 0) {
                            Intent intent = null;
                            if (psType.equals("1")) {
                                intent = new Intent(ConfirmOrder2Activity.this, PaySuccessActivity.class);
                            } else {
                                intent = new Intent(ConfirmOrder2Activity.this, PaySuccess2Activity.class);
                            }
                            Intent intent1 = new Intent();
                            intent1.putExtra("position", position);
                            intent1.putExtra("status", "1");
                            setResult(RESULT_OK, intent1);

//                            Intent intent = new Intent(ConfirmOrderActivity.this, PaySuccessActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("detail_Info", quickResponse.getData().getDetail_infos());
                            bundle.putString("pkcode", quickResponse.getData().getPkcode());
                            bundle.putString("qrcode", quickResponse.getData().getQrcode());
                            bundle.putString("orderId", orderId);
                            bundle.putString("position", "1");
                            intent.putExtras(bundle);
                            ConfirmOrder2Activity.this.startActivity(intent);

                            ConfirmOrder2Activity.this.finish();
                        } else {
                            AppConfig.currentTAB = MainActivity.TAB_ACCOUNT;
                            ConfirmOrder2Activity.this.finish();
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {

                    }
                });
                ToastUtil.show("购买成功");
            }

            @Override
            public void onDeeling() {
                ToastUtil.show("支付完成，后台处理中");
            }

            @Override
            public void onCancle() {
                ToastUtil.show("取消购买");
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.show(msg);
            }
        });
    }

    private void pay_4() {
        String currentMoney = totalPrice.substring(1);
//        getTn((int) (Double.valueOf(currentMoney) * 100), orderId, "2");
    }

    private void pay_1() {
        getLoadingDataBar().show();
        CartUtils.isFirstPay(PrefereUtils.getInstance().getToken(), new CartUtils.isFirstPayListener() {
            @Override
            public void load(boolean status, PayResponse info, String message) {
                if (status) {
                    if (info.getPay().getIs_first_pay() == 1) {
                        //支付密码尚未设置
                        showPayKeyBoard(true, tv_zongji.getText().toString().trim(), new OnPasswordInputFinish() {
                            @Override
                            public void inputFinish(String password) {
                                popEnterPassword.dismiss();
                                UserUtils.setPayInfo(PrefereUtils.getInstance().getToken(), password, new UserUtils.setPayListener() {
                                    @Override
                                    public void load(boolean status, UserResponse info, String message) {
                                        if (status) {
                                            //密码设置成功
                                            showPayKeyBoard(false, tv_zongji.getText().toString().trim(), new OnPasswordInputFinish() {
                                                @Override
                                                public void inputFinish(String password) {
                                                    //进行支付操作
                                                    getLoadingDataBar().show();
                                                    String token = PrefereUtils.getInstance().getToken();
                                                    Log.e("tgh", "token=" + token + "orderId=" + orderId + "pwd=" + MD5Utils.toMD5(password));
                                                    CartUtils.quick(token, orderId, password, new CartUtils.quickListener() {
                                                        @Override
                                                        public void load(boolean status, QuickResponse info, String message) {
                                                            if (status) {
                                                                //零钱支付成功
                                                                Intent intent = null;
                                                                if (psType.equals("1")) {
                                                                    intent = new Intent(ConfirmOrder2Activity.this, PaySuccessActivity.class);
                                                                } else {
                                                                    intent = new Intent(ConfirmOrder2Activity.this, PaySuccess2Activity.class);
                                                                }
                                                                Intent intent1 = new Intent();
                                                                intent1.putExtra("position", position);
                                                                intent1.putExtra("status", "1");
                                                                setResult(RESULT_OK, intent1);
                                                                Bundle bundle = new Bundle();
                                                                bundle.putSerializable("detail_Info", info.getData().getDetail_infos());
                                                                bundle.putString("pkcode", info.getData().getPkcode());
                                                                bundle.putString("qrcode", info.getData().getQrcode());
                                                                bundle.putString("orderId", orderId);
                                                                bundle.putString("position", "1");
                                                                intent.putExtras(bundle);
                                                                ConfirmOrder2Activity.this.startActivity(intent);

                                                                ConfirmOrder2Activity.this.finish();
                                                            } else {
                                                                if (message.contains("余额不足")) {
                                                                    PopUtil.showDialog(ConfirmOrder2Activity.this, "温馨提醒", "您的余额不足，是否前往充值？", "取消", "去充值", null, new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View v) {
                                                                            //充值界面
                                                                            startActivity(new Intent(ConfirmOrder2Activity.this, RechargeActivity.class));
                                                                            finish();
                                                                        }
                                                                    });
                                                                } else {
                                                                    ToastUtil.show(message);
                                                                }
                                                            }
                                                            closeProgressBar();
                                                        }
                                                    });

                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        });
                    } else if (info.getPay().getIs_first_pay() == 0) {
                        //支付密码已经设置
                        showPayKeyBoard(false, tv_zongji.getText().toString().trim(), new OnPasswordInputFinish() {
                            @Override
                            public void inputFinish(String password) {
                                //进行支付操作
                                popEnterPassword.dismiss();
                                getLoadingDataBar().show();
//                                String md5pwd=MD5Utils.md5(password.getBytes());
                                Log.e("tgh", "token=" + PrefereUtils.getInstance().getToken() + "orderId=" + orderId + "pwd=" + MD5Utils.toMD5(password));
                                CartUtils.quick(PrefereUtils.getInstance().getToken(), orderId, password, new CartUtils.quickListener() {
                                    @Override
                                    public void load(boolean status, QuickResponse info, String message) {
                                        if (status) {
                                            //零钱支付成功
                                            Intent intent = null;
                                            if (psType.equals("1")) {
                                                intent = new Intent(ConfirmOrder2Activity.this, PaySuccessActivity.class);
                                            } else {
                                                intent = new Intent(ConfirmOrder2Activity.this, PaySuccess2Activity.class);
                                            }
                                            Intent intent1 = new Intent();
                                            intent1.putExtra("position", position);
                                            intent1.putExtra("status", "1");
                                            setResult(RESULT_OK, intent1);

                                            Bundle bundle = new Bundle();
                                            bundle.putSerializable("detail_Info", info.getData().getDetail_infos());
                                            bundle.putString("pkcode", info.getData().getPkcode());
                                            bundle.putString("qrcode", info.getData().getQrcode());
                                            bundle.putString("orderId", orderId);
                                            bundle.putString("position", "1");
                                            intent.putExtras(bundle);
                                            ConfirmOrder2Activity.this.startActivity(intent);
                                            ConfirmOrder2Activity.this.finish();
                                        } else {

                                            if (message.contains("余额不足")) {
                                                PopUtil.showDialog(ConfirmOrder2Activity.this, "温馨提醒", "您的余额不足，是否前往充值？", "取消", "去充值", null, new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        //充值界面
                                                        startActivity(new Intent(ConfirmOrder2Activity.this, RechargeActivity.class));
                                                        finish();
                                                    }
                                                });
                                            } else {
                                                ToastUtil.show(message);
                                            }
                                        }
                                        closeProgressBar();
                                    }
                                });


                            }
                        });
                    }
                } else {
                    ToastUtil.show(message);
                }
                closeProgressBar();
            }
        });
    }

    PopEnterPassword popEnterPassword;

    public void showPayKeyBoard(boolean isFirstPay, String amount, OnPasswordInputFinish listener) {
        popEnterPassword = new PopEnterPassword(this, isFirstPay, amount, listener);
        // 显示窗口
        popEnterPassword.showAtLocation(this.findViewById(R.id.layoutContent),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置

    }

    private void changeIB(int id) {
        switch (id) {
            case R.id.rl_lingqian:
                tv_zongji.setText("总计:¥" + totalPrice);
                ib_lingqian.setBackgroundResource(R.mipmap.icon_fuxuankuan_xuanzhong);
                ib_zhifubao.setBackgroundResource(R.mipmap.icon_fuxuankuan_weixuan);
                ib_weixin.setBackgroundResource(R.mipmap.icon_fuxuankuan_weixuan);
                ib_yinlian.setBackgroundResource(R.mipmap.icon_fuxuankuan_weixuan);
                break;
            case R.id.rl_zhifubao:
                tv_zongji.setText("总计:¥" + totalPrice);
                ib_lingqian.setBackgroundResource(R.mipmap.icon_fuxuankuan_weixuan);
                ib_zhifubao.setBackgroundResource(R.mipmap.icon_fuxuankuan_xuanzhong);
                ib_weixin.setBackgroundResource(R.mipmap.icon_fuxuankuan_weixuan);
                ib_yinlian.setBackgroundResource(R.mipmap.icon_fuxuankuan_weixuan);
                break;
            case R.id.rl_weixin:
                tv_zongji.setText("总计:¥" + totalPrice);
                ib_lingqian.setBackgroundResource(R.mipmap.icon_fuxuankuan_weixuan);
                ib_zhifubao.setBackgroundResource(R.mipmap.icon_fuxuankuan_weixuan);
                ib_weixin.setBackgroundResource(R.mipmap.icon_fuxuankuan_xuanzhong);
                ib_yinlian.setBackgroundResource(R.mipmap.icon_fuxuankuan_weixuan);
                break;
            case R.id.rl_yinlian:
                tv_zongji.setText("总计:¥" + totalPrice);
                ib_lingqian.setBackgroundResource(R.mipmap.icon_fuxuankuan_weixuan);
                ib_zhifubao.setBackgroundResource(R.mipmap.icon_fuxuankuan_weixuan);
                ib_weixin.setBackgroundResource(R.mipmap.icon_fuxuankuan_weixuan);
                ib_yinlian.setBackgroundResource(R.mipmap.icon_fuxuankuan_xuanzhong);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


    }
}
